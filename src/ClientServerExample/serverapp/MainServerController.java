package ClientServerExample.serverapp;

import ClientServerExample.common.Message;
import ClientServerExample.common.MessageProvider;
import ClientServerExample.common.MessageTag;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class MainServerController {
    private MainServer server;
    private MainServerFrame frame;

    public MainServerController(MainServerFrame frame){
        this.frame=frame;
        this.server=new MainServer(this); //set the Controller of the MainServer to ensure two-way communication

        frame.setQuitButtonListener(new StopServerAction());
        frame.setSendButtonListener(new SendMessageAction());
    }

    /***********************************************************************************
     * CONTROL
     ************************************************************************************/

    /**
     * Starts the server in the current thread
     */
    public void startServer(){
        server.run();
    }

    /**
     * Action assigned to the "Stop Server" button on the GUI to stop the MainServer.
     * It stops all single servers (thus all clients) then it shuts down.
     */
    class StopServerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            server.stopSingleServers();
        }
    }

    /**
     * Action assigned to the "Send to all" button on the GUI.
     * It reads the message written by the user in the JTextField and sends it to all clients (global message)
     */
    class SendMessageAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String str=frame.getOutgoingTextField();
            Message msg=MessageProvider.getMessage(MessageTag.GLOBAL);
            msg.addObject(str);
            server.sendGlobalMessage(msg);
        }
    }

    /**
     * Removes a client from the map stored the MainServer
     *
     * @param id the id of the client removed
     */
    public void removeSingleServer(int id){
        server.removeSingleServer(id);
    }

    /***********************************************************************************
     * VIEW
     ************************************************************************************/

    /**
     * Display the incoming messages from the clients on the GUI
     *
     * @param str  String to display
     */
    public void displayMessage(String str){
        this.frame.setIncomingAreaText(str);
    }

    /**
     * Display the number of clients currently online
     *
     * @param n the number of online clients
     */
    public void setNumberOfOnlineClients(int n){
        this.frame.changeNumberOfClientsOnline(n);
    }

}
