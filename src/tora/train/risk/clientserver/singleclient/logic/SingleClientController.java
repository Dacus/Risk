package tora.train.risk.clientserver.singleclient.logic;

import tora.train.risk.clientserver.common.Controller;
import tora.train.risk.clientserver.common.Message;
import tora.train.risk.clientserver.common.MessageHandler;
import tora.train.risk.clientserver.common.MessageTag;
import tora.train.risk.clientserver.singleclient.gui.MapController;
import tora.train.risk.clientserver.singleclient.gui.SingleClientFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controls the Client.
 * Responds to user interaction on the Client's side.
 * Contains methods that modify the View (SingleClientFrame) when needed.
 * <p/>
 * Each CSocketClient has a reference to a MessageMessage handler that processes
 * incoming messages. The MessageHandler has a reference to this controller (see constructor) and calls its methods
 * according to the message received.
 * <p/>
 * Created by Andrea on 7/16/2015.
 */
public class SingleClientController implements Controller {
    private SingleClientFrame clientFrame;
    private CSocketClient clientSocket;

    private boolean readyFlag;

    public SingleClientController(SingleClientFrame frame, MessageHandler handler){
        this.clientFrame=frame;
        handler.setController(this);
        this.clientSocket=new CSocketClient(handler);

        this.clientFrame.setConnectionButtonListener(new ConnectAction());
        this.clientFrame.setDisconnectButtonListener(new DisconnectAction());
        this.clientFrame.setSendMessageButtonListener(new SendUserMessageAction());
        this.clientFrame.setReadyButtonListener(new ReadyAction());
    }

    /***************************************************************************************
     * CONTROL
     **************************************************************************************/

    /**
     * Connects the client to the server socket. If the connection succeeded, it starts a new
     * thread where the client continuously reads incoming messages, delegating
     * their processing to a MessageHandler.
     */
    public void startClient(){
        try {
            if (! clientSocket.isRunning()) {
                clientSocket.connect();
                clientSocket.setClientName(clientFrame.getName());

                connectByName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message with the Client's name
     */
    public void connectByName(){
        Message msg=new Message(MessageTag.CONNECT);
        msg.addObject(clientSocket.getClientName());
        sendMessage(msg);
    }

    /**
     * Stops the client thread by sending a Stop message to the CSocketServer to whom it communicates.
     * The stopping message will contain the id of this client so that the server can identify it and
     * remove it from the list of currently connected clients.
     */
    @Override
    public void stopRunning() {
        clientSocket.stopRunning();
        clientFrame.close();
    }

    /**
     * Sends a message from the CSocketClient to the CSocketServer the client communicates with.
     *
     * @param msg the message to be sent
     */
    @Override
    public void sendMessage(Message msg) {
        try {
            clientSocket.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads an incoming message received from the CSocketServer the client communicated with
     *
     * @return the message received from the client-server connection
     */
    @Override
    public Message readMessage() {
        Message received=null;
        try {
            received=clientSocket.readMessage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return received;
    }

    /***********************************************************************************
     * LISTENERS
     ************************************************************************************/
    /**
     * Displays the message received from the server
     *
     * @param msg
     */
    public void displayMessage(Message msg) {
        System.out.println("Client " + clientSocket.getClientId() + " displays global message");
        clientFrame.setIncomingAreaText(msg.getContent().get(0).toString());
    }

    /**
     * Sets the id of the client.
     * <p/>
     * As soon as the client connects, the server assigns it a unique identification number.
     * The server lets the client know about this number through a notification message.
     * The client will store this number and use it to communicate its identity to the server.
     *
     * @param id
     */
    public void setClientIdentity(int id) {
        this.clientSocket.setClientId(id);
    }

    /**
     * Adds a client name to the ComboBox that holds the names of the currently online clients
     *
     * @param name the String to be added to the ComboBox
     */
    public void addNewPlayerToCombo(String name) {
        this.clientFrame.addPlayer(name);
    }

    /**
     * Removes a client name from the ComboBox containing the online clients' names
     *
     * @param name the name to be removed from the ComboBox
     */
    public void removePlayerFromCombo(String name) {
        this.clientFrame.removePlayer(name);
    }

    /***************************************************************************************
     * VIEW RELATED
     **************************************************************************************/

    /**
     * Adds a list of names to the ComboBox holding online clients' names
     *
     * @param names list of Strings representing clients' names
     */
    public void addPlayersToCombo(ArrayList<String> names) {
        for (String name : names) {
            clientFrame.addPlayer(name);
        }
    }

    /**
     * Displays a message on an option pane to inform the client that it cannot connect
     *
     * @param max
     */
    public void restrictConnection(int max) {
        clientFrame.showOptionPanel(max + " players connected. Please try again later!");
    }

    /**
     * Changes the text on the Status panel of the Client GUI according to the value of b
     *
     * @param b true if the client is connected
     */
    public void setClientConnected(boolean b) {
        this.clientFrame.setStatus(b);
    }

    public void initializeMap() {
        MapController mapController=new MapController(clientSocket.getClientName());
    }

    /***************************************************************************************
     * LISTENER
     **************************************************************************************/

    /**
     * Action assigned to the "Connect" button on the GUI that connects the client to server
     */
    class ConnectAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            startClient();
        }
    }

    /**
     * Action assigned to the "Disconnect" button on the GUI that disconnects the client from the server
     */
    class DisconnectAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (clientSocket.isRunning()) {
                Message msg=new Message(MessageTag.STOP);
                msg.addObject(clientSocket.getClientId());
                sendMessage(msg);

                clientFrame.setStatus(false);
                clientSocket.stopRunning();
                clientFrame.close();
                System.out.println("Client Th: " + Thread.currentThread().getName());
            }
        }
    }

    /**
     * Action assigned to the "Send Message" button on the GUI that reads the text typed in by the user in the
     * Outgoing Messages area and sends it to the server.
     */
    class SendUserMessageAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Message msg=new Message(MessageTag.USER);
            String strToSend=clientFrame.getOutgoingMessageFromField();
            msg.addObject(strToSend);

            sendMessage(msg);
        }
    }

    /**
     * Action assigned to the "Ready" button on the GUI that sends a READY message to the server
     * telling that the client is ready to start
     */
    class ReadyAction implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (readyFlag){
                clientFrame.showOptionPanel("You already told the others that you are ready to play!");
            }else {
                Message msg = new Message(MessageTag.READY);
                sendMessage(msg);
                readyFlag=true;
            }
        }
    }
}
