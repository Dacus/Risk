package tora.train.risk.clientserver.clientapp;

import tora.train.risk.clientserver.singleclient.SingleClientController;
import tora.train.risk.clientserver.singleclient.SingleClientFrame;
import tora.train.risk.clientserver.singleclient.SingleClientMessageHandler;

/**
 * Created by Andrea on 7/21/2015.
 */
public class ClientAppThread implements Runnable {
    private SingleClientController singleClientController;
    private SingleClientFrame clientFrame;
    private SingleClientMessageHandler clientMsgHandler;

    public ClientAppThread(String name){
        this.clientMsgHandler=new SingleClientMessageHandler();
        this.clientFrame = new SingleClientFrame(name);
        this.singleClientController = new SingleClientController(clientFrame, clientMsgHandler);
    }

    @Override
    public void run(){
        System.out.println("Running client");
    }
}
