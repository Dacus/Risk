package tora.train.risk;

public class ContinentType {
    //Default map continent types From "Risk for dummies" map
    public static final ContinentType A = new ContinentType(2, 3);
    public static final ContinentType H = new ContinentType(2, 4);
    public static final ContinentType M = new ContinentType(2, 6);
    public static final ContinentType P = new ContinentType(4, 8);
    public static final ContinentType G = new ContinentType(6, 10);
    public static final ContinentType R = new ContinentType(8, 12);

    private final int bonus;
    private final int defaultDefence;

    /**
     * @param defence The number of units that initially defend the territories on this continent(final)
     * @param bonus   The bonus a player gets for owning this continent(final)
     */
    public ContinentType(int defence, int bonus) {
        this.defaultDefence = defence;
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public int getDefaultDefence() {
        return defaultDefence;
    }

    @Override
    public String toString(){
        return ""+bonus;
    }
}
