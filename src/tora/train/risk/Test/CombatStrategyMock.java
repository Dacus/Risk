package tora.train.risk.Test;

import tora.train.risk.CombatStrategy;
import tora.train.risk.Player;
import tora.train.risk.Territory;

/** Mock class for testing purposes
 * Created by intern on 7/17/15.
 */
public class CombatStrategyMock implements CombatStrategy {
    private static int DEFENCE_KILLS = 0;
    private static int ATTACKING_KILLS = 0;

    public static void setDefenceKills(int kills) {
        if (kills < 0)
            throw new IllegalArgumentException();
        else DEFENCE_KILLS = kills;
    }

    public static void setAttackingKills(int kills) {
        if (kills < 0)
            throw new IllegalArgumentException();
        else ATTACKING_KILLS = kills;
    }
    @Override
    public boolean solveAttack(int nrOfAttackingUnits, Territory source, Territory destination) {
        int defendingUnits = destination.getUnitNr();
        int attackingKills = ATTACKING_KILLS;
        int defendingKills = DEFENCE_KILLS;
        int unitsOnAttackingTerritory = source.getUnitNr();
        Player initiator = source.getOwner();

        if (attackingKills >= defendingUnits) {
            if (defendingKills < nrOfAttackingUnits) {
                changeOwnershipOfTerritory(nrOfAttackingUnits, destination, initiator, defendingKills);
                source.setMovableUnits(unitsOnAttackingTerritory - nrOfAttackingUnits);
                source.setUnitNr(unitsOnAttackingTerritory - nrOfAttackingUnits);
                return true;
            } else {
                makeTerritoryNeutral(destination);
                source.setMovableUnits(unitsOnAttackingTerritory - nrOfAttackingUnits);
                source.setUnitNr(unitsOnAttackingTerritory - nrOfAttackingUnits);
                return true;
            }
        } else {
            destination.setMovableUnits(defendingUnits - attackingKills);
            destination.setUnitNr(defendingUnits - attackingKills);
            if (defendingKills > nrOfAttackingUnits)
                defendingKills = nrOfAttackingUnits;
            source.setMovableUnits(unitsOnAttackingTerritory - nrOfAttackingUnits);
            source.setUnitNr(unitsOnAttackingTerritory - defendingKills);
            return false;
        }
    }


    /**
     * Marginal case when the territory becomes neutral as the attacked could not
     * conquer it but it killed all the units inside it
     *
     * @param destination the territory on which this happened
     */
    private void makeTerritoryNeutral(Territory destination) {
        destination.setMovableUnits(0);
        destination.setUnitNr(0);
        destination.setOwner(Player.CPU_MAP_PLAYER);
        destination.getContinent().addPlayer(Player.CPU_MAP_PLAYER);
        destination.setMovableUnits(0);
    }

    /**
     * Changes owner of a territory, after an attack with all his consequences.
     *
     * @param nrOfAttackingUnits how many units attacked the territory
     * @param destination        the territory that was attacked
     * @param player             the player that won the territory
     * @param defendingKills     how many units were on the territory before the attack
     */
    private void changeOwnershipOfTerritory(int nrOfAttackingUnits, Territory destination, Player player, int defendingKills) {
        destination.setOwner(player);
        destination.setMovableUnits(0);
        destination.setUnitNr(nrOfAttackingUnits - defendingKills);
        destination.getContinent().addPlayer(player);
    }

}
