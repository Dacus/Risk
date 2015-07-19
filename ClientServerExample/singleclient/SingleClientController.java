package singleclient;

import common.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Controls the Client.
 * Responds to user interaction on the Client's side.
 * Contains methods that modify the View (SingleClientFrame) when needed.
 *
 * Each CSocketClient has a reference to a MessageMessage handler that processes
 * incoming messages. The MessageHandler has a reference to this controller (see constructor) and calls its methods
 * according to the message received.
 *
 * Created by Andrea on 7/16/2015.
 */
public class SingleClientController implements Controller {
    private SingleClientFrame clientFrame;
    private CSocketClient clientSocket;

    public SingleClientController(SingleClientFrame frame, MessageHandler handler){
        this.clientFrame=frame;
        handler.setController(this);
        this.clientSocket=new CSocketClient(handler);

        this.clientFrame.setConnectionButtonListener(new ConnectAction());
        this.clientFrame.setDisconnectButtonListener(new DisconnectAction());
        this.clientFrame.setSendMessageButtonListener(new SendUserMessageAction());
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
                clientFrame.setStatus(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                Message msg=MessageProvider.getMessage(MessageTag.STOP);
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

    /***************************************************************************************
     * VIEW RELATED
     **************************************************************************************/
    /**
     * Displays the message received from the server on the GUI
     *
     * @param msg
     */
    public void displayMessage(Message msg){
        clientFrame.setIncomingAreaText(msg.getContent().get(0).toString());
    }

    /**
     * Sets the id of the client.
     *
     * As soon as the client connects, the server assigns it a unique identification number.
     * The server lets the client know about this number through a notification message.
     * The client will store this number and use it to communicate its identity to the server.
     *
     * @param id
     */
    public void setClientIdentity(int id){
        this.clientSocket.setClientId(id);
    }
}
