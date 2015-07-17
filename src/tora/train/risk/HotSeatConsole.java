package tora.train.risk;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Paul on 7/16/2015.
 */
public class HotSeatConsole {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static int readNumberOfPlayers(){
        int n;
        while(true) {
            try {

                n = Integer.parseInt(reader.readLine());
                if (n<1)
                    throw new NumberFormatException();
                break;
            }
            catch(NumberFormatException e){
                System.out.println("Please input a valid positive, non-zero, integer");
            }
            catch (IOException e)
            {
                System.out.println("Error in reading input, please try again");
            }
        }
        return n;
    }
    private static Player readPlayer() {
        System.out.println("Intput Player name");
        Player p;
        while (true) {
            try {

                p = new Player(reader.readLine());
                break;
            } catch (IOException e) {
                System.out.println("Error reading player name, try again");
            }
        }
        return p;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the game!");
        System.out.println("Please input desired number of players: ");
        int NumberOfPlayers=readNumberOfPlayers();
        Arena arena=new Arena();
        ArenaController arenaController=new ArenaController();
        for (int i=0;i<NumberOfPlayers;i++)
        {
            arenaController.addPlayer(readPlayer());
        }
        arenaController.distributePlayers(5,1);
        System.out.println(arenaController.getArena().fancyPrintArena());






    }



}
