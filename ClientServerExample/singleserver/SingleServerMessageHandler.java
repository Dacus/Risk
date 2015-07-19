package singleserver;

import common.MessageHandler;
import common.Controller;
import common.Message;
import common.MessageTag;

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
        MessageTag tag=message.getOperation();
        switch(tag){
            case STOP:{
                int clientId=(int)message.getContent().get(0);
                System.out.println("Client " + clientId + " requested stopping");
                serverController.stop(clientId);
                break;
            }
            case USER:{
                System.out.println("Server received user message");
                String received=message.getContent().get(0).toString();
                serverController.displayMessage(received);
                break;
            }
            default:{
               serverController.sendMessage(message);
               break;
            }
        }
    }

    @Override
    public void setController(Controller controller) {
        this.serverController=(SingleServerController)controller;
    }
}
