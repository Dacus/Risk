package ClientServerExample.singleclient;

import ClientServerExample.common.Message;
import ClientServerExample.common.MessageHandler;

import java.io.*;
import java.net.*;

/**
 * Client Thread that continuously reads incoming messages received from its corresponding CSocketServer.
 * Delegates message interpretation to a MessageHandler.
 *
 */
public class CSocketClient implements Runnable{
	private int clientId;
	private MessageHandler messageHandler;
	private final String hostname="localhost";
	private boolean isRunning;
	private final int PORT_NO=9990;

	private Socket socketClient;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	public CSocketClient(MessageHandler handler){
		this.messageHandler=handler;
	}

	public void connect() throws IOException {
		System.out.println("Attempting to connect to " + hostname + ":" + PORT_NO);
		socketClient = new Socket(hostname, PORT_NO);
		System.out.println("Connection Established");

		outputStream=new ObjectOutputStream(socketClient.getOutputStream());
		inputStream=new ObjectInputStream(socketClient.getInputStream());

		isRunning=true;
		Thread th=new Thread(this);
		th.start();

		System.out.println("Client Th: " + th.getName() + " created" );
	}

	/**
	 * Stops the thread by stopping the while loop in the run() method
	 */
	public synchronized void stopRunning(){
		this.isRunning=false;
	}

	/**
	 * Closes the socket
	 *
	 * @throws IOException
	 */
	public void closeConnection() throws IOException {
		socketClient.close();
	}

	/**
	 * Sends a message to the its corresponding CSocketServer
	 *
	 * @param message	the message to be sent
	 * @throws IOException
	 */
	public void sendMessage(Message message) throws IOException{
		System.out.println("Client sends message:" + message);
		outputStream.writeObject(message);
		outputStream.flush();
	}

	/**
	 * Reads the message received from its corresponding CSocketServer
	 *
	 * @return	the message it managed to read
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Message readMessage() throws ClassNotFoundException, IOException{
       Message msg=(Message)inputStream.readObject();
       System.out.println("Server responds:" + msg);
       return msg;
	}

	/**
	 * While is running, it reads the messages received from the server
	 * and lets the MessageHandler to process them
	 */
	@Override
	public void run() {
		while (isRunning){
			try {
				Message message = readMessage();
				System.out.println("Message to process:");
				messageHandler.handleMessage(message);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks if the client is still running
	 *
	 * @return	boolean value indicating the state of the client thread, running or stopped
	 */
	public boolean isRunning(){
		return isRunning;
	}

	/**
	 *
	 * @return the id of the client
	 */
	public int getClientId(){
		return clientId;
	}

	/**
	 * Sets the id of the thread to the value of the "id" parameter
	 *
	 * @param id
	 */
	public void setClientId(int id){
		this.clientId=id;
	}
}