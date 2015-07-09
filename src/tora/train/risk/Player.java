package tora.train.risk;

public class Player {
	public String name;
	//public Color color;
	private int score;

    /**
       Number of units that the player is allowed to place on its own territories
       at the beginning of each round
     */
	private int reinforcements;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public int getReinforcements() {
        return reinforcements;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setReinforcements(int reinforcements) {
        this.reinforcements = reinforcements;
    }
}
