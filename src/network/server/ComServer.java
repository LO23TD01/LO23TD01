package network.server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import data.GameTable;
import data.Profile;

import java.net.ServerSocket;

public class ComServer implements Runnable, ComServerInterface {

	private int				serverPort;
	private ServerSocket	serverSocket;
	private boolean			isStopped    = false;
	private static			HashMap<String, SocketClientHandler> connectedClients = new HashMap<String, SocketClientHandler>();

	//Used to test client/server communications on local
	//private static			HashMap<Integer, ClientHandlerRunnable> connectedClients = new HashMap<String, ClientHandlerRunnable>();
	//int i = 0;


	/*
	 *
	 * Constructor
	 *
	 */

	public ComServer(int serverPort) {
		this.serverPort = serverPort;
		this.serverSocket = startServer();
	}

	/*
	 *
	 * Methods
	 *
	 */

	public ServerSocket startServer() {

	    try {
	        ServerSocket socket = new ServerSocket(serverPort);
	        System.out.println("Server socket started on port :"+serverPort);
	        return socket;

	    } catch (IOException e) {
	    	// throw an exception if unable to bind at given port
	        System.out.println("Unable to start Server socket on port :"+serverPort);
	        e.printStackTrace();

	    }
	    return null;
	}

	/*
	//Used to test client/server communication
	public void sendMessage(int num, IMessage message){
		//TO-DO : Changer ipAddress par UUID quand ils seront gérés par DATA

		SocketClientHandler client = connectedClients.get(num);

		if(client != null)
			client.sendMessage(message);
	}*/

	/*
	 *
	 * Overridden methods
	 * 	(Runnable)
	 */

	@Override
	public void run() {
		while(! isStopped()){
	        Socket clientSocket = null;
	        try {
	            clientSocket = this.serverSocket.accept();

	            System.out.println("Nouveau client connecté");

	        } catch (IOException e) {
	            if(isStopped()) {
	                System.out.println("Server Stopped.") ;
	                return;
	            }
	            e.printStackTrace();
	        }

	        	SocketClientHandler client = new SocketClientHandler(clientSocket);
	        	new Thread(client).start();
	            connectedClients.put(clientSocket.getInetAddress().toString(), client);

	            //Used to test client/server communication on local
	        	//connectedClients.put(i, client);
	        	//i++;
	    }
	}

	/*
	 *
	 * Overridden methods
	 * 	(ComServerInterface)
	 */

	@Override
	public void sendResult(List<UUID> receivers, int r1, int r2, int r3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(List<UUID> receivers, String senderLogin, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showTimer(UUID user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNewTable(List<UUID> receivers, GameTable tableinfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendSelection(List<UUID> receivers, UUID player, boolean d1, boolean d2, boolean d3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateChips(List<UUID> receivers, UUID player, int nb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateChips(List<UUID> receivers, UUID win, UUID lose, int nb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasWon(List<UUID> receivers, UUID winner) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startTurn(List<UUID> receivers, UUID player, boolean isLastLaunch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendProfileUpdate(List<UUID> receivers, UUID userUpdated, Profile data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void kick(List<UUID> receivers, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void askStopGameEveryUser(List<UUID> receivers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopGameAccepted(List<UUID> receivers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshUserList(UUID user, List<UUID> receivers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void raiseException(UUID user, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newPlayerOnTable(List<UUID> receivers, Profile user, UUID tableID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newSpectatorOnTable(List<UUID> receivers, Profile user, UUID tableID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasAccepted(UUID user, List<UUID> receivers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasRefused(UUID user, List<UUID> receivers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newUser(List<UUID> receivers, Profile user) {
		// TODO Auto-generated method stub

	}

	/*
	 *
	 * Getters & setters
	 *
	 */

	public synchronized boolean isStopped() {
		return isStopped;
	}

	public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }

    }

}
