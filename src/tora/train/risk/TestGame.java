package tora.train.risk;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by intern on 7/13/15.
 */
public class TestGame {
    private ArenaController arenaController;
    private List<Player> players;
    private static final int NR_OF_PLAYERS=2;

    @Before
    public void init(){
        arenaController=new ArenaController();
        players=new ArrayList<Player>();
        for (int i=0; i<NR_OF_PLAYERS; i++) {
            Player p=new Player("Player"+i);
            players.add(p);
            arenaController.addPlayer(p);
        }
    }

    @Test
    public void testDistributingReinforcements(){
        arenaController.distributePlayers(5, 1);
        for (int i=0; i<arenaController.getNumberOfPlayers(); i++){
            Player p=arenaController.getPlayerByIndex(i);
            if (!p.equals(Player.CPU_MAP_PLAYER)) {
                List<Territory> list = arenaController.getArena().getOwnedTerritories(p);
                assertThat(list.size(), equalTo(5));
            }
        }
    }

    /**
     * Test the reinforcement after initial territory distribution.
     * While it can, it puts units on every territory owned by player. (It tries an even distribution)
     * First territories should have more reinforcements than last territories owned
     * (if units cannot be distributed evenly across the territories)
     */
    @Test
    public void testReinforcementAfterInitialTerritoryDistribution() {
        arenaController.distributePlayers(5, 1);
        Arena arena = arenaController.getArena();

        /*for (Player currentPlayer : players) {
            //it's currentPlayer's turn, ignore CPU
            if (!players.equals(Player.CPU_MAP_PLAYER)) {
                int allowedReinforcements = currentPlayer.getReinforcements();

                List<Territory> territoriesOwnedByPlayer = arena.getOwnedTerritories(currentPlayer);
                for (int i = 0; i < territoriesOwnedByPlayer.size(); i++) {
                    Territory currentTerritory = territoriesOwnedByPlayer.get(i);
                }
            }
        }*/
    }

    /**
     * Test the transfer of units between territories belonging to the same player.
     * Check if the transfer is done correctly by verifying the number of units on each territory involved
     */
    @Test
    public void testTransferUnitsNormal(){
        int unitsToTransfer=2;
        Point fromP=new Point(1,1);
        Point toP=new Point(1,2);

        Player player=players.get(0);

        Territory fromT=arenaController.getArena().getTerritoryAtCoordinate(fromP);
        Territory toT=arenaController.getArena().getTerritoryAtCoordinate(toP);
        fromT.setOwner(player);
        fromT.setUnitNr(4);
        toT.setOwner(player);
        toT.setUnitNr(2);

        int unitsOnSource=fromT.getUnitNr();
        int unitsOnDest=toT.getUnitNr();

        //(int nrOfAttackingUnits, Point init, Point dest, Player player) {
        arenaController.moveUnits(unitsToTransfer, fromP, toP, player);

        assertThat(fromT.getUnitNr(), equalTo(unitsOnSource-unitsToTransfer));
        assertThat(toT.getUnitNr(), equalTo(unitsOnDest + unitsToTransfer));

    }

    /**
     * Test the transfer of units between territories belonging to the same player when the number of units on
     * the source territory is the minimum allowed (1), so the transfer is not possible
     */
    @Test
    public void testTransferUnitsImpossible1(){
        int unitsToTransfer=2;
        Point fromP=new Point(1,1);
        Point toP=new Point(1,2);

        Player player=players.get(0);

        Territory fromT=arenaController.getArena().getTerritoryAtCoordinate(fromP);
        Territory toT=arenaController.getArena().getTerritoryAtCoordinate(toP);
        fromT.setOwner(player);
        fromT.setUnitNr(1);
        toT.setOwner(player);
        toT.setUnitNr(2);

        int unitsOnSource=fromT.getUnitNr();
        int unitsOnDest=toT.getUnitNr();

        //(int nrOfAttackingUnits, Point init, Point dest, Player player) {
        arenaController.moveUnits(unitsToTransfer, fromP, toP, player);

        //transfer not possible. The number of units remains unchanged
        assertThat(fromT.getUnitNr(), equalTo(unitsOnSource));
        assertThat(toT.getUnitNr(), equalTo(unitsOnDest));
    }


    /**
     * Helper methods (if something does not work)
     */
    private void printOwners(){
        for (int i=0; i<11; i++)
            for(int j=0; j<11; j++)
                System.out.println(arenaController.getArena().getTerritoryAtCoordinate(i,j).getOwner().getName());
    }

    private void printTerritoryListOwners(List<Territory> list){
        for (int i=0; i<list.size(); i++){
            if (!list.get(i).getOwner().equals(Player.CPU_MAP_PLAYER))
                System.out.println(list.get(i).getOwner());
        }
        System.out.println();
    }
}
