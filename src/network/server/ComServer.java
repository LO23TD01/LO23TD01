package network.server;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;

import data.ChatMessage;
import data.GameTable;
import data.Profile;
import data.State;
import data.TurnState;
import data.server.InterfaceSingleThreadData;
import network.messages.NewUserMessage;
import network.messages.HasSelectedMessage;
import network.messages.IsTurnMessage;
import network.messages.HasThrownMessage;
import network.messages.AddNewTableMessage;
import network.messages.HasWonMessage;
import network.messages.AskStopGameMessage;
import network.messages.ChangeStateMessage;
import network.messages.ChangeTurnStateMessage;
import network.messages.ExAequoMessage;
import network.messages.HasAcceptedMessage;
import network.messages.HasRefusedMessage;
import network.messages.KickedMessage;
import network.messages.LogoutUserRequestMessage;
import network.messages.NetworkChatMessage;
import network.messages.PlayerQuitGameMessage;
import network.messages.RaiseExceptionMessage;
import network.messages.SendProfileMessage;
import network.messages.SendTableInfoMessage;
import network.messages.SetCreatorMessage;
import network.messages.TablesUsersListMessage;
import network.messages.refreshUserListMessage;
import network.messages.SetSelectionMessage;
import network.messages.ShowTimerMessage;
import network.messages.StartTurnMessage;
import network.messages.StopGameMessage;
import network.messages.UpdateChipsChargeMessage;
import network.messages.UpdateChipsDechargeMessage;
import network.messages.HasLostMessage;
import data.User;
import network.messages.NewPlayerOnTableMessage;
import network.messages.NewSpectatorOnTableMessage;
import network.messages.UpdateTableInfoMessage;
import java.net.ServerSocket;

/**
 * Server application
 * @author lenovo
 *
 */
public class ComServer implements Runnable, ComServerInterface {

	private int				serverPort;
	private ServerSocket	serverSocket;
	private boolean			isStopped    = false;
	private 			HashMap<String, SocketClientHandler> connectedClients = new HashMap<String, SocketClientHandler>();
	private InterfaceSingleThreadData dataEngine;

	/*
	 *
	 * Constructor
	 *
	 */

	/**
	 * @param serverPort Server port used for communication
	 */
	public ComServer(int serverPort) {
		this.serverPort = serverPort;
		this.serverSocket = startServer();
		this.dataEngine = null;
	}

	/*
	 *
	 * Methods
	 *
	 */

	/**
	 * @return null
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

	/**
	 * @param adresse Connected client address
	 * @param uuid Unique ID of connected client
	 */
	public void replaceWithUUID (String adresse, UUID uuid){
		connectedClients.put(uuid.toString(), connectedClients.get(adresse));
		connectedClients.remove(adresse);
	}
	
	/**
	 * @param adresse Connected client address
	 */
	public void removeSocket (String adresse){
		connectedClients.remove(adresse);
	}


	/*
	 *
	 * Overridden methods
	 * 	(Runnable)
	 */

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(! isStopped()){
	        Socket clientSocket = null;
	        try {
	            clientSocket = this.serverSocket.accept();

	            System.out.println("Nouveau client connectï¿½");

	        } catch (IOException e) {
	            if(isStopped()) {
	                System.out.println("Server Stopped.");
	                return;
	            }
	            e.printStackTrace();
	        }

	        	SocketClientHandler client = new SocketClientHandler(clientSocket, this);
	        	new Thread(client).start();
	            connectedClients.put(clientSocket.getInetAddress().toString(), client);
	    }
	}

	/**
	 * @param SocketClientHandler à supprimer
	 */
	public String removeClient(SocketClientHandler client){
		for (String key : connectedClients.keySet()) {
			if(connectedClients.get(key).equals(client)){
				connectedClients.remove(key);
				return key;
			}

		}
		return null;
	}

	/*
	 *
	 * Overridden methods
	 * 	(ComServerInterface)
	 */

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#sendResult(java.util.List, int, int, int)
	 */
	@Override
	public void sendResult(List<UUID> receivers, UUID user, int r1, int r2, int r3) {
		SocketClientHandler handler;
		for (UUID receiver : receivers) {
			handler = connectedClients.get(receiver.toString());
			if (handler != null) {
				handler.sendMessage(new HasThrownMessage(user, r1, r2, r3));
			}
		}

	}

    /* (non-Javadoc)
     * @see network.server.ComServerInterface#sendMessage(java.util.List, data.ChatMessage)
     */
    @Override
    public void sendMessage(List<UUID> receivers, ChatMessage chatMsg) {
        for (UUID receiver : receivers) {
            SocketClientHandler handler = connectedClients.get(receiver);
            if (handler != null) {
                handler.sendMessage(new NetworkChatMessage(chatMsg));
            }
        }
    }

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#sendSelection(java.util.List, java.util.UUID, boolean, boolean, boolean)
	 */
	@Override
	public void sendSelection(List<UUID> receivers, UUID player, boolean d1, boolean d2, boolean d3) {
		for (UUID uuid : receivers) {
			SocketClientHandler handler = connectedClients.get(uuid.toString());
			if (handler != null)
				handler.sendMessage(new HasSelectedMessage(player, d1, d2, d3));
		}

		SocketClientHandler handler = connectedClients.get(player.toString());
		if (handler != null)
			handler.sendMessage(new SetSelectionMessage(d1, d2, d3));

	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#updateChips(java.util.List, java.util.UUID, int)
	 */
	@Override
	public void updateChips(List<UUID> receivers, UUID player, int nb) {
		for (UUID uuid : receivers) {
			SocketClientHandler handler = connectedClients.get(uuid.toString());
			if (handler != null)
				handler.sendMessage(new UpdateChipsChargeMessage(player, nb));
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#updateChips(java.util.List, java.util.UUID, java.util.UUID, int)
	 */
	@Override
	public void updateChips(List<UUID> receivers, UUID win, UUID lose, int nb) {
		for (UUID uuid : receivers) {
			SocketClientHandler handler = connectedClients.get(uuid.toString());
			if (handler != null)
				handler.sendMessage(new UpdateChipsDechargeMessage(win, lose, nb));
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#hasWon(java.util.List, java.util.UUID)
	 */
	@Override
	public void hasWon(List<UUID> receivers, UUID winner) {
		SocketClientHandler  handler;
		for (UUID user : receivers) {
			handler = connectedClients.get(user.toString());
			if (handler != null)
				handler.sendMessage(new HasWonMessage(winner));
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#startTurn(java.util.List, java.util.UUID, boolean)
	 */
	@Override
	public void startTurn(List<UUID> receivers, UUID player, boolean isLastLaunch) {
		for (UUID uuid : receivers) {
			SocketClientHandler handler = connectedClients.get(uuid.toString());
			if (handler != null)
				handler.sendMessage(new IsTurnMessage(player));
		}

		SocketClientHandler handler = connectedClients.get(player.toString());
		if (handler != null)
			handler.sendMessage(new StartTurnMessage());

	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#sendProfileUpdate(java.util.List, java.util.UUID, data.Profile)
	 */
	@Override
	public void sendProfileUpdate(List<UUID> receivers, UUID userUpdated, Profile data) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#sendProfile(java.util.UUID, data.Profile)
	 */
	@Override
	public void sendProfile(UUID receiver, Profile data) {
		SocketClientHandler handler = connectedClients.get(receiver.toString());
		if (handler != null)
			handler.sendMessage(new SendProfileMessage(receiver, data));
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#kick(java.util.List, java.lang.String)
	 */
	@Override
	public void kick(List<UUID> receivers, String msg) {
		for(UUID receiver : receivers) {
			SocketClientHandler handler = connectedClients.get(receiver.toString());
			if (handler != null) {
				handler.sendMessage(new KickedMessage(msg));
			}
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#askStopGameEveryUser(java.util.List)
	 */
	@Override
	public void askStopGameEveryUser(List<UUID> receivers) {
		for(UUID receiver : receivers) {
			SocketClientHandler handler = connectedClients.get(receiver.toString());
			if (handler != null) {
				handler.sendMessage(new AskStopGameMessage());
			}
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#stopGameAccepted(java.util.List)
	 */
	@Override
	public void stopGameAccepted(List<UUID> receivers) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#raiseException(java.util.UUID, java.lang.String)
	 */
	@Override
	public void raiseException(UUID user, String msg) {
		SocketClientHandler handler = connectedClients.get(user.toString());
		if (handler != null) {
			handler.sendMessage(new RaiseExceptionMessage(user, msg));
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#raiseException(java.util.List, java.lang.String)
	 */
	@Override
	public void raiseException(List<UUID> receivers, String msg) {
		for(UUID receiver : receivers) {
			raiseException(receiver, msg);
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#newPlayerOnTable(java.util.List, data.Profile, data.GameTable)
	 */
	@Override
	public void newPlayerOnTable(List<UUID> receivers, Profile user, GameTable tableInfo) {
        for(UUID receiver : receivers) {
            SocketClientHandler handler = connectedClients.get(receiver.toString());
            if (handler != null) {
                handler.sendMessage(new NewPlayerOnTableMessage(user));
            }
        }
        SocketClientHandler handler = connectedClients.get(user.getUUID().toString());
        handler.sendMessage(new UpdateTableInfoMessage(tableInfo));
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#newSpectatorOnTable(java.util.List, data.Profile, data.GameTable)
	 */
	@Override
	public void newSpectatorOnTable(List<UUID> receivers, Profile user, GameTable tableInfo) {
	    for(UUID receiver : receivers) {
            SocketClientHandler handler = connectedClients.get(receiver.toString());
            if (handler != null) {
                handler.sendMessage(new NewSpectatorOnTableMessage(user));
            }
        }
	    SocketClientHandler handler = connectedClients.get(user.getUUID().toString());
        handler.sendMessage(new UpdateTableInfoMessage(tableInfo));
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#hasAccepted(java.util.UUID, java.util.List)
	 */
	@Override
	public void hasAccepted(UUID user, List<UUID> receivers) {
		for(UUID receiver : receivers) {
			SocketClientHandler handler = connectedClients.get(receiver.toString());
			if (handler != null) {
				// Il faut pouvoir spï¿½cifier ï¿½ qui on envoie ?
				handler.sendMessage(new HasAcceptedMessage(user));
			}
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#hasRefused(java.util.UUID, java.util.List)
	 */
	@Override
	public void hasRefused(UUID user, List<UUID> receivers) {
		for(UUID receiver : receivers) {
			SocketClientHandler handler = connectedClients.get(receiver.toString());
			if (handler != null) {
				// Il faut pouvoir spï¿½cifier ï¿½ qui on envoie ?
				handler.sendMessage(new HasRefusedMessage(user));
			}
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#newUser(java.util.List, data.Profile)
	 */
	@Override
	public void newUser(List<UUID> receivers, Profile user) {
		for (UUID uuid : receivers) {
			SocketClientHandler client = connectedClients.get(uuid.toString());
			if(client != null)
				client.sendMessage(new NewUserMessage(user));
		}
	}

	/*
	 *
	 * Getters & setters
	 *
	 */

	/**
	 * Returns the server data engine
	 * @return dataEngine
	 */
	public InterfaceSingleThreadData getDataEngine() {
		return dataEngine;
	}

	/**
	 * Sets server data engine attribute
	 * @param dataEngine
	 */
	public void setDataEngine(InterfaceSingleThreadData dataEngine) {
		this.dataEngine = dataEngine;
	}

	/**
	 * Returns true if server is closed
	 * @return
	 */
	public synchronized boolean isStopped() {
		return isStopped;
	}

	/**
	 * Closes the server
	 */
	public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }

    }

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#sendTablesUsers(java.util.List, java.util.List, data.Profile)
	 */
	@Override
	public void sendTablesUsers(List<User> userList, List<GameTable> tableList, Profile user) {
			SocketClientHandler client = connectedClients.get(user.getUUID().toString());
			if(client != null)
				client.sendMessage(new TablesUsersListMessage(userList, tableList));
	}


	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#addNewTable(java.util.UUID, java.util.List, data.GameTable)
	 */
	@Override
	public void addNewTable(UUID receiver, List<UUID> receivers, GameTable tableinfo) {
		//la table du receiver unique doit etre complete, mais pas celle des receivers multiples, on peux la mettre en lightweight.
		SocketClientHandler client = connectedClients.get(receiver.toString());
		if(client != null)
			client.sendMessage(new SendTableInfoMessage(tableinfo));

		for (UUID uuid : receivers) {
			client = connectedClients.get(uuid.toString());
			if(client != null)
				client.sendMessage(new AddNewTableMessage(tableinfo));
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#showTimer(java.util.List)
	 */
	@Override
	public void showTimer(List<UUID> receivers) {
		for (UUID uuid : receivers) {
			SocketClientHandler client = connectedClients.get(uuid.toString());
			if(client != null)
				client.sendMessage(new ShowTimerMessage());
		}
	}

    /* (non-Javadoc)
     * @see network.server.ComServerInterface#refreshUserList(java.util.UUID, java.util.List)
     */
    @Override
    public void refreshUserList(UUID user, List<User> userList) {
    	SocketClientHandler handler = connectedClients.get(user.toString());
		if (handler != null)
			handler.sendMessage(new refreshUserListMessage(userList));

    }

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#playerQuitGame(java.util.List, java.util.UUID)
	 */
	@Override
	public void playerQuitGame(List<UUID> receivers, UUID user) {
		for (UUID uuid : receivers) {
			SocketClientHandler client = connectedClients.get(uuid.toString());
			if(client != null)
				client.sendMessage(new PlayerQuitGameMessage(user));
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#stopGame(java.util.List, boolean)
	 */
	@Override
	public void stopGame(List<UUID> receivers, boolean answer) {
		for (UUID uuid : receivers) {
			SocketClientHandler client = connectedClients.get(uuid.toString());
			if(client != null)
				client.sendMessage(new StopGameMessage(answer));
		}
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#hasLost(java.util.List, java.util.UUID)
	 */
	@Override
    public void hasLost(List<UUID> receivers, UUID loser) {
        SocketClientHandler  handler;
        for (UUID user : receivers) {
            handler = connectedClients.get(user.toString());
            if (handler != null)
                handler.sendMessage(new HasLostMessage(loser));
        }
    }

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#changeState(java.util.List, data.State)
	 */
	@Override
	public void changeState(List<UUID> receivers, State state) {
		SocketClientHandler  handler;
        for (UUID user : receivers) {
            handler = connectedClients.get(user.toString());
            if (handler != null)
                handler.sendMessage(new ChangeStateMessage(state));
        }
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#changeTurnState(java.util.List, data.TurnState)
	 */
	@Override
	public void changeTurnState(List<UUID> receivers, TurnState turnState) {
		SocketClientHandler  handler;
        for (UUID user : receivers) {
            handler = connectedClients.get(user.toString());
            if (handler != null)
            {
                handler.sendMessage(new ChangeTurnStateMessage(turnState));
            }
        }
	}

	/* (non-Javadoc)
	 * @see network.server.ComServerInterface#setCreator(java.util.List, java.util.UUID)
	 */
	@Override
	public void setCreator(List<UUID> receivers, UUID creator) {
		SocketClientHandler  handler;
        for (UUID user : receivers) {
            handler = connectedClients.get(user.toString());
            if (handler != null)
            	handler.sendMessage(new SetCreatorMessage(creator));
        }
	}

	@Override
	public void exAequoCase(List<UUID> receivers, List<User> users, boolean win) {
		SocketClientHandler  handler;
		for (UUID user : receivers) {
            handler = connectedClients.get(user.toString());
            if (handler != null)
            	handler.sendMessage(new ExAequoMessage(users, win));
        }
	}

}
