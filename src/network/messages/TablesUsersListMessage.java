package network.messages;

import java.util.List;

import data.client.ClientDataEngine;
import data.GameTable;
import data.server.ServerDataEngine;
import data.User;

public class TablesUsersListMessage implements IMessage {

	private static final long serialVersionUID = 7188120721853249541L;
	
	List<User> userList;
	List<GameTable> tableList;
	
	public TablesUsersListMessage(List<User> userList, List<GameTable> tableList) {
		this.userList = userList;
		this.tableList = tableList;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		//Appeler dataEngine.updateUsersList(usersList);
		//Appeler dataEngine.updateTablesList(tableList);
		//Quand les interface de Data seront implementes
	}

}
