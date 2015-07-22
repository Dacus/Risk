package tora.train.risk.clientserver.singleclient;

import tora.train.risk.clientserver.common.Controller;
import tora.train.risk.clientserver.common.Message;
import tora.train.risk.clientserver.common.MessageHandler;

import java.util.ArrayList;

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
                System.out.println("Client has assigned ID " + id );
                controller.setClientIdentity(id);
                controller.setClientConnected(true);
                break;
            }
            case START:{
                controller.displayMessage(msg);
                break;
            }
            case NEW_PLAYER_CONNECTED:{
                String name=String.valueOf(msg.getContent().get(0));
                controller.addNewPlayerToCombo(name);
                break;
            }
            case PLAYER_DISCONNECTED:{
                String name=String.valueOf(msg.getContent().get(0));
                controller.removePlayerFromCombo(name);
                break;
            }
            case ONLINE_PLAYERS:{
                int n=(int)msg.getContent().get(0);
                ArrayList<String> names=new ArrayList<String>();
                for (int i=1; i<n+1; i++){
                    names.add(String.valueOf(msg.getContent().get(i)));
                }
                controller.addPlayersToCombo(names);
                break;
            }
            case RESTRICT_CONNECTION:{
                int maxNumberOfClients=(int)msg.getContent().get(0);
                controller.restrictConnection(maxNumberOfClients);
                controller.setClientConnected(false);
                controller.stopRunning();
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
