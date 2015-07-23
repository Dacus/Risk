package tora.train.risk.clientserver.clientapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that controls the Graphical User Interface of the application running on the client machine.
 * It allows the creation of new ClientAppThreads.
 *
 * Created by Andrea on 7/17/2015.
 */
public class ClientAppController {
    private ClientAppFrame clientAppFrame;

    public ClientAppController(ClientAppFrame frame){
        //initialize fields
        this.clientAppFrame=frame;

        //add button listeners
        this.clientAppFrame.setNewClientButtonListener(new AddClientAction());
    }

    /**
     * Action assigned to the "New Client" button.
     */
    class AddClientAction implements ActionListener{

        /**
         * The method takes the String inserted by the client (client's name) and stores it in the ¨"clientName" variable.
         * If "clientName" is an empty String, an informative message is displayed.
         * Otherwise, a new ClientAppThread is created and started.
         * The String inserted by the client is erased.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            //get text from nameField
            String clientName=clientAppFrame.getClientName();

            if (!clientName.equals("")) {
                //create a new window for the client. Each window has its own associated controller
                Thread th=new ClientAppThread(clientName);
                th.start();

                //clear the nameField
                clientAppFrame.clearNameField();
            }
            else
                clientAppFrame.showOptionPanel("Please enter your name!");
        }
    }
}
