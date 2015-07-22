package tora.train.risk;

/** Interface to be implemented for eventually computing the bonuses in the
 * reinforcement phase
 */
public interface BonusStrategy {
    /**
     * Compute the player bonus depending on owned continents & territories
     * player == CPU || player == null returns 0
     * player having no territory returns 0
     *
     * @param player the player to compute bonus for
     * @return the reinforcements it is allowed to make this turn
     */
    int computePlayerBonus(Player player);
}
