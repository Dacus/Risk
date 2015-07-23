package tora.train.risk.GUI;

import tora.train.risk.ArenaController;
import tora.train.risk.Player;
import tora.train.risk.Territory;

import java.awt.*;
import java.util.List;

/**
 * Created by intern on 7/20/15.
 */
public class StaticInformations {
    private static ArenaController arenaController;

    public static Player getCurrentPlayer() {
        return arenaController.getCurrentPlayer();
    }

    public static int getXsize(){
        return arenaController.getArena().getXSize();
    }

    public static int getYsize(){
        return arenaController.getArena().getYSize();
    }

    public static void setArenaController(ArenaController arenaController){
        StaticInformations.arenaController=arenaController;
    }

    public static void addPlayer(Player p){
        arenaController.addPlayer(p);
    }

    public static Player getPlayerByIndex(int i){
        return arenaController.getPlayerByIndex(i);
    }

    public static Territory getTerritoryAtCoordinate(int x, int y){
        return arenaController.getArena().getTerritoryAtCoordinate(x,y);
    }

    public static void distributePlayers(){
        arenaController.distributePlayers(5,1);
    }

    public static List<Territory> getPlayersTerritories(Player p){
        return arenaController.getArena().getOwnedTerritories(p);
    }

    public static void endCurrentPlayerTurn() {
        arenaController.endCurrentPlayerTurn();
    }

    public static void submitReinforcements(Point position, int value) throws Exception{
        if  (!arenaController.reinforce(value, position, StaticInformations.getCurrentPlayer()))
            throw new IllegalArgumentException();
    }

    public static int getPlayersNumber(){
        return arenaController.getNumberOfPlayers();
    }
}
