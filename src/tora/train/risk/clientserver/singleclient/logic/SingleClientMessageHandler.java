package tora.train.risk.clientserver.singleclient.logic;

import tora.train.risk.Arena;
import tora.train.risk.Player;
import tora.train.risk.Territory;
import tora.train.risk.clientserver.common.Controller;
import tora.train.risk.clientserver.common.Message;
import tora.train.risk.clientserver.common.MessageHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Processes messages received from the server
 *
 * Created by Andrea on 7/16/2015.
 */
public class SingleClientMessageHandler implements MessageHandler {
    private SingleClientController controller;

    @Override
    public void handleMessage(Message msg) {
        switch(msg.getType()){
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
                int id=(int)msg.getElementAt(0);
                System.out.println("Client has assigned ID " + id );
                controller.setClientIdentity(id);
                controller.setStatus(Status.CONNECTED);
                break;
            }
            case START:{
                Arena arena=(Arena)msg.getElementAt(0);
                controller.initializeMap(arena);
                break;
            }
            case NEW_PLAYER_CONNECTED:{
                String name=String.valueOf(msg.getElementAt(0));
                controller.addNewPlayerToCombo(name);
                break;
            }
            case PLAYER_DISCONNECTED:{
                String name=String.valueOf(msg.getElementAt(0));
                controller.removePlayerFromCombo(name);
                break;
            }
            case ONLINE_PLAYERS:{
                int n=(int)msg.getElementAt(0);
                ArrayList<String> names=new ArrayList<String>();
                for (int i=1; i<n+1; i++){
                    names.add(String.valueOf(msg.getElementAt(i)));
                }
                controller.addPlayersToCombo(names);
                break;
            }
            case RESTRICT_CONNECTION:{
                int maxNumberOfClients=(int)msg.getElementAt(0);
                controller.restrictConnection(maxNumberOfClients);
                controller.setStatus(Status.NOT_CONNECTED);
                controller.stopRunning();
                break;
            }
            case YOUR_TURN:{
                Player currentPlayer=(Player)msg.getElementAt(0);
                List<String> restOfPlayersInOrder=(List<String>)msg.getElementAt(1);
                List<Territory> territoriesOfCurrentPlayer=(List<Territory>)msg.getElementAt(2);
                controller.establishTurn(currentPlayer, restOfPlayersInOrder, territoriesOfCurrentPlayer);
                break;
            }
            case REINFORCE_ACCEPTED:{
                System.out.println("In handler, Client received:" + msg);
                String playerName=String.valueOf(msg.getElementAt(0));
                int reinfLeft=(int)msg.getElementAt(1);
                int x=(int)msg.getElementAt(2);
                int y=(int)msg.getElementAt(3);
                int numOfUnits=(int)msg.getElementAt(4);
                controller.applyReinforcement(playerName, reinfLeft, x, y, numOfUnits);
                break;
            }
            case REINFORCE_DENIED:{
                controller.denyReinforcement();
                break;
            }
            case REINFORCE_END: {
                System.out.println("Client asked to move on " + msg);
                int reinforcements=(int)msg.getElementAt(0);
                controller.updateReinforcementLabel(reinforcements);
                break;
            }
            case START_TRANSFER:{
                controller.enableMoveAndAttackView();
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
