package tora.train.risk.ClientServerExample.test;

import tora.train.risk.ClientServerExample.serverapp.MainServerController;
import tora.train.risk.ClientServerExample.serverapp.MainServerFrame;

/**
 * Created by Andrea on 7/16/2015.
 */
public class TestServer {
    public static void main(String[] args) {
        MainServerFrame serverFrame=new MainServerFrame();
        MainServerController controller=new MainServerController(serverFrame);

        controller.startServer();
    }
}
