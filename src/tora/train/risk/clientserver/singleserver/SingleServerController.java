package tora.train.risk.clientserver.singleserver;

import tora.train.risk.clientserver.common.Controller;
import tora.train.risk.clientserver.common.Message;
import tora.train.risk.clientserver.common.MessageType;
import tora.train.risk.clientserver.serverapp.MainServerController;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that holds control over the CMSocketServer.
 *
 * It communicates with the MainServer when needed (see the MainServer reference in the constructor).
 * Example: client disconnects
 *      1. Client presses the "Disconnect" button, sending a message: Tag=STOP, Content:{clientId}
 *      2. CMSocketServer reads it from the communication channel. The MessageHandler receives it for processing
 *      3. The MessageHandler calls the stop(clientId) method on the SingleServerController, that tells the MainServer
 *      that a client wants to disconnect. Without a reference to the MainServer, this couldn't be possible.
 *
 * Created by Andrea on 7/16/2015.
 */
public class SingleServerController implements Controller {
    private CMSocketServer singleServer;
    private MainServerController mainController;
    private SingleServerMessageHandler messageHandler;

    public SingleServerController(CMSocketServer server, MainServerController supremeController, SingleServerMessageHandler handler) {
        this.singleServer = server;
        this.mainController=supremeController;
        this.messageHandler=handler;
        messageHandler.setController(this);
    }

    /***********************************************************************************************
     * CONTROL
     **********************************************************************************************/

    /**
     * Asks the MainServer to remove from the list of connected clients the one with the id equal to
     * the value of the "id" parameter
     *
     * @param id client to remove
     */
    public void stop(int id){
        stopRunning();
        mainController.removeSingleServer(id);
    }

    @Override
    public void startRunning() {
        //Keep each client on its own thread
        Thread thread = new Thread(singleServer);
        thread.start();
    }

    /**
     * Stops the CMSocketServer thread's loop
     */
    public void stopRunning() {
        try {
            singleServer.stopRunning();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message from the single server CMSocketServer to the client connected to it
     *
     * @param msg message to be sent
     */
    @Override
    public void writeMessage(Message msg) {
        try {
            singleServer.writeMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the incoming message received from the CSocketClient connected to the single CMSocketServer
     *
     * @return message read from the communication channel
     */
    @Override
    public Message readMessage() {
        try {
            return singleServer.readMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The MainServer assigns a unique id to each new connected client. To make sure that the client knows it's assigned
     * id, the MainServer asks the client's corresponding "single server" to communicate this information through
     * an IDENTITY tagged message.
     *
     * This method sends a message to the client with the unique identification number
     * the MainServer has assigned to it.
     *
     * @param id    identification number that the client receives when connecting to the MainServer
     */
    public void setID(int id){
        Message inMsg=readMessage();
        String name=String.valueOf(inMsg.getElementAt(0));

        Message outMsg=new Message(MessageType.IDENTITY);
        outMsg.addElement(id);
        writeMessage(outMsg);

        mainController.setClientName(name, id);
    }

    /**
     * Sends a STOP tagged message to the client, asking him to stop listening for messages
     *
     */
    public void stopClient(){
        writeMessage(new Message(MessageType.STOP));
        stopRunning();
    }

    /****************************************************************************
     * VIEW RELATED
     ****************************************************************************/

    /**
     * Displays in the corresponding field on the application GUI the message received from the client
     *
     * @param str String to be displayed
     */
    public void displayMessage(String str){
        mainController.displayMessage(str);
    }

    /**
     * Notifies the main server that the client sent a READY message
     */
    public void notifyClientIsReady() {
        mainController.incrementNumberOfReadyClients();
    }

    /**
     * Builds a message having on the first position the number of online clients
     * followed by their names and sends it to the client, tagged with ONLINE_PLAYERS
     *
     * @param names
     */
    public void sendListOfOnlineClients(ArrayList<String> names) {
        Message msg=new Message(MessageType.ONLINE_PLAYERS);
        int n=names.size();
        msg.addElement(n);
        for (int i=0; i<n; i++){
            msg.addElement(names.get(i));
        }
        writeMessage(msg);
    }

    /**
     * Receives the connection message from the client.
     * Sends a restriction message with the maximum number of clients that can be online at a time
     * Stops the CMSocketServer after sending the message
     *
     * @param maxNumberOfClients
     */
    public void restrictClient(int maxNumberOfClients) {
        Message inMsg=readMessage();
        String name=String.valueOf(inMsg.getElementAt(0));

        Message msg=new Message(MessageType.RESTRICT_CONNECTION);
        msg.addElement(maxNumberOfClients);
        writeMessage(msg);

        stopRunning();
    }

    public void tryReinforce(Point point, int value, int playerId) {
        mainController.tryReinforce(point, value, playerId);
    }
}
