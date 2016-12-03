package network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import data.client.ClientDataEngine;
import data.GameTable;
import data.Profile;
import network.messages.AcceptReplayMessage;
import data.Rules;
import data.User;
import network.messages.ConnectionMessage;
import network.messages.CreateTableMessage;
import network.messages.GetProfileMessage;
import network.messages.IMessage;
import network.messages.LaunchGameMessage;
import network.messages.LogoutUserRequestMessage;
import network.messages.ThrowDiceMessage;
import network.messages.SelectDiceMessage;
import network.messages.QuitGameMessage;
import network.messages.RefuseReplayMessage;
import network.messages.UpdateProfileMessage;
import network.messages.AskJoinTableMessage;
import network.messages.askRefreshUserListMessage;

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
	public void throwDice(UUID user, boolean d1, boolean d2, boolean d3) {
		sendMessage(new ThrowDiceMessage(user, d1, d2, d3));
	}

	@Override
	public void launchGame(UUID userUuid) {
        LaunchGameMessage msg = new LaunchGameMessage(userUuid);
        sendMessage(msg);
	}

	@Override
	public void createNewTable(UUID user, String name, String pwd, int min, int max, int token, boolean withSpec,
			boolean withChat, Rules rules) {
		CreateTableMessage msg = new CreateTableMessage(user, name, pwd, min, max, token, withSpec, withChat, rules);
		sendMessage(msg);
	}

	@Override
	public void selectDice(UUID user, boolean d1, boolean d2, boolean d3) {
		sendMessage(new SelectDiceMessage(user, d1, d2, d3));
	}

	@Override
	public void updateUserProfile(UUID user, Profile profile) {
		sendMessage(new UpdateProfileMessage(user,profile));
	}

	//DropTable supprimé

	@Override
	public void quit(UUID user, GameTable table) {
		sendMessage(new QuitGameMessage(user, table));
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
		sendMessage(new askRefreshUserListMessage(user));
	}

	@Override
	public void askJoinTable(UUID user, UUID tableId, boolean asPlayer) {
		sendMessage(new AskJoinTableMessage(user, tableId, asPlayer));
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
    public void getProfile(UUID user, UUID sender) {
        sendMessage(new GetProfileMessage(user, sender));
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
