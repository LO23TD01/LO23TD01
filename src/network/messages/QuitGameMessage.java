package network.messages;

import java.util.UUID;

import org.hildan.fxgson.FxGson;

import data.GameTable;
import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class QuitGameMessage implements IMessage {

	private static final long serialVersionUID = -506628116565819592L;
	private UUID user;
	private String table;
	
	public QuitGameMessage(UUID user, GameTable table) {
		this.user = user;
		this.table = FxGson.create().toJson(table);
	}
	
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.quit(new User(new Profile(user)), FxGson.create().fromJson(table, GameTable.class));
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

}
