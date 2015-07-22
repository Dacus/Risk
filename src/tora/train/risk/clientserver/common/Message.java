package tora.train.risk.clientserver.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Means of communication between the client and the server
 */

/**
 * Gandeste-te *intotdeauna* daca are sens sa schimbi continutul unei clase (daca este nevoie de setteri). Daca nu,
 * atunci este o idee buna sa declari cat mai multe field-uri ale unei clase (de preferat toate) final. In acest mod
 * este foarte usor de folosit clasa intr-un mediu multithreaded (no state, no locks, no synchronization problems).
 *
 * In plus, este mai usor de inteles si intretinut codul care nu modifica starea interna a obiectelor.
 */
public class Message implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * Daca numele tipului (MessageTag) este diferit de numele instantei (operation), inseamna ca numele tipului
	 * nu comunica suficienta informatie despre ce reprezinta. Considera redenumirea tipului la ceva mai expresiv:
	 * MessageType / OperationType / MessageOperation / InstructionType ?
	 */
	private MessageTag operation;

	/**
	 * Instantele de tip List se numesc de obicei
	 * 	1.	<nume_sugestiv>List. 	Exemplu: contentList
	 * 	2.	<nume_sugestiv_plural>. Exemplu: contents / items / entries / listeners / etc.
	 */

	/**
	 * Daca alegi sa faci clasa imutabila, ai grija ca si field-urile sa fie imutabile. De exemplu:
	 * 		private final List<Object> contents;
	 *
	 * 		public List<Object> getContents() {
	 * 			return contents;
	 * 		}
	 *
	 * 	Apoi undeva in cod:
	 *
	 * 		message.getContents().clear()
	 *
	 * 	Este clar ca desi referinta contents nu poate fi modificat, continutul ei poate. Si asta poate cauza probleme.
	 * 	Considera utilizarea unei liste imutabile, eg UnmodifiableList
	 */
	private List<Object> content;
	
	public Message(MessageTag op, List<Object> list){
		this.operation=op;
		this.content=list;
	}

	public Message(MessageTag op){
		this.operation=op;

		/**
		 * De fiecare data cand folosesti new, consumi putin timp pentru alocarea memoriei si apelul constructorului.
		 * Considera utilizarea unor instante predefinite cum ar fi Collections.EMPTY_LIST pentru a evita penalty-ul de
		 * performanta, si a comunica mai bine faptul ca ai nevoie de o lista goala.
		 *
		 * NB: Collections.EMPTY_LIST este imutabila. Ce consecinte ar avea sa adaugi elemente in lista vida pe care
		 * o folosesc toate componentele din sistem?
		 */
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
