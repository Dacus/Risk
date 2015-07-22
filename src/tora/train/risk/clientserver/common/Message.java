package tora.train.risk.clientserver.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Means of communication between the client and the server
 */
public class Message implements Serializable{
	private static final long serialVersionUID = 1L;

	private MessageTag operation;
	private List<Object> content;
	
	public Message(MessageTag op, List<Object> list){
		this.operation=op;
		this.content=list;
	}

	public Message(MessageTag op){
		this.operation=op;
		this.content=new ArrayList<Object>();
	}

	public Message(){
		this.content=new ArrayList<Object>();
	}
	
	public MessageTag getOperation() {
		return operation;
	}

	public List<Object> getContent() {
		return content;
	}
	
	public void addObject(Object obj){
		this.content.add(obj);
	}

	@Override
	public String toString() {
		return "Message [operation=" + operation + ", content=" + content + "]";
	}

	public void setOperation(MessageTag tag){
		this.operation=tag;
	}

	public void clearContents(){
		this.content.clear();
	}

}
