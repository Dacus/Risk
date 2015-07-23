package tora.train.risk.clientserver.common;

/**
 * Interface for all controllers
 *
 * Created by Andrea on 7/16/2015.
 */
public interface Controller {
    void startRunning();
    void stopRunning();
    void writeMessage(Message msg);
    Message readMessage();
}
