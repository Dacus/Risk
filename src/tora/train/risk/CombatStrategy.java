package tora.train.risk;

/**
 * Interface that tells us how to solve an attack on the map
 * Created by intern on 7/17/15.
 */
public interface CombatStrategy {
    /**
     * @param nrOfAttackingUnits how many units does player use to attack an enemy territory.
     *                           (0 < value <= nr of units available)
     * @param source             the territory  from which the player initiates the attack. (belongs to player)
     * @param destination        the territory that player wants to attack. (does not belong to player)
     */
    public boolean solveAttack(int nrOfAttackingUnits, Territory source, Territory destination);
}
