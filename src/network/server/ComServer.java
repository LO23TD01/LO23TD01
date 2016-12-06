package network.server;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import data.ChatMessage;
import data.GameTable;
import data.Profile;
import data.server.ServerDataEngine;
import network.messages.NewUserMessage;
import network.messages.HasSelectedMessage;
import network.messages.IsTurnMessage;
import network.messages.HasThrownMessage;
import network.messages.AddNewTableMessage;
import network.messages.HasWonMessage;
import network.messages.AskStopGameMessage;
import network.messages.HasAcceptedMessage;
import network.messages.HasRefusedMessage;
import network.messages.KickedMessage;
import network.messages.LogoutUserRequestMessage;
import network.messages.NetworkChatMessage;
import network.messages.PlayerQuitGameMessage;
import network.messages.SendProfileMessage;
import network.messages.SendTableInfoMessage;
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

public class ComServer implements Runnable, ComServerInterface {

	private int				serverPort;
	private ServerSocket	serverSocket;
	private boolean			isStopped    = false;
	private 			HashMap<String, SocketClientHandler> connectedClients = new HashMap<String, SocketClientHandler>();
	private ServerDataEngine dataEngine;
	
	/*
	 * 
	 * Constructor
	 * 
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
	
	public void replaceWithUUID (String adresse, UUID uuid){
		connectedClients.put(uuid.toString(), connectedClients.get(adresse));
		connectedClients.remove(adresse);
	}
	
	
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
	            
	            System.out.println("Nouveau client connect�");
	            
	        } catch (IOException e) {
	            if(isStopped()) {
	                System.out.println("Server Stopped.") ;
	                return;
	            }
	            e.printStackTrace();
	        }
	        
	        	SocketClientHandler client = new SocketClientHandler(clientSocket, this);
	        	new Thread(client).start();
	            connectedClients.put(clientSocket.getInetAddress().toString(), client);
	    }
	}
	
	/*
	 * 
	 * Overridden methods
	 * 	(ComServerInterface)
	 */
	
	@Override
	public void sendResult(List<UUID> receivers, int r1, int r2, int r3) {
		SocketClientHandler handler;
		for (UUID receiver : receivers) {
			handler = connectedClients.get(receiver.toString());
			if (handler != null) {
				handler.sendMessage(new HasThrownMessage(receiver, r1, r2, r3));
			}
		}
		
	}

    @Override                                                                                                                                  
    public void sendMessage(List<UUID> receivers, ChatMessage chatMsg) {                                                                      
        for (UUID receiver : receivers) {                                                                                                      
            SocketClientHandler handler = connectedClients.get(receiver);                                                                      
            if (handler != null) {                                                                                                             
                handler.sendMessage(new NetworkChatMessage(chatMsg));                                                                          
            }                                                                                                                                  
        }                                                                                                                                      
    }  

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

	@Override
	public void updateChips(List<UUID> receivers, UUID player, int nb) {
		for (UUID uuid : receivers) {
			SocketClientHandler handler = connectedClients.get(uuid.toString());
			if (handler != null)
				handler.sendMessage(new UpdateChipsChargeMessage(player, nb));
		}
	}

	@Override
	public void updateChips(List<UUID> receivers, UUID win, UUID lose, int nb) {
		for (UUID uuid : receivers) {
			SocketClientHandler handler = connectedClients.get(uuid.toString());
			if (handler != null)
				handler.sendMessage(new UpdateChipsDechargeMessage(win, lose, nb));
		}
	}

	@Override
	public void hasWon(List<UUID> receivers, UUID winner) {
		SocketClientHandler  handler;
		for (UUID user : receivers) {
			handler = connectedClients.get(user.toString());
			if (handler != null)
				handler.sendMessage(new HasWonMessage(winner));
		}
	}

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

	@Override
	public void sendProfileUpdate(List<UUID> receivers, UUID userUpdated, Profile data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendProfile(UUID receiver, Profile data) {
		SocketClientHandler handler = connectedClients.get(receiver.toString());
		if (handler != null)
			handler.sendMessage(new SendProfileMessage(receiver, data));
	}

	@Override
	public void kick(List<UUID> receivers, String msg) {
		for(UUID receiver : receivers) {		
			SocketClientHandler handler = connectedClients.get(receiver.toString());
			if (handler != null) {
				handler.sendMessage(new KickedMessage(msg));
			}
		}
	}

	@Override
	public void askStopGameEveryUser(List<UUID> receivers) {
		for(UUID receiver : receivers) {		
			SocketClientHandler handler = connectedClients.get(receiver.toString());
			if (handler != null) {
				handler.sendMessage(new AskStopGameMessage());
			}
		}
	}

	@Override
	public void stopGameAccepted(List<UUID> receivers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void raiseException(UUID user, String msg) {
		// TODO Auto-generated method stub
		
	}

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

	@Override
	public void hasAccepted(UUID user, List<UUID> receivers) {
		for(UUID receiver : receivers) {
			SocketClientHandler handler = connectedClients.get(receiver.toString());
			if (handler != null) {
				// Il faut pouvoir sp�cifier � qui on envoie ?
				handler.sendMessage(new HasAcceptedMessage(user));
			}
		}
	}

	@Override
	public void hasRefused(UUID user, List<UUID> receivers) {
		for(UUID receiver : receivers) {
			SocketClientHandler handler = connectedClients.get(receiver.toString());
			if (handler != null) {
				// Il faut pouvoir sp�cifier � qui on envoie ?
				handler.sendMessage(new HasRefusedMessage(user));
			}
		}
	}

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
	
	public ServerDataEngine getDataEngine() {
		return dataEngine;
	}

	public void setDataEngine(ServerDataEngine dataEngine) {
		this.dataEngine = dataEngine;
	}
	
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
	@Override
	public void sendTablesUsers(List<User> userList, List<GameTable> tableList, Profile user) {
			SocketClientHandler client = connectedClients.get(user.getUUID().toString());
			if(client != null)
				client.sendMessage(new TablesUsersListMessage(userList, tableList));
	}


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

	@Override
	public void showTimer(List<UUID> receivers) {
		for (UUID uuid : receivers) {
			SocketClientHandler client = connectedClients.get(uuid.toString());
			if(client != null)
				client.sendMessage(new ShowTimerMessage());
		}
	}

    @Override
    public void refreshUserList(UUID user, List<User> userList) {
    	SocketClientHandler handler = connectedClients.get(user.toString());
		if (handler != null)
			handler.sendMessage(new refreshUserListMessage(userList));
        
    }

	@Override
	public void playerQuitGame(List<UUID> receivers, UUID user) {
		for (UUID uuid : receivers) {
			SocketClientHandler client = connectedClients.get(uuid.toString());
			if(client != null)
				client.sendMessage(new PlayerQuitGameMessage(user));
		}
	}

	@Override
	public void stopGame(List<UUID> receivers, boolean answer) {
		for (UUID uuid : receivers) {
			SocketClientHandler client = connectedClients.get(uuid.toString());
			if(client != null)
				client.sendMessage(new StopGameMessage(answer));
		}
	}

	@Override
    public void hasLost(List<UUID> receivers, UUID loser) {
        SocketClientHandler  handler;
        for (UUID user : receivers) {
            handler = connectedClients.get(user.toString());
            if (handler != null)
                handler.sendMessage(new HasLostMessage(loser));
        }
    }
}
