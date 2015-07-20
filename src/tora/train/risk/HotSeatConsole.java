package tora.train.risk;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by Paul on 7/16/2015.
 */
public class HotSeatConsole {
    private static Scanner reader = new Scanner(System.in);
    private ArenaController arenaController;

    public HotSeatConsole() {
        arenaController=new ArenaController();
    }

    //Move Methods Modified to not use Territory
    private void moveUp(int unitsNr,Point init, Player player,ArenaController arenaController){
        Point dest=new Point(init.x-1,init.y);
        arenaController.moveUnits(unitsNr,init,dest,player);
    }

    private void moveDown(int unitsNr,Point init, Player player,ArenaController arenaController){
        Point dest=new Point(init.x+1,init.y);
        arenaController.moveUnits(unitsNr,init,dest,player);
    }

    private void moveLeft(int unitsNr,Point init, Player player,ArenaController arenaController){
        Point dest=new Point(init.x,init.y-1);
        arenaController.moveUnits(unitsNr,init,dest,player);
    }

    private void moveRight(int unitsNr,Point init, Player player,ArenaController arenaController){
        Point dest=new Point(init.x,init.y+1);
        arenaController.moveUnits(unitsNr, init, dest, player);
    }
    //

    private Player readPlayer() {
        System.out.println("Input Player name");
        String name="";
        while (name.equals("")) {
            name=reader.nextLine();
        }
        return new Player(name);
    }

    private Point readPoint(){
        Point point=null;
        try{
            int x=reader.nextInt();
            reader.nextLine();
            int y=reader.nextInt();
            reader.nextLine();
            point=new Point(x,y);
            return point;
        } catch(Exception e){
            System.out.println("Incorrect coordinates");
        }
        finally {
            return point;
        }
    }

    private void playerReinforces(Player player, ArenaController arenaController)
    {
        int x,y,numberOfUnits;

        System.out.println("Type in the coordinates of a territory and the number of units to reinforce it with");

        int num=player.getReinforcements();
        while (num>0)
        {
            try {
                System.out.println("You have "+num+" units left");
                Point point=readPoint();
                numberOfUnits = reader.nextInt();
                reader.nextLine();

                if (arenaController.reinforce(numberOfUnits, point,player)==false)
                    System.out.println("Invalid reinforce command");

                num=player.getReinforcements();
            } catch (Exception e){
                System.out.println("Incorrect number of units");
                reader.nextLine();
            }
        }

    }
    private void playerTurn(Player player,ArenaController arenaController){
        System.out.println("Type end to finish turn, move followed by coordinates, number of units and direction of movement \n to move units");
        String command="";
        String direction="";
        while(!command.equals("end")){
            command = reader.nextLine();
            if (command.equals("move")) {
                Point point = readPoint();
                int numberOfUnits = reader.nextInt();
                reader.nextLine();
                direction = reader.nextLine();

                switch (direction.toLowerCase()){
                    case "up":
                        moveUp(numberOfUnits,point,player,arenaController);
                        break;
                    case "down":
                        moveDown(numberOfUnits,point,player,arenaController);
                        break;
                    case "left":
                        moveLeft(numberOfUnits,point,player,arenaController);
                        break;
                    case "right":
                        moveRight(numberOfUnits,point,player,arenaController);
                        break;
                    default:
                        System.out.println("Invalid direction");
                        break;
                }

            }
        }
    }

    private void printWelcomeMessage(){
        System.out.println("Welcome to the game!");
        System.out.println("Please input the number of players: ");
    }

    private void addPlayers(){
        int numberOfPlayers=reader.nextInt();

        for (int i = 0;i < numberOfPlayers;i++)
        {
            arenaController.addPlayer(readPlayer());
        }
    }

    private void initialDistribution(){
        arenaController.distributePlayers(5,1);

        System.out.println(arenaController.getArena().fancyPrintArena());

        System.out.println("Players will now distribute 15 units on their territories:");
        for (int i=1;i <= arenaController.getNumberOfPlayers();i++){
            System.out.println(arenaController.getPlayerByIndex(i).getName()+" it is your turn to distribute your starting units");
            playerReinforces(arenaController.getPlayerByIndex(i),arenaController);
        }
    }

    public void startGame() {
        printWelcomeMessage();
        addPlayers();
        initialDistribution();

        boolean win=false;

        while (!win){
            for (int i=1;i<=arenaController.getNumberOfPlayers();i++){
                Player currentPlayer = arenaController.getPlayerByIndex(i);
                System.out.println("It is now "+ currentPlayer.getName()+"'s turn");
                arenaController.givePlayerBonus(currentPlayer);
                System.out.println(currentPlayer.getName()+" you have "+ currentPlayer.getReinforcements() + " units to reinforce");
                playerReinforces(currentPlayer,arenaController);
                playerTurn(currentPlayer,arenaController);

            }
            if (arenaController.getNumberOfPlayers()<3)
            win=true;
        }
        System.out.println(arenaController.getArena().fancyPrintArena());
    }

    public static void main(String[] args) {
        HotSeatConsole hot =new HotSeatConsole();
        hot.startGame();
    }


}
