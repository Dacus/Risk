package tora.train.risk.Test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tora.train.risk.Arena;
import tora.train.risk.ArenaController;
import tora.train.risk.Player;
import tora.train.risk.Territory;

import java.awt.*;
import java.util.*;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by intern on 7/13/15.
 */
public class TestGame {
    private static final int NR_OF_PLAYERS = 2;
    private ArenaController arenaController;
    private List<Player> players;

    @Before
    public void init(){
        arenaController=new ArenaController();
        players = new ArrayList<>();
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

    @Test
    public void testDistributingReinforcements_WhenTooManyTerritoriesPerPlayer_ExpectedDistributionFail() {
        int nrOfDistributableTerritories = arenaController.getArena().getDistributableTerritories().size();
        int nrOfPlayers = players.size() - 1;
        int territoriesPerPlayer = nrOfDistributableTerritories / nrOfPlayers + 1;
        assertThat(arenaController.distributePlayers(territoriesPerPlayer, 1), is(false));
    }

    /**
     * Test the order of players turns in the game.
     */
    @Test
    public void testPlayersTurnsOrder() {
        ArenaController localArenaController = new ArenaController();
        localArenaController.addPlayer(new Player("Lorand"));
        localArenaController.addPlayer(new Player("Ervin"));
        localArenaController.addPlayer(new Player("Andrea"));
        localArenaController.addPlayer(new Player("Florin"));
        localArenaController.addPlayer(new Player("Isabela"));
        localArenaController.addPlayer(new Player("Paul"));
        localArenaController.addPlayer(new Player("Alex"));

        //game did not begin so you cannot end current player's turn
        assertThat(localArenaController.endCurrentPlayerTurn(), is(false));

        //the game started, 3 rounds in this game, each player has only one turn per round
        for (int round = 1; round <= 3; round++) {
            //contains players that finished their turns
            Set<Player> finishedTurns = new HashSet<>();
            for (int turn = 1; turn <= 7; turn++) {
                //who's next?
                Player player = localArenaController.getCurrentPlayer();
                //calling getCurrentPlayer twice should get the same result
                Player samePlayer = localArenaController.getCurrentPlayer();
                assertThat(player, equalTo(samePlayer));
                //no CPU player allowed
                assertNotEquals(Player.CPU_MAP_PLAYER, player);
                //no duplicate turns allowed
                assertThat(finishedTurns.contains(player), is(false));
                //player finishes turn
                finishedTurns.add(player);
                assertThat(localArenaController.endCurrentPlayerTurn(), is(true));
            }
            //all players must have finished their turns
            assertThat(localArenaController.endCurrentPlayerTurn(), is(false));
        }
    }

    /**
     * Tests if a given point is on the map or not.
     */
    @Test
    public void testCoordinatesAreOnTheMap() {
        Arena arena = arenaController.getArena();
        Arena.CoordinatesCalculator coordinatesCalculator = arena.new CoordinatesCalculator();

        final int sizeX = arena.getXSize();
        final int sizeY = arena.getYSize();

        Point minusXY = new Point(-1, -1);
        Point minusX = new Point(-1, 0);
        Point minusY = new Point(0, -1);
        Point origin = new Point(0, 0);
        Point outsideX = new Point(sizeX, 0);
        Point outsideY = new Point(0, sizeY);
        Point outsideXY = new Point(sizeX, sizeY);
        Point outsideXminusY = new Point(sizeX, -1);
        Point outsideYminusX = new Point(-1, sizeY);

        assertThat(((sizeX >= 1) && (sizeY >= 1)), is(true));
        Point insideXY = new Point(sizeX - 1, sizeY - 1);

        assertThat(coordinatesCalculator.coordinatesAreOnTheMap(insideXY), is(true));
        assertThat(coordinatesCalculator.coordinatesAreOnTheMap(minusXY), is(false));
        assertThat(coordinatesCalculator.coordinatesAreOnTheMap(minusX), is(false));
        assertThat(coordinatesCalculator.coordinatesAreOnTheMap(minusY), is(false));
        assertThat(coordinatesCalculator.coordinatesAreOnTheMap(origin), is(true));
        assertThat(coordinatesCalculator.coordinatesAreOnTheMap(outsideX), is(false));
        assertThat(coordinatesCalculator.coordinatesAreOnTheMap(outsideY), is(false));
        assertThat(coordinatesCalculator.coordinatesAreOnTheMap(outsideXY), is(false));
        assertThat(coordinatesCalculator.coordinatesAreOnTheMap(outsideXminusY), is(false));
        assertThat(coordinatesCalculator.coordinatesAreOnTheMap(outsideYminusX), is(false));
    }

    /**
     * Tests moving of units from a territory that is not owned by the player that moves.
     */
    @Test
    public void testMoveUnitsFromAlienTerritory() {
        //TODO: Lorand
        /*
        Queue<Player> playerQueue = arenaController.getPlayersQueue();
        Player me = playerQueue.remove();
        Player you = playerQueue.remove();
        List<Territory> yourTerritories = arenaController.getArena().getOwnedTerritories(you);
        assertThat(yourTerritories, not(empty()));
        Territory yourTerritory = yourTerritories.get(0);
        Territory neighbourTerritory = yourTerritory
        int nrOfAttackingUnits = yourTerritory.getUnitNr();

        assertThat(arenaController.moveUnits(nrOfAttackingUnits - 1, yourTerritory
        */
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

        for (Player currentPlayer : players) {
            //it's currentPlayer's turn, ignore CPU
            if (!players.equals(Player.CPU_MAP_PLAYER)) {
                int allowedReinforcements = currentPlayer.getReinforcements();
                List<Territory> territoriesOwnedByPlayer = arena.getOwnedTerritories(currentPlayer);
                assertFalse(territoriesOwnedByPlayer.isEmpty());

                int reinforcementsPerTerritory = allowedReinforcements / territoriesOwnedByPlayer.size();
                for (int i = 0; i < territoriesOwnedByPlayer.size(); i++) {
                    Territory currentTerritory = territoriesOwnedByPlayer.get(i);
                    assertTrue(arenaController.reinforce(reinforcementsPerTerritory, currentTerritory, currentPlayer));
                    assertEquals(allowedReinforcements - (i+1) * reinforcementsPerTerritory, currentPlayer.getReinforcements());
                }

                //if player has remaining reinforcements, put one on every territory, starting with first owned territory
                int remainingReinforcements = allowedReinforcements % territoriesOwnedByPlayer.size();
                assertEquals(remainingReinforcements, currentPlayer.getReinforcements());
                for (int i = 0; i < remainingReinforcements; i++) {
                    Territory currentTerritory = territoriesOwnedByPlayer.get(i);
                    assertTrue(arenaController.reinforce(1, currentTerritory, currentPlayer));
                    assertEquals(remainingReinforcements - (i+1), currentPlayer.getReinforcements());
                }

                //try to reinforce without reinforcements
                assertEquals(0, currentPlayer.getReinforcements());
                assertFalse(arenaController.reinforce(1, territoriesOwnedByPlayer.get(0), currentPlayer));

                //check number of units on each territory owned by player
                for (int i = 0; i < territoriesOwnedByPlayer.size(); i++) {
                    if (i < remainingReinforcements)
                        assertEquals(reinforcementsPerTerritory + 2, territoriesOwnedByPlayer.get(i).getUnitNr());
                    else
                        assertEquals(reinforcementsPerTerritory + 1, territoriesOwnedByPlayer.get(i).getUnitNr());
                }
            }
        }
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
        fromT.setMovableUnits(4);
        toT.setOwner(player);
        toT.setUnitNr(2);
        toT.setMovableUnits(2);

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
        for (Territory territory : list) {
            if (!territory.getOwner().equals(Player.CPU_MAP_PLAYER))
                System.out.println(territory.getOwner());
        }
        System.out.println();
    }
}
