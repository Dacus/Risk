package tora.train.risk;

import java.awt.*;

public class Territory {

    /**
     * army units in the territory
     */
    private int unitNr;
    /**
     * the number of units the "lord" can move
     * inv : movableUnits <= unitNr
     */
    private int movableUnits;
    /**
     * the "lord" of the territory
     */
    private Player owner;
    /**
     * the continent to which territory belongs
     */
    private Continent continent;

    private Point coordinates;

    /***
     * default constructor, creates a territory on the given Continent
     * @param c Continent
     */
    public Territory(Continent c){
        owner=Player.CPU_MAP_PLAYER;
        continent=c;
        unitNr=c.getType().getDefaultDefence();
    }

    public Territory(Continent c, Point coordinates) {
        this(c);
        this.coordinates = coordinates;
        this.unitNr = 0;
        this.movableUnits = 0;
    }

    public int getUnitNr() {
        return unitNr;
    }

    public void setUnitNr(int unitNr) {
        if (unitNr < movableUnits)
            throw new IllegalArgumentException();
        this.unitNr = unitNr;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player p) {
        this.owner = p;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public int getMovableUnits() {
        return movableUnits;
    }

    public void setMovableUnits(int movableUnits) {
        if (movableUnits > unitNr)
            throw new IllegalArgumentException();
        this.movableUnits = movableUnits;
    }

    @Override
    public String toString(){
        return owner.getName()+"("+unitNr+")";
    }
}
