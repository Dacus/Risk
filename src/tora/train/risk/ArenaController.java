package tora.train.risk;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArenaController {
    private static Arena arena;
    private List<Player> players;

    public ArenaController() {
        //TODO
        players = new ArrayList<Player>();
        initArena();
    }

    /**
     * @param p player that wants to get the reinforcements
     * @return the number of units that p is allowed to use for reinforcement
     */
    public int getReinforcements(Player p) {
        //TODO
        return 0;
    }

    /**
     * Adds a player to the game.
     *
     * @param p player to add
     */
    public void addPlayer(Player p) {
        players.add(p);
    }

    /**
     * Moves units from a territory to another.
     *
     * @param nrOfAttackingUnits how many units to move (value > 0)
     * @param init      the territory's coordinates from which the player moves (territory must belong to player)
     * @param dest      the territory's coordinates to which the player moves
     *                  (if territory does not belong to player = attack)
     * @param player    the player that want to move
     * @return true if player can move the specified units from init to dest
     */
    public boolean moveUnits(int nrOfAttackingUnits, Point init, Point dest, Player player) {
        if (!ArenaCommandValidator.validateMove(arena, nrOfAttackingUnits, init, dest, player)) {
            return false;
        }

        if (arena.getTerritoryAtCoordinate(dest).owner != player) {
            resolveAttack(nrOfAttackingUnits, init, dest, player);
        } else {
            transferUnits(nrOfAttackingUnits, init, dest, player);
        }
        return true;
    }

    private void transferUnits(int nrOfAttackingUnits, Point init, Point dest, Player player) {
    }

    private void resolveAttack(int nrOfAttackingUnits, Point init, Point dest, Player player) {
        int defendingUnits = arena.getTerritoryAtCoordinate(dest).unitNr;
        int attackingKills = calculateKills("attack");
        int defendingKills = calculateKills("defend");

        if (attackingKills >= defendingUnits) {
            changeOwnershipOfTerritory(nrOfAttackingUnits, dest, player, defendingKills);
        } else {
            arena.getTerritoryAtCoordinate(dest).unitNr = defendingUnits - attackingKills;
        }

    }

    private void changeOwnershipOfTerritory(int nrOfAttackingUnits, Point dest, Player player, int defendingKills) {
        arena.getTerritoryAtCoordinate(dest).owner = player;
        arena.getTerritoryAtCoordinate(dest).unitNr = nrOfAttackingUnits - defendingKills;
        //TODO complete method
    }

    private int calculateKills(String typeOfCombat) {
        //TODO create method for both cases attacking and defending
        return 0;
    }

    /**
     * Initializes territories and places players on the map.
     */
    private void initArena() {
        //TODO
    }

    /**
     * Puts player's units on specified territory.
     *
     * @param nrOfUnits how many units to place on the territory (value > 0)
     * @param dest      the territory's coordinates where the units will be placed (territory must belong to player)
     * @param player    the player that puts his reinforcements
     * @return true if player can put his reinforcements on the specified territory
     */
    private boolean reinforce(int nrOfUnits, Point dest, Player player) {
        //TODO
        return false;
    }



}
