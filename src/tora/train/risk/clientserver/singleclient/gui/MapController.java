package tora.train.risk.clientserver.singleclient.gui;

import tora.train.risk.Arena;
import tora.train.risk.Player;
import tora.train.risk.Territory;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

/**
 * Created by intern on 7/22/15.
 */
public class MapController {
    private MapView mapView;
    private Arena arena;

    private boolean winnerExists = false;
    private HashMap<Point, Integer> reinforcements;

    public MapController(String name) {
        this.mapView = new MapView(name);

        mapView.setVisible(true);
       /* mapView.setBtnAddAttackListener(new btnAttackAction());
        mapView.setBtnSubmitAllAttacksListener(new btnSubmitAttacksAction());*/
    }

    public void updateArenaView(Arena arena) {
        this.arena=arena;
        mapView.printArena(arena);
    }

    public void changeTurn(Player currentPlayer, List<String> orderedListOfPlayers, List<Territory> territoryList) {
        mapView.showCurrentPlayersSituation(currentPlayer, territoryList);
    }

    public void addReinforceButtonListener(ActionListener a) {
        mapView.setBtnAddReinforcementListener(a);
    }

    public void addAttackButtonListener(ActionListener a) {
        mapView.setBtnAddAttackListener(a);
    }

    public Point getReinforceCoordinates() throws NumberFormatException {
        int x = Integer.parseInt(mapView.getXposDest());
        int y = Integer.parseInt(mapView.getYposDest());
        return new Point(x, y);
    }

    public int getReinforcementValue() throws NumberFormatException {
        return Integer.parseInt(mapView.getValue());
    }

    public void displayInformativeMessage(String s) {
        mapView.showOptionPanel(s);
    }

    public void updateArena(String playerName, int reinforcementsLeft, int x, int y, int numOfUnits) {
        mapView.showLeftReinforcements(reinforcementsLeft);
        mapView.updateTerritories(playerName, x, y, numOfUnits);

        //update arena
        arena.getTerritoryAtCoordinate(x, y).setUnitNr(numOfUnits);
    }

    public void showMoveAndAttackPanel() {
        mapView.setAttackPhaseViewVisible();
    }

    public void updateReinforcementLabel(int reinforcements) {
        mapView.showLeftReinforcements(reinforcements);
    }
}

