package tora.train.risk;

import java.awt.*;

/**
 * Created by Paul on 7/20/2015.
 */
public class MoveCommand {
    private Point coordinates;
    private int numberOfUnits;
    private Player owner;
    String direction;

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public Player getOwner() {
        return owner;
    }


    public MoveCommand(Point coordinates,int numberOfUnits, Player owner,String direction){
        this.coordinates=coordinates;
        this.numberOfUnits=numberOfUnits;
        this.owner=owner;
        this.direction=direction;
    }

    public Point getCoordinates() {
        return coordinates;
    }
    public Point getDestinationPoint(){
        switch (direction){
            case "up":
                return new Point(coordinates.x+1,coordinates.y);
            case "down":
                return new Point(coordinates.x-1,coordinates.y);
            case "left":
                return new Point(coordinates.x,coordinates.y-1);
            case "right":
                return new Point(coordinates.x,coordinates.y+1);
            default:
                return null;
        }
    }
}
