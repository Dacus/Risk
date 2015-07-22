package tora.train.risk.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

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
        StaticInformations.distributePlayers();
        mainView.printMap();
        mainView.setBtnAddReinforcementListener(new btnReinforcementAction());
        mainView.setBtnSubmitReinforcementsListener(new btnSubmitReinforcementsAction());
        startGame();
    }

    private void startGame(){
        reinforcementPhase();
    }

    private void reinforcementPhase(){
        //TODO
        mainView.reinforcePhaseView();
        reinforcements = new HashMap<>();
        mainView.showCurrentPlayer();
        mainView.showLeftReinforcements();
        mainView.printPlayersTerritories(StaticInformations.getCurrentPlayer());
    }

    private void attackPhase(){
        //TODO
    }

    public void setVisible(boolean visible) {
        mainView.setVisible(visible);
    }

    private class btnReinforcementAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Point destination = new Point(mainView.getXpos(), mainView.getYpos());
            if (reinforcements.containsKey(destination)) {
                Integer value = reinforcements.get(destination);
                value += mainView.getValue();
                reinforcements.put(destination, value);
            } else {
                reinforcements.put(destination, mainView.getValue());
            }
            mainView.showLeftReinforcements();
        }
    }

    private class btnSubmitReinforcementsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playersPlayed++;
            for (Map.Entry<Point, Integer> pair : reinforcements.entrySet()) {
                StaticInformations.submitReinforcements(pair.getKey(), pair.getValue());
            }
            System.out.println(reinforcements.toString());
            StaticInformations.endCurrentPlayerTurn();
            if (playersPlayed>StaticInformations.getPlayersNumber()){
                attackPhase();
            }
            else{
                reinforcementPhase();
            }
        }
    }



}
