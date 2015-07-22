package tora.train.risk;

import java.awt.*;

/**
 * Created by Paul on 7/20/2015.
 */
public class MoveCommand {
    private Point coordinates;
    private int numberOfUnits;
    private Player owner;


    public MoveCommand(Point coordinates, int numberOfUnits, Player owner, HotSeatConsole.Direction direction) {
        this.coordinates = coordinates;
        this.numberOfUnits = numberOfUnits;
        this.owner = owner;
        //this.direction = direction;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public Player getOwner() {
        return owner;
    }

    public Point getCoordinates() {
        return coordinates;
    }
}
