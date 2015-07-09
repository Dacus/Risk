package tora.train.risk;

import java.awt.*;
import java.util.List;

public class Arena {
	private Territory[][] map ;
    private List<Continent> continents;

    public Arena() {
        defaultInitializer();
    }

    public Arena(Territory[][] map, List<Continent> continents) {
        this.map = map;
        this.continents = continents;
    }

    private static void defaultInitializer() {
        //TODO Create the default map from the "Risk for dummies" document
    }

    public Territory getAtCoordinate(int x, int y) {
        return map[x][y];
    }

    public Territory getTerritoryFrom(Point coordinate) {
        return map[coordinate.x][coordinate.y];
    }

    public Continent getContinentAtIndex(int index) {
        return continents.get(index);
    }
}
