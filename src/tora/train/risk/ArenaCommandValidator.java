package tora.train.risk;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * Created by intern on 7/9/15.
 */
public class ArenaCommandValidator {

    /**
     * Checks whether player can move units from a territory to another.
     *
     * @param arena     arena of the game
     * @param nrOfUnits how many units the player wants to move
     * @param init      coordinates of the territory from which the player wants to move
     * @param dest      coordinates of the territory on which the player wants to move
     * @param player    the player that moves units
     * @return true if move can be done
     */
    public static Boolean validateMove(Arena arena, int nrOfUnits, Point init, Point dest, Player player) {
        if (!checkOwnerIsValid(arena, init, player)) {
            return false;
        } else if (!checkUnitsIsValid(arena, init, nrOfUnits)) {
            return false;
        } else if (!checkMovePossible(arena, init, dest)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks whether the territory placed at init coordinates belongs to player.
     *
     * @param arena  arena of the game
     * @param init   coordinates of territory to check
     * @param player supposed owner of the territory
     * @return true if territory belongs to player
     */
    private static Boolean checkOwnerIsValid(Arena arena, Point init, Player player) {
        return arena.getTerritoryAtCoordinate(init).getOwner().equals(player);
    }

    /**
     * Checks whether the territory placed at init coordinates has a specified number of units.
     *
     * @param arena     arena of the game
     * @param init      coordinates of territory to check
     * @param nrOfUnits supposed number of units on the territory
     * @return true if territory has the specified number of units
     */
    private static Boolean checkUnitsIsValid(Arena arena, Point init, int nrOfUnits) {
        return arena.getTerritoryAtCoordinate(init).getUnitNr() > nrOfUnits;
    }

    /**
     * Checks whether the territory placed at dest coordinates is adjacent to the territory at init coordinates.
     *
     * @param arena arena of the game
     * @param init  coordinates of territory from which the move is initiated
     * @param dest  coordinates of territory on which the player wants to move
     * @return
     */
    private static Boolean checkMovePossible(Arena arena, Point init, Point dest) {
        Boolean valid = true;
        int x = abs(init.x - dest.x);
        int y = abs(init.y - dest.y);
        if ((x + y) > 1 || (x == 0 && y == 0)) {
            valid = false;
        } else if (init.x < 0 || init.y < 0 || dest.x < 0 || dest.y < 0) {
            valid = false;
        }
        else if(init.x >= arena.getXSize() || init.y >= arena.getYSize() || dest.x >= arena.getXSize() || dest.y >= arena.getYSize())
            valid = false;
        return valid;
    }
}
