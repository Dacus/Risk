package tora.train.risk;

public class Territory {

    /**
     * army units in the territory
     */
	protected int unitNr;
	/**
     * the "lord" of the territory
     */
	protected Player owner;
	/**
     * the continent to which territory belongs
     */
	protected Continent continent;

    public int getUnitNr() {
        return unitNr;
    }

    public Player getOwner() {
        return owner;
    }

    public Continent getContinent() {
        return continent;
    }
}
