package tora.train.risk.Test;

import org.junit.BeforeClass;
import tora.train.risk.Arena;
import tora.train.risk.BonusStrategy;
import tora.train.risk.RiskDefaultBonus;

/**
 * Created by intern on 7/20/15.
 */
public class TestPlayerBonus {
    private static Arena defaultArena;
    private static BonusStrategy defaultStrat;

    /**
     * Initialize the arena and strategy we are testing
     */
    @BeforeClass
    public static void init() {
        defaultArena = new Arena();
        defaultStrat = new RiskDefaultBonus(defaultArena);
    }

    //TODO test the player giving strategy
}
