package data.server;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import data.ChatMessage;
import data.GameTable;
import data.Parameters;
import data.Profile;
import data.User;

public class InterfaceSingleThreadData implements InterfaceDataNetwork {

	private ExecutorService es;
	private ServerDataEngine server;

	//le seul et unique constructeur appelée UNE SEULE FOIS (pour avoir qu'un seul thread hein;)
	public InterfaceSingleThreadData(ServerDataEngine server)
	{
		this.es =  Executors.newSingleThreadExecutor();
		this.server = server;
	}

	public boolean exist(User user)
	{
		return this.server.exist(user);
	}



	@Override
	public Profile getProfile(User user) {
		return server.getProfile(user);
	}

	@Override
	public void updateUserProfile(UUID uuid, Profile profile) {

		es.execute(new Runnable() {
		    public void run() {
		    	server.updateUserProfile(uuid, profile);
		    }
		});
	}

	@Override
	public void sendMessage(ChatMessage message) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.sendMessage(message);
		    }
		});
	}

	@Override
	public void dropTable(GameTable table) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.dropTable(table);
		    }
		});
	}

	@Override
	public void quit(User user, GameTable table) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.quit(user, table);
		    }
		});
	}

	@Override
	public void askJoinTable(User user, GameTable table, boolean isPlayer) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.askJoinTable(user, table, isPlayer);
		    }
		});
	}

	@Override
	public void launchGame(User user) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.launchGame(user);
		    }
		});
	}

	@Override
	public void createNewTable(User user, String name, Parameters params) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.createNewTable(user, name, params);
		    }
		});
	}

	@Override
	public void disconnectUser(User user) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.disconnectUser(user);
		    }
		});
	}

	@Override
	public void hasThrown(UUID uuid, boolean d1, boolean d2, boolean d3) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.hasThrown(uuid, d1, d2, d3);
		    }
		});
	}

	@Override
	public void hasSelected(UUID uuid, boolean d1, boolean d2, boolean d3) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.hasSelected(uuid, d1, d2, d3);
		    }
		});
	}

	@Override
	public void connectUser(Profile profile) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.connectUser(profile);
		    }
		});
	}

	@Override
	public void hasRefusedReplay(UUID uuid) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.hasRefusedReplay(uuid);
		    }
		});
	}

	@Override
	public void hasAcceptedReplay(UUID uuid) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.hasAcceptedReplay(uuid);
		    }
		});
	}

	@Override
	public void askRefreshUsersList(User user) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.askRefreshUsersList(user);
		    }
		});
	}

	@Override
	public void askQuitTable(UUID tableID, UUID user) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.askQuitTable(tableID, user);
		    }
		});
	}

	@Override
	public void answerStopGame(UUID tableID, boolean answer, UUID user) {
		es.execute(new Runnable() {
		    public void run() {
		    	server.answerStopGame(tableID, answer, user);
		    }
		});
	}

}
