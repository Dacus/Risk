package ClientServerExample.singleserver;

import ClientServerExample.common.Message;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

/**
 * Also called "single server", CMSocketServer communicates with a CSocketClient. It delegates message processing
 * responsibilities to a MessageHandler.
 */
public class CMSocketServer implements Runnable {
	private SingleServerMessageHandler messageHandler;

	private ObjectInputStream serverInputStream;
	private ObjectOutputStream serverOutputStream;
	private boolean isRunning;

	public CMSocketServer(Socket connection, SingleServerMessageHandler handler)
			throws IOException {
		this.isRunning = true;
		this.messageHandler=handler;

		serverInputStream = new ObjectInputStream(connection.getInputStream());
		serverOutputStream = new ObjectOutputStream(
				connection.getOutputStream());
	}

	/**
	 * The thread executes rounds: it reads a message from the client and process it.
	 */
	public void run() {
		while (isRunning) {
			try {
				Message msg = readMessage();
				messageHandler.handleMessage(msg);
			} catch (Exception e) {
				System.out.println("CMSocketServer error");
			}
		}
	}

	/**
	 * Reads an incoming message from the client arriving through the input stream
	 *
	 * @return the message received from the client to which this server is connected
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Message readMessage() throws IOException,
			ClassNotFoundException {
		
		Message msg = (Message) serverInputStream.readObject();
		return msg;
	}

	/**
	 * Sends a message to the client through the output stream
	 *
	 * @param msg message to be sent
	 * @throws IOException
	 */
	public void sendMessage(Message msg) throws IOException {
		
		serverOutputStream.writeObject(msg);
		System.out.println("Server sends:" + msg);
	}

	/**
	 * Stops the thread from running by changing the boolean variable polled at each round
	 *
	 * @throws IOException
	 */
	public synchronized void stopRunning() throws IOException {
		this.isRunning = false;
	}
}
