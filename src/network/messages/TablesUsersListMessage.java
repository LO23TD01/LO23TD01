package network.messages;

import java.util.Arrays;
import java.util.List;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.GameTable;
import data.server.ServerDataEngine;
import data.User;

/**
 * Message to be sent to gather the list of users on a given table
 * @author lenovo
 *
 */
public class TablesUsersListMessage implements IMessage {

	private static final long serialVersionUID = 7188120721853249541L;

	String userList;
	String tableList;

	/**
	 * Constructor
	 * @param userList List if all the users
	 * @param tableList List of all the tables
	 */
	public TablesUsersListMessage(List<User> userList, List<GameTable> tableList) {
		User[] users = userList.toArray(new User[0]);
		GameTable[] tables = tableList.toArray(new GameTable[0]);

		this.userList = FxGson.create().toJson(users);
		this.tableList = FxGson.create().toJson(tables);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {
		User[] users = FxGson.create().fromJson(userList, User[].class);
		dataEngine.updateUsersList(Arrays.asList(users));

		GameTable[] tables = FxGson.create().fromJson(tableList, GameTable[].class);
		dataEngine.updateTablesList(Arrays.asList(tables));
	}

}
