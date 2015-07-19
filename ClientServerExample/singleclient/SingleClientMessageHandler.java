package singleclient;

import common.Controller;
import common.Message;
import common.MessageHandler;

/**
 * Processes messages received from the server
 *
 * Created by Andrea on 7/16/2015.
 */
public class SingleClientMessageHandler implements MessageHandler {
    private SingleClientController controller;

    @Override
    public void handleMessage(Message msg) {
        switch(msg.getOperation()){
            case STOP:{
                System.out.println("In SingleClientMessageHandler() client is asked to stop");
                controller.stopRunning();
                break;
            }
            case GLOBAL:{
                controller.displayMessage(msg);
                break;
            }
            case IDENTITY:{
                int id=(int)msg.getContent().get(0);
                controller.setClientIdentity(id);
                System.out.println("Client has assigned ID " + id );
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller=(SingleClientController)controller;
    }
}
