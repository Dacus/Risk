package tora.train.risk;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    private static final int SIZE_X=11;
    private static final int SIZE_Y=11;
    private Territory[][] map;
    private List<Continent> continents;

    public Arena() {
        map = new Territory[SIZE_X][SIZE_Y];
        continents = new ArrayList<>();

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

    /**
     * Find all territories owned by a player
     *
     * @param p Player
     * @return a list of Territories owned by p
     */
    public List<Territory> getOwnedTerritories(Player p) {
        List<Territory> list = new ArrayList<>();
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                Territory t = map[i][j];
                if (t.getOwner().equals(p)) {
                    list.add(t);
                }
            }
        }
        return list;
    }

    @Override
    public String toString(){
        return printArena();
    }

    private String printArena() {
        String s = "";
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (map[i][j].toString().length() == 1)
                    s += " ";
                s += " " + map[i][j].toString() + " ";
            }
            s += "\n";
        }
        return s;
    }

    /**
     *     Identical to arena.printArena(), but is much more easy to read
     *     note: Increased readability works only for player names up to 10 characters
     */

    public String fancyPrintArena(){
        String s = "";
        String playerName;
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (map[i][j].toString().length() == 1)
                    s += " ";
                playerName=map[i][j].toString();
                while(playerName.length()<14)
                    playerName+=" ";


                s += " " + playerName + "| ";
            }
            s += "\n";
        }
        return s;
    }

    /**
     * Initializing the map as it is in the documentation
     */
    private void defaultInitializer() {
        int i;
        Continent ANW = new Continent(ContinentType.A);
        Continent ASW = new Continent(ContinentType.A);
        Continent ANE = new Continent(ContinentType.A);
        Continent ASE = new Continent(ContinentType.A);
        Continent MN = new Continent(ContinentType.M);
        Continent MS = new Continent(ContinentType.M);
        Continent PN = new Continent(ContinentType.P);
        Continent PS = new Continent(ContinentType.P);
        Continent GN = new Continent(ContinentType.G);
        Continent GS = new Continent(ContinentType.G);
        Continent R = new Continent(ContinentType.R);
        Continent HW = new Continent(ContinentType.H);
        Continent HE = new Continent(ContinentType.H);
        continents.add(ANE);
        continents.add(ANW);
        continents.add(ASE);
        continents.add(ASW);
        continents.add(MN);
        continents.add(MS);
        continents.add(PN);
        continents.add(PS);
        continents.add(GN);
        continents.add(GS);
        continents.add(HE);
        continents.add(HW);
        continents.add(R);

        //ANW
        for ( i=0;i<5;i++) {
            map[i][0]=new Territory(ANW,new Point(i,0));
        }
        for ( i=0;i<=2;i++) {
            map[i][1]=new Territory(ANW,new Point(i,1));
        }
        map[0][2]=new Territory(ANW,new Point(0,2));

        //ASW
        for ( i=6;i< SIZE_X;i++) {
            map[i][0] =new Territory(ASW,new Point(i,0));
        }
        for ( i=8;i< SIZE_X;i++) {
            map[i][1]=new Territory(ASW,new Point(i,1));
        }
        map[10][2]=new Territory(ASW,new Point(10,2));

        //ANE
        for ( i=0;i<=4;i++) {
            map[i][10]=new Territory(ANE,new Point(i,10));
        }
        for ( i=0;i<=2;i++) {
            map[i][9]=new Territory(ANE,new Point(i,9));
        }
        map[0][8]=new Territory(ANE,new Point(0,8));

        //ASE
        for ( i=6;i<SIZE_X;i++) {
            map[i][10]=new Territory(ASE,new Point(i,10));
        }
        for ( i=8;i<SIZE_X;i++) {
            map[i][9]=new Territory(ASE,new Point(i,9));
        }
        map[10][8]=new Territory(ASE,new Point(10,8));

        //MN
        for ( i=3;i<=7;i++){
            map[0][i]=new Territory(MN,new Point(0,i));
        }
        for ( i=2;i<=8;i++){
            map[1][i]=new Territory(MN,new Point(1,i));
        }

        //MS
        for ( i=3;i<=7;i++){
            map[10][i]=new Territory(MS,new Point(10,i));
        }
        for ( i=2;i<=8;i++){
            map[9][i]=new Territory(MS,new Point(9,i));
        }

        //PN
        for ( i=2;i<=8;i++){
            map[2][i]=new Territory(PN,new Point(2,i));
        }
        for ( i=2;i<=8;i++){
            if(i==2 || i==3 || i==7 || i==8) {
                map[3][i]=new Territory(PN,new Point(3,i));
            }
        }
        //PS
        for ( i=2;i<=8;i++){
            map[8][i]=new Territory(PS,new Point(8,i));
        }
        for ( i=2;i<=8;i++){
            if(i==2 || i==3 || i==7 || i==8) {
                map[7][i]=new Territory(PS,new Point(7,i));
            }
        }

        //GN
        for( i=4;i<=6;i++){
            map[3][i]=new Territory(GN,new Point(3,i));
        }
        for( i=3;i<=7;i++){
            if (i!=5) {
                map[4][i]=new Territory(GN,new Point(4,i));
            }
        }

        //GS
        for( i=4;i<=6;i++){
            map[7][i]=new Territory(GS,new Point(7,i));
        }
        for( i=3;i<=7;i++){
            if (i!=5) {
                map[6][i]=new Territory(GS,new Point(6,i));
            }
        }

        //R
        for( i=3;i<=7;i++){
            map[5][i]=new Territory(R,new Point(5,i));
        }
        map[6][5] =new Territory(R,new Point(6,5));
        map[4][5]=new Territory(R,new Point(4,5));

        //HW
        for( i=3;i<=7;i++){
            map[i][1]=new Territory(HW,new Point(i,1));
        }
        for( i=4;i<=6;i++){
            map[i][2]=new Territory(HW,new Point(i,2));
        }
        map[5][0]=new Territory(HW,new Point(5,0));

        //HE
        for( i=3;i<=7;i++){
            map[i][9]=new Territory(HE,new Point(i,9));
        }
        for( i=4;i<=6;i++){
            map[i][8]=new Territory(HE,new Point(i,8));
        }
        map[5][10]=new Territory(HE,new Point(5,10));
    }
}
