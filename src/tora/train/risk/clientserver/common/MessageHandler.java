package tora.train.risk.clientserver.common;

/**
 * Created by Andrea on 7/16/2015.
 */
public interface MessageHandler {
    public void handleMessage(Message msg);

    /**
     * De ce are nevoie un MessageHandler de metoda setController? Daca eu am o componenta care foloseste un
     * MessageHandler (eg o functie), de ce as avea nevoie sa apelez setController pe acel handler?
     */
    public void setController(Controller controller);
}
