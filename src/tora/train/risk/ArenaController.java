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
     * @param nrOfUnits how many units to move (value > 0)
     * @param init      the territory's coordinates from which the player moves (territory must belong to player)
     * @param dest      the territory's coordinates to which the player moves
     *                  (if territory does not belong to player = attack)
     * @param player    the player that want to move
     * @return true if player can move the specified units from init to dest
     */
    public boolean moveUnits(int nrOfUnits, Point init, Point dest, Player player) {
        if (!ArenaCommandValidator.validateMove(arena, nrOfUnits, init, dest, player)) {
            return false;
        }

        if (arena.getTerritoryFrom(dest).owner != player) {
            resolveAttack();
        }
        return true;
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

    private void resolveAttack() {

    }

}
