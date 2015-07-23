package tora.train.risk.clientserver.serverapp;

import tora.train.risk.ArenaController;
import tora.train.risk.clientserver.common.Controller;
import tora.train.risk.clientserver.common.Message;
import tora.train.risk.clientserver.common.MessageType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

/**
 * This class controls the actions on the MainServer and displays the results on the MainServerFrame. It is entirely
 * responsible for the user interaction with the MainServer.
 *
 * There is a two way communication between the MainServer and the Controller. The Controller may call methods on the
 * Server and the other way around. When the model (Server) changes and those changes have to be reflected on the view
 * (MainServerFrame), the Server will call methods on the Controller to make those changes happen. This implements
 * in fact the Observer pattern.
 *
 * Created by Andrea on 7/16/2015.
 */
public class MainServerController implements Controller {
    private MainServer server;
    private MainServerFrame frame;
    private ArenaController arenaController;

    public MainServerController(MainServerFrame frame){
        this.frame=frame;
        this.server=new MainServer(this); //set the Controller of the MainServer to ensure two-way communication

        frame.setSendButtonListener(new SendMessageAction());
        frame.setWindowExitListener(new ServerWindowListener());
    }

    /***********************************************************************************
     * CONTROL
     ************************************************************************************/

    /**
     * Calls a method on the main server that increments the variable counting the
     * number of clients that have pushed the 'Ready' button
     */
    public void incrementNumberOfReadyClients() {
        server.incrementReadyCounter();
    }

    /**
     * Sets the client's name and id in the main server
     *
     * @param name
     * @param id
     */
    public void setClientName(String name, int id) {
        server.setClientName(name, id);
    }

    /**
     * Starts the server in the current thread
     */
    @Override
    public void startRunning() {
        server.run();
    }

    @Override
    public void stopRunning() {
        server.stopSingleServers();
        try {
            server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to all connected clients
     *
     * @param msg
     */
    @Override
    public void writeMessage(Message msg) {
        server.sendGlobalMessage(msg);
    }

    @Override
    public Message readMessage() {
        return null;
    }

    public void closeWindow() {
        this.frame.close();
    }

    /***********************************************************************************
     * LISTENERS
     ************************************************************************************/
    /**
     * Removes a client from the map stored the MainServer
     *
     * @param id the id of the client removed
     */
    public void removeSingleServer(int id) {
        server.removeSingleServer(id);
    }

    /**
     * Displays the incoming messages from the clients on the GUI
     *
     * @param str String to display
     */
    public void displayMessage(String str) {
        this.frame.setIncomingAreaText(str);
    }

    /**
     * Displays the number of clients currently online
     *
     * @param n the number of online clients
     */
    public void setNumberOfOnlineClients(int n) {
        this.frame.changeNumberOfClientsOnline(n);
    }

    /***********************************************************************************
     * LISTENERS
     ************************************************************************************/

    /**
     * ********************************************************************************
     * GAME RELATED
     * **********************************************************************************
     */
    public void startGame() {
        this.arenaController = new ArenaController();

        Message msg = new Message(MessageType.START);
        msg.addElement(arenaController.getArena());
        server.sendGlobalMessage(msg);
    }

    /** Window listener class for closing the frame and releasing the resources, resulting
     *  in a graceful server stop
     */
    private class ServerWindowListener implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {}

        @Override
        public void windowClosing(WindowEvent e) {
            stopRunning();
        }

        @Override
        public void windowClosed(WindowEvent e) {}

        @Override
        public void windowIconified(WindowEvent e) {}

        @Override
        public void windowDeiconified(WindowEvent e) {}

        @Override
        public void windowActivated(WindowEvent e) {}

        @Override
        public void windowDeactivated(WindowEvent e) {}
    }

    /**
     * Action assigned to the "Send to all" button on the GUI.
     */
    private class SendMessageAction implements ActionListener {

        /**
         * Creates a new Message, tagged GLOBAL.
         * Adds the String entered by the user in the frame's "outgoingTextField" JTextField to this message.
         * Sends this message to all online clients.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String strToSend = frame.getOutgoingString();

            Message msgToSend = new Message(MessageType.GLOBAL);
            msgToSend.addElement(strToSend);

            server.sendGlobalMessage(msgToSend);
        }
    }

    /***********************************************************************************
     * GAME RELATED
     ************************************************************************************/
    public void startGame(){
        this.arenaController=new ArenaController();
        List<String> list=server.getPlayers();

        for (String name: list) {
            arenaController.addPlayer(new Player(name));
        }

        arenaController.distributePlayers(5,1);

        Message msg=new Message(MessageType.START);
        msg.addElement(arenaController.getArena());
        server.sendGlobalMessage(msg);
    }


}
