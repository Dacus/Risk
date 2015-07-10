package tora.train.risk;

import java.awt.*;
import java.util.List;

public class Arena {
    private Territory[][] map;
    private List<Continent> continents;

    public Arena() {
        defaultInitializer();
    }

    /**
     * @param map        the map of the territories, map.length > 0,map[0].length >0;
     * @param continents the partition of the map into continents
     */
    public Arena(Territory[][] map, List<Continent> continents) {
        this.map = map;
        this.continents = continents;
    }

    private static void defaultInitializer() {
        //TODO Create the default map from the "Risk for dummies" document
    }

    public Territory getTerritoryAtCoordinate(int x, int y) {
        return map[x][y];
    }

    public Territory getTerritoryAtCoordinate(Point coordinate) {
        return map[coordinate.x][coordinate.y];
    }

    public int getXSize() {
        return map.length;
    }

    public int getYSize() {
        return map[0].length;
    }

    public List<Continent> getContinents() {
        return continents;
    }
}
