package ClientServerExample.serverapp;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import ClientServerExample.common.Message;
import ClientServerExample.common.MessageProvider;
import ClientServerExample.common.MessageTag;
import singleserver.CMSocketServer;
import singleserver.SingleServerMessageHandler;
import singleserver.SingleServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents the central server. It keeps record of each connected client and continuously listens for
 * new connections. The MainServer has its own controller called MainServerController.
 *
 * Each client has a corresponding server (CMSocketServer) to whom it communicates, and each CMSocketServer ("single server")
 * has its own SingleServerController. To keep track of all clients, the main server has a Hash Map where the keys
 * are client ids and the values are SingleServerControllers.
 *
 * The main server can reach out to clients through the SingleServerControllers saved in the hash map.
 * When the main server needs to communicate with clients (e.g. to send them a message), it will iterate through the map
 * and call methods on one/some/each SingleServerController object in the map, as needed.
 */
public class MainServer implements Runnable{
	private HashMap<Integer, SingleServerController> map = new HashMap<>();
	private static final int PORT_NO = 9990;
	private AtomicInteger id = new AtomicInteger(1);
	private AtomicBoolean isRunning=new AtomicBoolean(true);
	private SingleServerMessageHandler messageHandler;
	private MainServerController mainServerController;
	private Socket client;

	public MainServer(MainServerController supremeServerController){
		this.messageHandler=new SingleServerMessageHandler();
		this.mainServerController=supremeServerController;
	}

	public void run() {
		System.out.println("Starting the multiple socket server at port: " + PORT_NO);
		try {			
			ServerSocket serverSocket = new ServerSocket(PORT_NO);
			
			System.out.println("Multiple Socket Server Initialized");

			while (isRunning.get()) {
				System.out.println("Listening for clients");

				client = serverSocket.accept();

				if (client!=null) {
					//create a new server and a new controller for it
					CMSocketServer server = new CMSocketServer(client, messageHandler);
					SingleServerController controller = new SingleServerController(server, mainServerController, messageHandler);

					//set the id of the controller
					controller.setID(id.get());

					//add controller to map
					map.put(id.get(), controller);

					//display the number of currently connected clients on the Server GUI
					mainServerController.setNumberOfOnlineClients(map.size());

					System.out.println("Client " + id.get() + " added");
					id.getAndIncrement();

					//Keep each client on its own thread
					Thread thread = new Thread(server);
					thread.start();
				}
			}
		} catch (Exception e) {
			System.out.println("Main server stops");
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Sends the same message to all clients
	 *
	 * @param msg	message to be sent
	 */
	public void sendGlobalMessage(Message msg){
		for (int key: map.keySet()){
			map.get(key).sendMessage(msg);
		}
	}

	/**
	 * Stops all clients
	 */
	public void stopSingleServers(){
		for (int key: map.keySet()) {
			SingleServerController c = map.get(key);
			c.stopClient();
		}
	}

	/**
	 * Remove the client identified by "id".
	 * Display the number of currently connected clients on the Server GUI if the operation was successful
	 *
	 * @param id The id of the client to be removed
	 */
	public void removeSingleServer(int id){
		if (map.remove(id)!=null) {
			mainServerController.setNumberOfOnlineClients(map.size());
		}
	}

	/**
	 * Stops the Main Server exiting the while loop.
	 */
	public void stop(){
		this.isRunning.set(false);
	}
}
