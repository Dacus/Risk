package tora.train.risk.ClientServerExample.common;

/**
 * Interface for all controllers
 *
 * Created by Andrea on 7/16/2015.
 */
public interface Controller {
    public void stopRunning();
    public void sendMessage(Message msg);
    public Message readMessage();
}
