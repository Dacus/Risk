package tora.train.risk;

public class Player {
    public static final Player CPU_MAP_PLAYER;

    static {
        CPU_MAP_PLAYER = new Player("CPU_MAP");
        CPU_MAP_PLAYER.setReinforcements(0);
    }
    private static final int INITIAL_REINFORCEMENTS = 20;
    private String name;
    //public Color color;
    private int score;

    /**
     * Number of units that the player is allowed to place on its own territories
     * at the beginning of each round
     */
    private int reinforcements;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.reinforcements = INITIAL_REINFORCEMENTS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getReinforcements() {
        return reinforcements;
    }

    public void setReinforcements(int reinforcements) {
        this.reinforcements = reinforcements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this==null ||o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (!name.equals(player.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        String s = "";
        s += "Player name " + name + " " + reinforcements+" reinforcements";
        return s;
    }
}
