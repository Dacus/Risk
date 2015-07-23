package tora.train.risk.clientserver.singleserver;

import tora.train.risk.clientserver.common.Controller;
import tora.train.risk.clientserver.common.Message;
import tora.train.risk.clientserver.common.MessageHandler;
import tora.train.risk.clientserver.common.MessageType;

/**
 * Class that processes the messages received from the client
 *
 * Created by Andrea on 7/16/2015.
 */
public class SingleServerMessageHandler implements MessageHandler {
    private SingleServerController serverController;

    @Override
    public void handleMessage(Message message) {
        System.out.println("Message received:" + message);
        MessageType type=message.getType();
        switch(type){
            case STOP:{
                int clientId=(int)message.getElementAt(0);
                System.out.println("Client " + clientId + " requested stopping");
                serverController.stop(clientId);
                break;
            }
            case READY:{
                serverController.notifyClientIsReady();
                break;
            }
            case USER:{
                String received=message.getElementAt(0).toString();
                serverController.displayMessage(received);
                break;
            }
            default:{
                System.out.println("Default");
               break;
            }
        }
    }

    @Override
    public void setController(Controller controller) {
        this.serverController=(SingleServerController)controller;
    }
}
