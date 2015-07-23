package tora.train.risk.clientserver.singleclient.gui;

import tora.train.risk.Arena;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by intern on 7/22/15.
 */
public class MapController {
    private MapView mapView;

    private boolean winnerExists = false;
    private HashMap<Point, Integer> reinforcements;

    public MapController(String name){
        this.mapView=new MapView(name);
        mapView.setVisible(true);

        /*mapView.setBtnAddReinforcementListener(new btnReinforcementAction());
        mapView.setBtnSubmitReinforcementsListener(new btnSubmitReinforcementsAction());

        mapView.setBtnAddAttackListener(new btnAttackAction());
        mapView.setBtnSubmitAllAttacksListener(new btnSubmitAttacksAction());*/
    }

    public void updateArena(Arena arena){
        mapView.printArena(arena);
    }


}
