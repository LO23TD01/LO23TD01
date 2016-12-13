package network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import data.client.ClientDataEngine;
import data.ChatMessage;
import data.GameTable;
import data.Profile;
import network.messages.AcceptReplayMessage;
import network.messages.AnswerStopGameMessage;
import data.Rules;
import data.User;
import network.messages.ConnectionMessage;
import network.messages.CreateTableMessage;
import network.messages.GetProfileMessage;
import network.messages.IMessage;
import network.messages.LaunchGameMessage;
import network.messages.LogoutUserRequestMessage;
import network.messages.NetworkChatMessage;
import network.messages.ThrowDiceMessage;
import network.messages.SelectDiceMessage;
import network.messages.QuitGameMessage;
import network.messages.RefuseReplayMessage;
import network.messages.UpdateProfileMessage;
import network.messages.AskJoinTableMessage;
import network.messages.AskQuitTableMessage;
import network.messages.askRefreshUserListMessage;

/**
 * Client Network engine
 */
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

	/**
	 * Constructor for the ComClient class. Creates and connects a socket to the indicated port and address
	 * @param ipAdress	the IP address of the game server
	 * @param serverPort	the port of the game server the socket will connect to 
	 */
	public ComClient(String ipAdress, int serverPort) {
		this.serverPort = serverPort;
		this.ipAdress = ipAdress;
		
		try {
			socketToServer = new Socket(ipAdress, serverPort);
			
			System.out.println("Client connect� au serveur");
			
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
	
	/**
	 * Sends a message to the server via the SocketServerHandler sendMessage method
	 * @param message	IMessage to be sent 
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
	
	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#throwDice(java.util.UUID, boolean, boolean, boolean)
	 */
	@Override
	public void throwDice(UUID user, boolean d1, boolean d2, boolean d3) {
		sendMessage(new ThrowDiceMessage(user, d1, d2, d3));
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#launchGame(java.util.UUID)
	 */
	@Override
	public void launchGame(UUID userUuid) {
        LaunchGameMessage msg = new LaunchGameMessage(userUuid);
        sendMessage(msg);
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#createNewTable(java.util.UUID, java.lang.String, java.lang.String, int, int, int, boolean, boolean, data.Rules)
	 */
	@Override
	public void createNewTable(UUID user, String name, String pwd, int min, int max, int token, boolean withSpec,
			boolean withChat, Rules rules) {
		CreateTableMessage msg = new CreateTableMessage(user, name, pwd, min, max, token, withSpec, withChat, rules);
		sendMessage(msg);
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#selectDice(java.util.UUID, boolean, boolean, boolean)
	 */
	@Override
	public void selectDice(UUID user, boolean d1, boolean d2, boolean d3) {
		sendMessage(new SelectDiceMessage(user, d1, d2, d3));
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#updateUserProfile(java.util.UUID, data.Profile)
	 */
	@Override
	public void updateUserProfile(UUID user, Profile profile) {
		sendMessage(new UpdateProfileMessage(user,profile));
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#quit(java.util.UUID, java.util.UUID)
	 */
	@Override
	public void quit(UUID user, UUID tableId) {
		// Quit pour le diagramme Quitter Partie (en cours de jeu)
		sendMessage(new QuitGameMessage(user, tableId));
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#updateUsersList(java.util.UUID)
	 */
	@Override
	public void updateUsersList(UUID user) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#askQuitTable(java.util.UUID, java.util.UUID)
	 */
	@Override
	public void askQuitTable(UUID tableId, UUID user) {
		sendMessage(new AskQuitTableMessage(tableId,user));
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#askRefreshUsersList(java.util.UUID)
	 */
	@Override
	public void askRefreshUsersList(UUID user) {
		sendMessage(new askRefreshUserListMessage(user));
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#askJoinTable(java.util.UUID, java.util.UUID, boolean)
	 */
	@Override
	public void askJoinTable(UUID user, UUID tableId, boolean asPlayer) {
		sendMessage(new AskJoinTableMessage(user, tableId, asPlayer));
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#acceptReplay(java.util.UUID)
	 */
	@Override
	public void acceptReplay(UUID user) {
		sendMessage(new AcceptReplayMessage(user));
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#refuseReplay(java.util.UUID)
	 */
	@Override
	public void refuseReplay(UUID user) {
		sendMessage(new RefuseReplayMessage(user));
	}

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#logoutUserRequest(java.util.UUID)
	 */
	@Override
	public void logoutUserRequest(UUID user) {
		sendMessage(new LogoutUserRequestMessage(user));
		stop();
	}

    /* (non-Javadoc)
     * @see network.client.ComClientInterface#connection(data.Profile)
     */
    @Override
    public void connection(Profile user) {
        ConnectionMessage msg = new ConnectionMessage(user);
        sendMessage(msg);
    }

    /* (non-Javadoc)
     * @see network.client.ComClientInterface#getProfile(java.util.UUID, java.util.UUID)
     */
    @Override
    public void getProfile(UUID user, UUID sender) {
        sendMessage(new GetProfileMessage(user, sender));
    }

	
	
	/*
	 * 
	 * Getters & setters
	 * 
	 */
	
	/**
	 * Getter for the clientData attribute
	 * @return	a ClientDataEngine object
	 */
	public ClientDataEngine getClientData() {
		return clientData;
	}

	/**
	 * Setter for the clientData attribute
	 * @param	the clientData to set
	 */
	public void setClientData(ClientDataEngine clientData) {
		this.clientData = clientData;
	}
	
	/**
	 * Getter for the isStopped attribute
	 * @return	a boolean indicating whether the ComClient object is stopped
	 */
	public boolean isStopped() {
		return isStopped;
	}
	
	/**
	 * Stops the ComClient object
	 */
	public void stop(){
        this.isStopped = true;
        try {
            this.socketToServer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    /* (non-Javadoc)
     * @see network.client.ComClientInterface#sendMessage(data.ChatMessage)
     */
    @Override
    public void sendMessage(ChatMessage msg) {
		sendMessage(new NetworkChatMessage(msg));
    }

	/* (non-Javadoc)
	 * @see network.client.ComClientInterface#answerStopGame(java.util.UUID, boolean, java.util.UUID)
	 */
	@Override
	public void answerStopGame(UUID tableId, boolean answer, UUID user) {
		sendMessage(new AnswerStopGameMessage(tableId, answer, user));
	}

}
