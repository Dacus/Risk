package common;

/**
 * Created by Andrea on 7/16/2015.
 */
public interface MessageHandler {
    public void handleMessage(Message msg);
    public void setController(Controller controller);
}
