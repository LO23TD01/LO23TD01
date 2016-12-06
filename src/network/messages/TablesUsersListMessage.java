package network.messages;

import java.util.Arrays;
import java.util.List;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.GameTable;
import data.server.ServerDataEngine;
import data.User;

public class TablesUsersListMessage implements IMessage {

	private static final long serialVersionUID = 7188120721853249541L;
	
	String userList;
	String tableList;
	
	public TablesUsersListMessage(List<User> userList, List<GameTable> tableList) {
		User[] users = userList.toArray(new User[1]);
		GameTable[] tables = tableList.toArray(new GameTable[1]);
		
		this.userList = FxGson.create().toJson(users);
		this.tableList = FxGson.create().toJson(tables);
	}

	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		User[] users = FxGson.create().fromJson(userList, User[].class);
		System.out.println(dataEngine.toString());
		dataEngine.updateUsersList(Arrays.asList(users));
		
		GameTable[] tables = FxGson.create().fromJson(tableList, GameTable[].class);
		dataEngine.updateTablesList(Arrays.asList(tables));
	}

}
