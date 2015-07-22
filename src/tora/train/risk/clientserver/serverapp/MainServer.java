package tora.train.risk.clientserver.serverapp;

import tora.train.risk.clientserver.common.Message;
import tora.train.risk.clientserver.common.MessageTag;
import tora.train.risk.clientserver.singleserver.CMSocketServer;
import tora.train.risk.clientserver.singleserver.SingleServerController;
import tora.train.risk.clientserver.singleserver.SingleServerMessageHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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

/**
 * Faptul ca MainServer este un Runnable indica
 */
public class MainServer implements Runnable{
	private HashMap<Integer, SingleServerController> map = new HashMap<>();
    private HashMap<Integer, String> clientMap = new HashMap<>();

    private static final int PORT_NO = 9990;
    private static final int MAX_NUMBER_OF_CLIENTS=2;

	private AtomicInteger id = new AtomicInteger(1);
	private boolean isRunning= true;

	private SingleServerMessageHandler messageHandler;
	private MainServerController mainServerController;

	private Socket client;
    private AtomicInteger readyCounter = new AtomicInteger(0);

    public MainServer(MainServerController supremeServerController){
		this.messageHandler=new SingleServerMessageHandler();
		this.mainServerController=supremeServerController;
	}

	public void run() {
		System.out.println("Starting the multiple socket server at port: " + PORT_NO);
		try {			
			ServerSocket serverSocket = new ServerSocket(PORT_NO);
			serverSocket.setSoTimeout(1000);
			System.out.println("Multiple Socket Server Initialized");

			while (isRunning) {
				System.out.println("Listening for clients");

                client = serverSocket.accept();

                //create a new server and a new controller for it
                CMSocketServer server = new CMSocketServer(client, messageHandler);
                SingleServerController controller = new SingleServerController(server, mainServerController, messageHandler);

                if (map.size() < MAX_NUMBER_OF_CLIENTS){

                    //set the id and the name of the client
                    controller.setID(id.get());
                    System.out.println("Client " + id.get() + " connected");

                    //send names of online clients
                    controller.sendListOfOnlineClients(new ArrayList<String>(clientMap.values()));

                    //add client to map
                    map.put(id.get(), controller);

                    //display the number of currently connected clients on the Server GUI
                    mainServerController.setNumberOfOnlineClients(map.size());

                    id.getAndIncrement();

                    //Keep each client on its own thread
                    Thread thread = new Thread(server);
                    thread.start();

                }
                else{
                    //tell client that he cannot connect
                    controller.restrictClient(MAX_NUMBER_OF_CLIENTS);
                }
			}
		} catch (Exception e) {
			System.out.println("Main server stops");
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
            broadcastRemovingPlayer(clientMap.remove(id));
		}
	}

	/**
	 * Stops the Main Server exiting the while loop.
	 */
	public synchronized void stop() {
		this.isRunning = false;
	}

    public void incrementReadyCounter() {
        int x = readyCounter.getAndIncrement();
        if (x == map.size() - 1) {
            Message msg = new Message(MessageTag.START);
            msg.addObject("StartGame");
            sendGlobalMessage(msg);
        }
    }

    /**
     * Save the client's name and Id in a map.
     * Tell all other clients about the newcomer
     *
     * @param name  name of the new client
     * @param id    id of the new client
     */
    public void setClientName(String name, int id) {
        this.clientMap.put(id, name);
        broadcastAddingNewPlayer(name);
    }

    /**
     * When a new client connects, all other clients will know about it.
     * The name of the new client is sent to all other connected clients.
     *
     * @param name  the name of the new client
     */
    public void broadcastAddingNewPlayer(String name){
        Message msg=new Message(MessageTag.NEW_PLAYER_CONNECTED);
        msg.addObject(name);
        sendGlobalMessage(msg);
    }

    /**
     * When a client disconnects, the server sends a message to all online clients
     * letting them know about that client.
     *
     * @param name
     */
    public void broadcastRemovingPlayer(String name){
        Message msg=new Message(MessageTag.PLAYER_DISCONNECTED);
        msg.addObject(name);
        sendGlobalMessage(msg);
    }
}
