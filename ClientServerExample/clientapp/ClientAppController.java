package clientapp;

import singleclient.SingleClientController;
import singleclient.SingleClientFrame;
import singleclient.SingleClientMessageHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Application for the client machine.
 * It provides a Graphical User interface where new clients can be added.
 *
 * To add a new client: insert the name of the client then hit "New Client" button.
 *
 * Created by Andrea on 7/17/2015.
 */
public class ClientAppController {
    private ClientAppFrame frame;
    private SingleClientMessageHandler clientMsgHandler;

    public ClientAppController(){
        //initialize fields
        this.clientMsgHandler=new SingleClientMessageHandler();
        this.frame=new ClientAppFrame();

        //add button listeners
        this.frame.setNewClientButtonListener(new AddClientAction());
    }

    /**
     * Action assigned to the "New Client" button to create a new Client thread
     */
    class AddClientAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            //get text from nameField
            String clientName=frame.getClientName();

            if (!clientName.equals("")) {

                //create a new window for the client. Each window has its own associated controller
                SingleClientFrame clientFrame = new SingleClientFrame(clientName);
                SingleClientController singleClientController = new SingleClientController(clientFrame, clientMsgHandler);

                //clear the nameField
                frame.clearNameField();
            }
            else
                frame.showOptionPanel("Please enter your name!");
        }
    }
}
