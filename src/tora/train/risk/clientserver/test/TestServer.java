package tora.train.risk.clientserver.test;

import tora.train.risk.clientserver.serverapp.MainServerController;
import tora.train.risk.clientserver.serverapp.MainServerFrame;

/**
 * Created by Andrea on 7/16/2015.
 */
public class TestServer {
    public static void main(String[] args) {
        MainServerFrame serverFrame=new MainServerFrame();
        MainServerController controller=new MainServerController(serverFrame);

        controller.startRunning();
    }
}
