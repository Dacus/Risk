package tora.train.risk.clientserver.singleclient.gui;

/**
 * Created by intern on 7/22/15.
 */
public class MapController {
    private MapView mapView;

    public MapController(String name){
        this.mapView=new MapView(name);
        mapView.setVisible(true);
    }
}
