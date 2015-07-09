package tora.train.risk;

import java.awt.*;

/**
 * Created by intern on 7/9/15.
 */
public class ArenaCommandValidator {
    public static Boolean validateMove(Arena arena, int nrOfUnits, Point init, Point dest, Player player) {
        if(!checkOwnerIsValid(arena, init, player)) {
            return false;
        } else if (!checkUnitsIsValid(arena, init, nrOfUnits, player)) {
            return false;
        } else if(!checkMovePosible(arena, init, dest, player)) {
            return false;
        } else {
            return true;
        }
    }

    private static Boolean checkOwnerIsValid(Arena arena, Point init, Player player) {
        return arena.getAtCoordinate(init).owner == player;
    }

    private static Boolean checkUnitsIsValid(Arena arena, Point init, int nrOfUnits, Player player) {
        return arena.getAtCoordinate(init).unitNr >= nrOfUnits;
    }

    private static Boolean checkMovePosible(Arena arena, Point init, Point dest, Player player) {
        Boolean valid = true;
        if((init.x - dest.x + init.y - init.y) > 1) {
            valid = false;
        } else if(init.x < 1 && init.y < 1 && dest.x < 1 && dest.y < 1) {
            valid = false;
        }
        return valid;
    }
}
