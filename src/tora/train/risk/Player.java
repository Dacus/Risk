package tora.train.risk;

public class Player {
    public static final Player CPU_MAP_PLAYER = new Player("CPU_MAP");
    private static final int INITIAL_REIFORCEMENTS = 5;
    private String name;
    //public Color color;
	private int score;
    /**
     Number of units that the player is allowed to place on its own territories
     at the beginning of each round
     */
    private int reinforcements;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.reinforcements = INITIAL_REIFORCEMENTS;
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
}
