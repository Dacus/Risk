package tora.train.risk.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static tora.train.risk.GUI.StaticInformations.*;

/**
 * Created by intern on 7/20/15.
 */
public class MainController {
    MainView mainView;
    //TODO when there is a winner???
    private boolean win = false;
    private int playersPlayed=0;
    private HashMap<Point, Integer> reinforcements;

    public MainController(){
        mainView=new MainView();
        distributePlayers();
        mainView.printArena(StaticInformations.getArena());
        
        mainView.setBtnAddReinforcementListener(new btnReinforcementAction());
        mainView.setBtnSubmitReinforcementsListener(new btnSubmitReinforcementsAction());
        
        mainView.setBtnAddAttackListener(new btnAttackAction());
        mainView.setBtnSubmitAllAttacksListener(new btnSubmitAttacksAction());
        startGame();
    }

    private void startGame(){
        reinforcementPhase();
    }

    private void reinforcementPhase(){
        mainView.reinforcePhaseView();
        reinforcements = new HashMap<>();
        mainView.showCurrentPlayer();
        mainView.showLeftReinforcements();
        mainView.printPlayersTerritories(getCurrentPlayer());
    }

    private void attackPhase(){
        //TODO adauga logica
        mainView.attackPhaseView();
    }

    public void setVisible(boolean visible) {
        mainView.setVisible(visible);
    }

    private class btnReinforcementAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Point destination = new Point(mainView.getXposDest(), mainView.getYposDest());
            int value=mainView.getValue();
            if (reinforcements.containsKey(destination)) {
                value += reinforcements.get(destination);
                reinforcements.put(destination, value);
            } else {
                reinforcements.put(destination,value);
            }
            try {
                submitReinforcements(destination, value);
            }catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Invalid position!");
            }
            //mainView.printArena(StaticInformations.getArena());
            finally {
                System.out.println("destination"+destination.toString()+ " value "+value);
            }
            //mainView.printMap();
            mainView.showLeftReinforcements();
            System.out.println(getCurrentPlayer());
        }
    }

    private class btnSubmitReinforcementsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playersPlayed++;
            /*
            for (Map.Entry<Point, Integer> pair : reinforcements.entrySet()) {
                StaticInformations.submitReinforcements(pair.getKey(), pair.getValue());
            }
            */
            System.out.println(reinforcements.toString());
            endCurrentPlayerTurn();
            if (playersPlayed< getPlayersNumber()-1){
                reinforcementPhase();
            }
            else{
                System.out.println("This is sparta!!!");
                playersPlayed=0;
                StaticInformations.resetPlayer();
                attackPhase();
            }
        }
    }
    
    private class btnAttackAction implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
        
    }
    
    
    private class btnSubmitAttacksAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
/*
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StaticInformations.setArenaController(new ArenaController());
                    StaticInformations.addPlayer(new Player("ion"));
                    StaticInformations.addPlayer(new Player("ana"));
                    MainController window = new MainController();
                    window.mainView.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    */

}


