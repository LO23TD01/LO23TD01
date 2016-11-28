package network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import data.ClientDataEngine;
import data.Profile;
import network.messages.AcceptReplayMessage;
import network.messages.ConnectionMessage;
import network.messages.DropTableMessage;
import network.messages.GetProfileMessage;
import network.messages.IMessage;
import network.messages.LogoutUserRequestMessage;
import network.messages.ThrowDiceMessage;
import network.messages.QuitGameMessage;
import network.messages.RefuseReplayMessage;
import network.messages.UpdateProfileMessage;

public class ComClient implements ComClientInterface{
	private int 					serverPort;
	private String 					ipAdress;
	private Socket 					socketToServer;
	private boolean 				isStopped = false;
	private SocketServerHandler 	server;
	private ClientDataEngine		clientData;
	
	
	/*
	 * 
	 * Constructor
	 * 
	 */

	public ComClient(String ipAdress, int serverPort) {
		this.serverPort = serverPort;
		this.ipAdress = ipAdress;
		
		try {
			socketToServer = new Socket(ipAdress, serverPort);
			
			System.out.println("Client connecté au serveur");
			
			SocketServerHandler server = new SocketServerHandler(socketToServer, this);
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
	public void throwDice(UUID user) {
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
		sendMessage(new UpdateProfileMessage(user,profile));
	}

	@Override
	public void dropTable(UUID tableId) {
		sendMessage(new DropTableMessage(tableId));
	}

	@Override
	public void quit(UUID user) {
		sendMessage(new QuitGameMessage(user));
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
		sendMessage(new AcceptReplayMessage(user));
	}

	@Override
	public void refuseReplay(UUID user) {
		sendMessage(new RefuseReplayMessage(user));
	}

	@Override
	public void logoutUserRequest(UUID user) {
		sendMessage(new LogoutUserRequestMessage(user));
		
	}

    @Override
    public void connection(Profile user) {
        ConnectionMessage msg = new ConnectionMessage(user);
        sendMessage(msg);
    }

    @Override
    public void getProfile(UUID user) {
        sendMessage(new GetProfileMessage(user));
    }

	
	
	/*
	 * 
	 * Getters & setters
	 * 
	 */
	
	public ClientDataEngine getClientData() {
		return clientData;
	}

	public void setClientData(ClientDataEngine clientData) {
		this.clientData = clientData;
	}
	
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

    @Override
    public void sendMessage(String msg) {
        // TODO Auto-generated method stub
        
    }
}
