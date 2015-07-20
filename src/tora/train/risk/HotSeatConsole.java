package tora.train.risk;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by Paul on 7/16/2015.
 */
public class HotSeatConsole {
    private static Scanner scanner = new Scanner(System.in);
    private ArenaController arenaController;

    public HotSeatConsole(){
        arenaController=new ArenaController();
    }

    private void printWelcomeMessage(){
        System.out.println("Welcome to the game!");
        System.out.println("Please input desired number of players: ");
    }

    private void subscribePlayers(){
        int NumberOfPlayers=scanner.nextInt();
        scanner.nextLine();

        for (int i=0;i<NumberOfPlayers;i++)
        {
            System.out.println("Player " + i +":");
            arenaController.addPlayer(readPlayer());
        }
    }

    private void distributeFirstFiveTerritories(){
        arenaController.distributePlayers(5,1);
        System.out.println(arenaController.getArena().fancyPrintArena());
    }

    private void distributeTerritoriesLeft(){
        System.out.println("All player will now distribute 15 units each");
        for (int i=1;i<arenaController.getNumberOfPlayers();i++){
            System.out.println(arenaController.getPlayerByIndex(i).getName()+" it is your turn to distribute your starting units");
            playerReinforces(arenaController.getPlayerByIndex(i),arenaController);

        }
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
        arenaController.moveUnits(unitsNr,init,dest,player);
    }
    //

    private Player readPlayer() {
        System.out.println("Input Player name:");
        String name= scanner.nextLine();
        while (name.equals("")){
            name=scanner.nextLine();
        }
        return new Player(name);
    }

    private Point readPoint(){
        int x=scanner.nextInt();
        scanner.nextLine();
        int y=scanner.nextInt();
        scanner.nextLine();

        Point point=new Point(x,y);
        return point;
    }

    private void playerReinforces(Player player, ArenaController arenaController)
    {
        int x,y,numberOfUnits;

        System.out.println("Type in the coordinates of a territory and the number of units to reinforce it with");
        while (player.getReinforcements()>0)
        {
            System.out.println("You have "+player.getReinforcements()+" units left");
            Point point=readPoint();

            numberOfUnits=scanner.nextInt();
            scanner.nextLine();

            if (arenaController.reinforce(numberOfUnits,point,player)==false)
                System.out.println("Invalid reinforce command");
        }

    }

    private void playerTurn(Player player,ArenaController arenaController){
        System.out.println("Type end to finish turn, move followed by coordinates, number of units and direction of movement \n to move units");
        String command="";
        String direction="";
        while(!command.equals("end")){
            try {
                command = scanner.nextLine();
            }
            catch (Exception e)
            {
                System.out.println("Problem reading input,try again");
            }
            if (command.equals("move")) {
                Point point = readPoint();
                int numberOfUnits = scanner.nextInt();
                scanner.nextLine();
                try {
                    direction = scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("Problem reading input,try again");
                }
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

    public void startGame() {
        printWelcomeMessage();
        subscribePlayers();
        distributeFirstFiveTerritories();
        distributeTerritoriesLeft();

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
