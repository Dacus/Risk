package tora.train.risk;

import java.util.List;

/**
 * Created by intern on 7/20/15.
 */
public class RiskDefaultBonus implements BonusStrategy {
    private static final int BONUS_FOR_GROUP = 1;
    private static final int GROUP_SIZE = 3;
    private static final int DEFAULT_BONUS = 5;
    private Arena arena;

    public RiskDefaultBonus(Arena arena) {
        this.arena = arena;
    }

    /**
     * Default bonus computer with the rules from "Risk for dummies"
     *
     * @param player player to compute bonus
     * @return the bonus it has at the start of the turn
     */
    @Override
    public int computePlayerBonus(Player player) {
        if (player == null || player.equals(Player.CPU_MAP_PLAYER))
            return 0;

        int bonus = DEFAULT_BONUS, territories = 0;
        territories = arena.getOwnedTerritories(player).size();
        if (territories == 0)
            return 0;

        List<Continent> continents = arena.getContinents();
        int N = arena.getXSize(), M = arena.getYSize();
        for (Continent continent : continents) {
            Player owner = continent.getOwnerIfExists();
            if (owner != null && owner.equals(player)) {
                    bonus += continent.getType().getBonus();
                }
        }
        bonus += (territories / GROUP_SIZE) * BONUS_FOR_GROUP;

        return bonus;
    }
}
