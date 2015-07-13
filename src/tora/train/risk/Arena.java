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
         map=new Territory[11][11];
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

    private void printArena(){
        for (int i=0;i<=10;i++){
            for (int j=0;j<=10;j++){
                if (map[i][j].toString().length()==1)
                    System.out.print(" ");
                System.out.printf(" "+map[i][j].toString()+" ");
            }
            System.out.println();
        }
    }

    private void defaultInitializer() {
        int i=0;

        //ANW
        for ( i=0;i<5;i++) {
            map[i][0]=new Territory(ANW);
        }
        for ( i=0;i<=2;i++) {
            map[i][1]=new Territory(ANW);
        }
        map[0][2]=new Territory(ANW);

        //ASW
        for ( i=6;i<=10;i++) {
            map[i][0] =new Territory(ASW);
        }
        for ( i=8;i<=10;i++) {
            map[i][1]=new Territory(ASW);
        }
        map[10][2]=new Territory(ASW);

        //ANE
        for ( i=0;i<=4;i++) {
            map[i][10]=new Territory(ANE);
        }
        for ( i=0;i<=2;i++) {
            map[i][9]=new Territory(ANE);
        }
        map[0][8]=new Territory(ANE);

        //ASE
        for ( i=6;i<=10;i++) {
            map[i][10]=new Territory(ASE);
        }
        for ( i=8;i<=10;i++) {
            map[i][9]=new Territory(ASE);
        }
        map[10][8]=new Territory(ASE);

        //MN
        for ( i=3;i<=7;i++){
            map[0][i]=new Territory(MN);;
        }
        for ( i=2;i<=8;i++){
            map[1][i]=new Territory(MN);
        }

        //MS
        for ( i=3;i<=7;i++){
            map[10][i]=new Territory(MS);;
        }
        for ( i=2;i<=8;i++){
            map[9][i]=new Territory(MS);;
        }

        //PN
        for ( i=2;i<=8;i++){
            map[2][i]=new Territory(PN);;
        }
        for ( i=2;i<=8;i++){
            if(i==2 || i==3 || i==7 || i==8) {
                map[3][i]=new Territory(PN);
            }
        }
        //PS
        for ( i=2;i<=8;i++){
            map[8][i]=new Territory(PS);
        }
        for ( i=2;i<=8;i++){
            if(i==2 || i==3 || i==7 || i==8) {
                map[7][i]=new Territory(PS);
            }
        }

        //GN
        for( i=4;i<=6;i++){
            map[3][i]=new Territory(GN);;
        }
        for( i=3;i<=7;i++){
            if (i!=5) {
                map[4][i]=new Territory(GN);
            }
        }

        //GS
        for( i=4;i<=6;i++){
            map[7][i]=new Territory(GS);
        }
        for( i=3;i<=7;i++){
            if (i!=5) {
                map[6][i]=new Territory(GS);
            }
        }

        //R
        for( i=3;i<=7;i++){
            map[5][i]=new Territory(R);
        }
        map[6][5] =new Territory(R);;
        map[4][5]=new Territory(R);

        //HW
        for( i=3;i<=7;i++){
            map[i][1]=new Territory(HW);
        }
        for( i=4;i<=6;i++){
            map[i][2]=new Territory(HW);
        }
        map[5][0]=new Territory(HW);

        //HE
        for( i=3;i<=7;i++){
            map[i][9]=new Territory(HE);
        }
        for( i=4;i<=6;i++){
            map[i][8]=new Territory(HE);
        }
        map[5][10]=new Territory(HE);
    }

    /**
     * testing -> map correctness
     * @param args
     */
    public static void main(String[] args) {
        Arena a=new Arena();
        a.printArena();
    }
}
