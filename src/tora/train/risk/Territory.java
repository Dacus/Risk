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

    /***
     * default constructor, creates a territory on the given Continent
     * @param c Continent
     */
    public Territory(Continent c){
        owner=Player.CPU_MAP_PLAYER;
        continent=c;
        unitNr=c.getType().getDefaultDefence();
    }

    public int getUnitNr() {
        return unitNr;
    }

    public Player getOwner() {
        return owner;
    }

    public Continent getContinent() {
        return continent;
    }

    @Override
    public String toString(){
        return continent.getType().toString();
    }
}
