package ClientServerExample.common;

/**
 * Created by Andrea on 7/18/2015.
 */
public class MessageProvider {
    private static final Message msg=new Message();

    public static synchronized Message getMessage(MessageTag tag){
        msg.setOperation(tag);
        msg.clearContents();
        return msg;
    }
}
