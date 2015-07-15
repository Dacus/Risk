package tora.train.risk;

import java.awt.*;
import java.util.List;

/**
 * Created by intern on 7/14/15.
 */
public class RiskSimulation {

    ArenaController arenaController=new ArenaController();

    private void moveUp(int unitsNr,Point src, Player player){
        Point dest=new Point(src.x+1,src.y);
        arenaController.moveUnits(unitsNr,src,);
    }

    private void moveDown(int unitsNr,Point src, Player player){
        Point dest=new Point(src.x-1,src.y);
        arenaController.moveUnits(unitsNr,src,);
    }

    private void moveLeft(int unitsNr,Point src, Player player){
        Point dest=new Point(src.x,src.y-1);
        arenaController.moveUnits(unitsNr,src,);
    }

    private void moveRight(int unitsNr,Point src, Player player){
        Point dest=new Point(src.x,src.y+1);
        arenaController.moveUnits(unitsNr,src,);
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

        //TODO
    }

    private static int NR_PLAYERS=2;

    public static void main(String[] args) {
        RiskSimulation risk=new RiskSimulation();
        risk.doSmth();
    }
}
