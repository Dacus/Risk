package tora.train.risk;

import java.util.Random;

/**
 * Created by intern on 7/17/15.
 */
public class RiskCombatStrategy implements CombatStrategy {
    @Override
    public boolean solveAttack(int nrOfAttackingUnits, Territory source, Territory destination) {
        int defendingUnits = destination.getUnitNr();
        int attackingKills = calculateKills(CombatType.ATTACK, nrOfAttackingUnits);
        int defendingKills = calculateKills(CombatType.DEFEND, defendingUnits);
        int unitsOnAttackingTerritory = source.getUnitNr();
        Player initiator = source.getOwner();

        if (attackingKills >= defendingUnits) {
            if (defendingKills < nrOfAttackingUnits) {
                changeOwnershipOfTerritory(nrOfAttackingUnits, destination, initiator, defendingKills);
                source.setUnitNr(unitsOnAttackingTerritory - nrOfAttackingUnits);
                return true;
            } else {
                makeTerritoryNeutral(destination);
                source.setUnitNr(unitsOnAttackingTerritory - nrOfAttackingUnits);
                return true;
            }
        } else {
            destination.setUnitNr(defendingUnits - attackingKills);
            if (defendingKills > nrOfAttackingUnits)
                defendingKills = nrOfAttackingUnits;
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
        destination.setUnitNr(0);
        destination.setOwner(Player.CPU_MAP_PLAYER);
        destination.getContinent().addPlayer(Player.CPU_MAP_PLAYER);
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
        destination.setUnitNr(nrOfAttackingUnits - defendingKills);
        destination.getContinent().addPlayer(player);
    }

    /**
     * Method that randomizes the number of units that will be killed
     * in a fight, depending of they either defend or attack
     *
     * @param type          the combat type the units are engaged in
     * @param numberOfUnits the number of units that are involved
     * @return the number of kills it delivered
     */
    private int calculateKills(CombatType type, int numberOfUnits) {
        Random randomizer = new Random();
        int kills = 0;
        for (int i = 0; i < numberOfUnits; i++) {
            int chance = randomizer.nextInt(100);
            if (chance < type.winChance) {
                kills++;
            }
        }
        System.out.println(kills + " killed");
        return kills;
    }

    /**
     * Enum with the type of combats we encounter on the map
     */
    private static enum CombatType {
        DEFEND(70),
        ATTACK(60);

        private final int winChance;

        /**
         * @param winChance < 100% percentage that represents what is the chance of one unit killing
         *                  another in a fight of this type
         */
        private CombatType(int winChance) {
            this.winChance = winChance;
        }
    }
}
