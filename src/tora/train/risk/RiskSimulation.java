package tora.train.risk;

import java.awt.*;
import java.util.List;

/**
 * Created by intern on 7/14/15.
 */
public class RiskSimulation {

    ArenaController arenaController=new ArenaController();

    private void moveUp(int unitsNr,Territory src, Player player){
        Point init=src.getCoordinates();
        Point dest=new Point(init.x-1,init.y);
        arenaController.moveUnits(unitsNr,init,dest,player);
    }

    private void moveDown(int unitsNr,Territory src, Player player){
        Point init=src.getCoordinates();
        Point dest=new Point(init.x+1,init.y);
        arenaController.moveUnits(unitsNr,init,dest,player);
    }

    private void moveLeft(int unitsNr,Territory src, Player player){
        Point init=src.getCoordinates();
        Point dest=new Point(init.x,init.y-1);
        arenaController.moveUnits(unitsNr,init,dest,player);
    }

    private void moveRight(int unitsNr,Territory src, Player player){
        Point init=src.getCoordinates();
        Point dest=new Point(init.x,init.y+1);
        arenaController.moveUnits(unitsNr,init,dest,player);
    }

    private void doSmth(){
        //consider the order is the entering one
        //TO DO -> Server determine random/circular order

        System.out.println("Initial status");
        System.out.println(arenaController.getArena().toString());


        System.out.println("Players are distributed");
        for (int i=1;i<=NR_PLAYERS;i++) {
            arenaController.addPlayer(new Player("Player" + i));
        }
        arenaController.distributePlayers(5,1);
        System.out.println(arenaController.getArena().toString());



        System.out.println("Players reinforced each territory with 4 units");
        for (int i=1;i<=NR_PLAYERS;i++){
            List<Territory> teritories= arenaController.getArena().getOwnedTerritories(arenaController.getPlayerByIndex(i));
            for (Territory t:teritories){
                arenaController.reinforce(3,t,arenaController.getPlayerByIndex(i));
            }
        }
        System.out.println(arenaController.getArena().toString());


        Territory t=arenaController.getArena().getOwnedTerritories(arenaController.getPlayerByIndex(1)).get(1);
        System.out.println("Player1 move up 2 units from "+t.getCoordinates().toString());
        moveUp(2,t,arenaController.getPlayerByIndex(1));
        System.out.println(arenaController.getArena().toString());
    }

    private static int NR_PLAYERS=2;

    public static void main(String[] args) {
        RiskSimulation risk=new RiskSimulation();
        risk.doSmth();
    }
}
