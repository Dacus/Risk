package tora.train.risk;

import java.awt.*;
import java.util.List;

public class Arena {
	private Teritory[][] map ;
    private List<Continent> continents;

    public Arena() {
        defaultInitializer();
    }

    public Arena(Teritory[][] map, List<Continent> continents) {
        this.map = map;
        this.continents = continents;
    }

    private void defaultInitializer() {
        //TODO Create the default map from the "Risk for dummies" document
        for (int i=0;i<=4;i++){
          //  map[i,0]
        }

    }

    public Teritory getAtCoordinate(int x, int y) {
        return map[x][y];
    }

    public Teritory getAtCoordinate(Point coordinate) {
        return map[coordinate.x][coordinate.y];
    }

    public Continent getContinetAtIndex(int index) {
        return continents.get(index);
    }


}
