package tora.train.risk;

public enum ContinentType {
    A_NE(2, 3),
    A_NW(2, 3),
    A_SE(2, 3),
    A_SW(2, 3),
    H_E(2, 4),
    H_W(2, 4),
    M_N(2, 6),
    M_S(2, 6),
    P_S(4, 8),
    P_N(4, 8),
    G_N(6, 10),
    G_S(6, 10),
    R(8, 12);

    private int bonus;
    private int defaultDefence;

    /**
     * @param defence The amount of units that initially defend the territories on this continent
     * @param bonus   The bonus a player gets for owning this continent
     */
    private ContinentType(int defence, int bonus) {
        this.defaultDefence = defence;
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public int getDefaultDefence() {
        return defaultDefence;
    }
}
