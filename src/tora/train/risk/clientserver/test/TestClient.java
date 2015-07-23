package tora.train.risk.clientserver.test;

import tora.train.risk.clientserver.clientapp.ClientAppController;
import tora.train.risk.clientserver.clientapp.ClientAppFrame;

import java.io.IOException;


public class TestClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientAppFrame frame=new ClientAppFrame();
        ClientAppController clientApp = new ClientAppController(frame);
    }
}
