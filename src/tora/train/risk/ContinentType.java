package tora.train.risk;

import java.io.Serializable;

public class ContinentType implements Serializable{
    //Default map continent types From "Risk for dummies" map
    public static final ContinentType A = new ContinentType(2, 3, true);
    public static final ContinentType H = new ContinentType(2, 4, true);
    public static final ContinentType M = new ContinentType(2, 6, true);
    public static final ContinentType P = new ContinentType(4, 8, false);
    public static final ContinentType G = new ContinentType(6, 10, false);
    public static final ContinentType R = new ContinentType(8, 12, false);

    private final int bonus;
    private final int defaultDefence;
    private final boolean distributable;

    /**
     * @param defence       The number of units that initially defend the territories on this continent(final)
     * @param bonus         The bonus a player gets for owning this continent(final)
     * @param distributable True if territories on this continent can be distributed to players at the beginning of the game
     */
    public ContinentType(int defence, int bonus, boolean distributable) {
        this.defaultDefence = defence;
        this.bonus = bonus;
        this.distributable = distributable;
    }

    public int getBonus() {
        return bonus;
    }

    public int getDefaultDefence() {
        return defaultDefence;
    }

    public boolean isDistributable() {
        return distributable;
    }
}
