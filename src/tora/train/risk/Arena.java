package tora.train.risk;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    private Territory[][] map;
    private List<Continent> continents;
    private Continent ANW;
    private Continent ASW;
    private Continent ANE;
    private Continent ASE;
    private Continent MN;
    private Continent MS;
    private Continent PN;
    private Continent PS;
    private Continent GN;
    private Continent GS;
    private Continent R;
    private Continent HW;
    private Continent HE;

    public Arena() {
         map=new Teritory[10][10];
         continents=new ArrayList<Continent>();
         ANW=new Continent(ContinentType.A);
         ASW=new Continent(ContinentType.A);
         ANE=new Continent(ContinentType.A);
         ASE=new Continent(ContinentType.A);
         MN=new Continent(ContinentType.M);
         MS=new Continent(ContinentType.M);
         PN=new Continent(ContinentType.P);
         PS=new Continent(ContinentType.P);
         GN=new Continent(ContinentType.G);
         GS=new Continent(ContinentType.G);
         R=new Continent(ContinentType.R);
         HW=new Continent(ContinentType.H);
         HE=new Continent(ContinentType.H);
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

    private void defaultInitializer() {
        //ANW
        for (int i=0;i<5;i++) {
            map[i][0].owner = Player.CPU_MAP_PLAYER;
            map[i][0].continent = ANW;
            map[i][0].unitNr = ContinentType.A.getDefaultDefence();
        }
        for (int i=0;i<=2;i++) {
            map[i][1].owner = Player.CPU_MAP_PLAYER;
            map[i][1].continent = ANW;
            map[i][1].unitNr = ContinentType.A.getDefaultDefence();
        }
        map[0][2].owner = Player.CPU_MAP_PLAYER;
        map[0][2].continent = ANW;
        map[0][2].unitNr = ContinentType.A.getDefaultDefence();

        //ASW
        for (int i=6;i<=10;i++) {
            map[i][0].owner = Player.CPU_MAP_PLAYER;
            map[i][0].continent = ASW;
            map[i][0].unitNr = ContinentType.A.getDefaultDefence();
        }
        for (int i=8;i<=10;i++) {
            map[i][1].owner = Player.CPU_MAP_PLAYER;
            map[i][1].continent = ASW;
            map[i][1].unitNr = ContinentType.A.getDefaultDefence();
        }
        map[10][2].owner = Player.CPU_MAP_PLAYER;
        map[10][2].continent = ASW;
        map[10][2].unitNr = ContinentType.A.getDefaultDefence();

        //ANE
        for (int i=0;i<=4;i++) {
            map[i][10].owner = Player.CPU_MAP_PLAYER;
            map[i][10].continent = ANE;
            map[i][10].unitNr = ContinentType.A.getDefaultDefence();
        }
        for (int i=0;i<=2;i++) {
            map[i][9].owner = Player.CPU_MAP_PLAYER;
            map[i][9].continent = ANE;
            map[i][9].unitNr = ContinentType.A.getDefaultDefence();
        }
        map[0][8].owner = Player.CPU_MAP_PLAYER;
        map[0][8].continent = ANE;
        map[0][8].unitNr = ContinentType.A.getDefaultDefence();

        //ASE
        for (int i=6;i<=10;i++) {
            map[i][10].owner = Player.CPU_MAP_PLAYER;
            map[i][10].continent = ASE;
            map[i][10].unitNr = ContinentType.A.getDefaultDefence();
        }
        for (int i=8;i<=10;i++) {
            map[i][9].owner = Player.CPU_MAP_PLAYER;
            map[i][9].continent =  ASE;
            map[i][9].unitNr = ContinentType.A.getDefaultDefence();
        }
        map[10][8].owner = Player.CPU_MAP_PLAYER;
        map[10][8].continent = ASE;
        map[10][8].unitNr = ContinentType.A.getDefaultDefence();

        //MN
        for (int i=3;i<=7;i++){
            map[0][i].unitNr=ContinentType.M.getDefaultDefence();
            map[0][i].owner=Player.CPU_MAP_PLAYER;
            map[0][i].continent=MN;
        }
        for (int i=2;i<=8;i++){
            map[1][i].unitNr=ContinentType.M.getDefaultDefence();
            map[1][i].owner=Player.CPU_MAP_PLAYER;
            map[1][i].continent=MN;
        }

        //MS
        for (int i=3;i<=7;i++){
            map[10][i].unitNr=ContinentType.M.getDefaultDefence();
            map[10][i].owner=Player.CPU_MAP_PLAYER;
            map[0][i].continent=MS;
        }
        for (int i=2;i<=8;i++){
            map[9][i].unitNr=ContinentType.M.getDefaultDefence();
            map[9][i].owner=Player.CPU_MAP_PLAYER;
            map[9][i].continent=MS;
        }

        //PN
        for (int i=2;i<=8;i++){
            map[2][i].unitNr=ContinentType.P.getDefaultDefence();
            map[2][i].owner=Player.CPU_MAP_PLAYER;
            map[2][i].continent=PN;
        }
        for (int i=2;i<=8;i++){
            if(i==2 || i==3 || i==7 || i==8) {
                map[3][i].unitNr = ContinentType.P.getDefaultDefence();
                map[3][i].owner = Player.CPU_MAP_PLAYER;
                map[3][i].continent = PN;
            }
        }
        //PS
        for (int i=2;i<=8;i++){
            map[8][i].unitNr=ContinentType.P.getDefaultDefence();
            map[8][i].owner=Player.CPU_MAP_PLAYER;
            map[8][i].continent=PS;
        }
        for (int i=2;i<=8;i++){
            if(i==2 || i==3 || i==7 || i==8) {
                map[7][i].unitNr = ContinentType.P.getDefaultDefence();
                map[7][i].owner = Player.CPU_MAP_PLAYER;
                map[7][i].continent = PS;
            }
        }

        //GN
        for(int i=4;i<=6;i++){
            map[3][i].unitNr = ContinentType.G.getDefaultDefence();
            map[3][i].owner = Player.CPU_MAP_PLAYER;
            map[3][i].continent = GN;
        }
        for(int i=3;i<=7;i++){
            if (i!=5) {
                map[4][i].unitNr = ContinentType.G.getDefaultDefence();
                map[4][i].owner = Player.CPU_MAP_PLAYER;
                map[4][i].continent = GN;
            }
        }

        //GS
        for(int i=4;i<=6;i++){
            map[7][i].unitNr = ContinentType.G.getDefaultDefence();
            map[7][i].owner = Player.CPU_MAP_PLAYER;
            map[7][i].continent = GS;
        }
        for(int i=3;i<=7;i++){
            if (i!=5) {
                map[6][i].unitNr = ContinentType.G.getDefaultDefence();
                map[6][i].owner = Player.CPU_MAP_PLAYER;
                map[6][i].continent = GS;
            }
        }

        //R
        for(int i=3;i<=7;i++){
            map[5][i].unitNr = ContinentType.R.getDefaultDefence();
            map[5][i].owner = Player.CPU_MAP_PLAYER;
            map[5][i].continent = R;
        }
        map[6][5].unitNr = ContinentType.R.getDefaultDefence();
        map[6][5].owner = Player.CPU_MAP_PLAYER;
        map[6][5].continent = R;
        map[4][5].unitNr = ContinentType.R.getDefaultDefence();
        map[4][5].owner = Player.CPU_MAP_PLAYER;
        map[4][5].continent = R;

        //HW
        for(int i=3;i<=7;i++){
            map[i][1].unitNr = ContinentType.H.getDefaultDefence();
            map[i][1].owner = Player.CPU_MAP_PLAYER;
            map[i][1].continent = HW;
        }
        for(int i=4;i<=6;i++){
            map[i][2].unitNr = ContinentType.H.getDefaultDefence();
            map[i][2].owner = Player.CPU_MAP_PLAYER;
            map[i][2].continent = HW;
        }
        map[5][0].unitNr = ContinentType.H.getDefaultDefence();
        map[5][0].owner = Player.CPU_MAP_PLAYER;
        map[5][0].continent = HW;

        //HE
        for(int i=3;i<=7;i++){
            map[i][9].unitNr = ContinentType.H.getDefaultDefence();
            map[i][9].owner = Player.CPU_MAP_PLAYER;
            map[i][9].continent = HE;
        }
        for(int i=4;i<=6;i++){
            map[i][8].unitNr = ContinentType.H.getDefaultDefence();
            map[i][8].owner = Player.CPU_MAP_PLAYER;
            map[i][8].continent = HE;
        }
        map[5][10].unitNr = ContinentType.H.getDefaultDefence();
        map[5][10].owner = Player.CPU_MAP_PLAYER;
        map[5][10].continent = HE;
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

    public static void main(String[] args) {
        Arena a=new Arena();
    }
}
