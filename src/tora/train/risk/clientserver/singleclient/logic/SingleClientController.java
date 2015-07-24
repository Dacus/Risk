package tora.train.risk.clientserver.singleclient.logic;

import tora.train.risk.Arena;
import tora.train.risk.Player;
import tora.train.risk.Territory;
import tora.train.risk.clientserver.common.Controller;
import tora.train.risk.clientserver.common.Message;
import tora.train.risk.clientserver.common.MessageHandler;
import tora.train.risk.clientserver.common.MessageType;
import tora.train.risk.clientserver.singleclient.gui.MapController;
import tora.train.risk.clientserver.singleclient.gui.SingleClientFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private MapController mapController;
    private boolean myTurn=false;

    private boolean readyFlag;

    public SingleClientController(SingleClientFrame frame, MessageHandler handler){
        this.clientFrame=frame;
        handler.setController(this);
        this.clientSocket=new CSocketClient(handler);

        this.clientFrame.setConnectionButtonListener(new ConnectAction());
        this.clientFrame.setWindowListener(new WindowDisconnectAction());
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
    public void startRunning(){
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
        Message msg=new Message(MessageType.CONNECT);
        msg.addElement(clientSocket.getClientName());
        writeMessage(msg);
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
    public void writeMessage(Message msg) {
        try {
            clientSocket.writeMessage(msg);
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

    public void setTurn(boolean value){
        this.myTurn=value;
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
        clientFrame.setIncomingAreaText(msg.getElementAt(0).toString());
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
     * @param status the status to be set on the panel
     */
    public void setStatus(Status status) {
        this.clientFrame.setStatus(status);
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
            startRunning();
        }
    }

    /**
     * Action assigned to the "Disconnect" button on the GUI that disconnects the client from the server
     */
    private class WindowDisconnectAction implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            if (clientSocket.isRunning()) {
                Message msg = new Message(MessageType.STOP);
                msg.addElement(clientSocket.getClientId());
                writeMessage(msg);


                clientSocket.stopRunning();
                clientFrame.close();
                System.out.println("Client Th: " + Thread.currentThread().getName());
            } else {
                clientFrame.close();
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    }

    /**
     * Action assigned to the "Send Message" button on the GUI that reads the text typed in by the user in the
     * Outgoing Messages area and sends it to the server.
     */
    class SendUserMessageAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Message msg=new Message(MessageType.USER);
            String strToSend=clientFrame.getOutgoingMessageFromField();
            msg.addElement(strToSend);

            writeMessage(msg);
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
                Message msg = new Message(MessageType.READY);
                writeMessage(msg);
                clientFrame.setStatus(Status.READY);
                readyFlag=true;
            }
        }
    }    /*************************************************************************************************
     * GAME RELATED
     ************************************************************************************************/
    public void initializeMap(Arena arena) {
        mapController=new MapController(clientSocket.getClientName());

        setMapListeners();

        clientFrame.setStatus(Status.PLAYING);
        System.out.println("Arena received");
        mapController.setArena(arena);
        mapController.updateArenaView();
    }

    public void setMapListeners(){

        this.mapController.addReinforceButtonListener(new ReinforceAction());
        this.mapController.addAttackButtonListener(new AttackAction());
    }

    public void establishTurn(Player currentPlayer, List<String> orderedListOfPlayers, List<Territory> territoryList) {
        mapController.changeTurn(currentPlayer, orderedListOfPlayers, territoryList);

        if (currentPlayer.getName().equals(clientSocket.getClientName())) {
            setTurn(true);
            System.out.println("It's my turn");
        }
        else {
            setTurn(false);
        }

    }

    class ReinforceAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (myTurn) {
                try {
                    Point destination = mapController.getReinforceCoordinates();
                    int value = mapController.getReinforcementValue();

                    Message msg = new Message(MessageType.TRY_REINFORCE);
                    msg.addElement(destination);
                    msg.addElement(value);
                    msg.addElement(clientSocket.getClientId());

                    writeMessage(msg);
                }
                catch(NumberFormatException ex){
                    mapController.displayInformativeMessage("Please insert numerical values");
                }
            }
        }
    }

    class AttackAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (myTurn) {
                try {
                    Point destination = mapController.getReinforceCoordinates();
                    int value = mapController.getReinforcementValue();


                    //TO DO
                }
                catch(NumberFormatException ex){
                    mapController.displayInformativeMessage("Please insert numerical values");
                }
            }
        }
    }

    public void applyReinforcement(String playerName, int reinforcementsLeft, int x, int y, int numOfUnits) {
        mapController.updateArena(playerName, reinforcementsLeft, x, y, numOfUnits);
    }

    public void denyReinforcement() {
        mapController.displayInformativeMessage("Reinforcement denied");
    }

    public void enableMoveAndAttackView() {
        mapController.showMoveAndAttackPanel();
    }

    public void updateReinforcementLabel(int reinforcements) {
        mapController.updateReinforcementLabel(reinforcements);
    }

}
