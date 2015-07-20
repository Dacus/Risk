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

    @Override
    public int computePlayerBonus(Player player) {
        int bonus = DEFAULT_BONUS, territories = 0;
        List<Continent> continents = arena.getContinents();
        int N = arena.getXSize(), M = arena.getYSize();
        for (Continent continent : continents) {
            try {
                if (continent.getOwnerIfExists().equals(player)) {
                    bonus += continent.getType().getBonus();
                }
            } catch (NullPointerException e) {
                //no owner exists
            }
        }
        territories = arena.getOwnedTerritories(player).size();

        if (territories == 0) {
            bonus = 0;
        } else {
            bonus += (territories / GROUP_SIZE) * BONUS_FOR_GROUP;
        }
        return bonus;
    }
}
