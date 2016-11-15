package network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import data.Profile;
import network.messages.IMessage;

public class ComClient implements ComClientInterface{
	private int 					serverPort;
	private String 					ipAddress;
	private Socket 					socketToServer;
	private boolean 				isStopped = false;
	private SocketServerHandler 	server;


	/*
	 *
	 * Constructor
	 *
	 */

	public ComClient(String ipAddress, int serverPort) {
		this.serverPort = serverPort;
		this.ipAddress = ipAddress;

		try {
			socketToServer = new Socket(ipAddress, serverPort);

			System.out.println("Client connecté au serveur");

			SocketServerHandler server = new SocketServerHandler(socketToServer);
        	new Thread(server).start();
        	this.server = server;

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 *
	 * Methods
	 *
	 */

	public void sendMessage(IMessage message){
		if(server != null)
			server.sendMessage(message);
	}


	/*
	 *
	 * Overridden methods
	 * 	(ComClientInterface)
	 */

	@Override
	public void connection(Profile user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void throwDice(UUID user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void launchGame(UUID user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createNewTable(UUID user, String name, String pwd, int min, int max, int token, boolean withSpec,
			boolean withChat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectDice(UUID user, boolean d1, boolean d2, boolean d3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUserProfile(UUID user, Profile profile) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropTable(UUID tableId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void quit(UUID user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUsersList(UUID user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void askQuitTable(UUID tableId, UUID user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void askRefreshUsersList(UUID user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void askJoinTable(UUID user, UUID tableId, boolean asPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptReplay(UUID user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refuseReplay(UUID user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logoutUserRequest(UUID user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getProfile(UUID user) {
		// TODO Auto-generated method stub

	}


	/*
	 *
	 * Getters & setters
	 *
	 */

	public boolean isStopped() {
		return isStopped;
	}

	public void stop(){
        this.isStopped = true;
        try {
            this.socketToServer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
}
