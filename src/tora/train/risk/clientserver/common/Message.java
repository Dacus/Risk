package tora.train.risk.clientserver.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Means of communication between the client and the server
 */
public class Message implements Serializable{
	private static final long serialVersionUID = 1L;

	private MessageType type;
	private List<Object> contents;
	
	public Message(MessageType type, List<Object> list){
		this.type=type;
		this.contents=list;
	}

	public Message(MessageType type){
		this.type=type;
		this.contents= new ArrayList<Object>();
	}
	
	public MessageType getType() {
		return type;
	}

	public Object getElementAt(int i) {
		return contents.get(i);
	}
	
	public void addElement(Object obj){
		this.contents.add(obj);
	}

	@Override
	public String toString() {
		return "Message [type=" + type + ", contents=" + contents + "]";
	}
}
