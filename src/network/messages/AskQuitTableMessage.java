package network.messages;

import java.util.UUID;

import data.GameTable;
import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a player wants to quit a table
 * @author lenovo
 */
public class AskQuitTableMessage implements IMessage {

	private static final long serialVersionUID = 2143481984334543591L;
    private UUID tableId;
    private UUID user;

    /**
     * Constructor
     * @param tableId UUID of the table
     * @param userID UUID of the user asking to quit
     */
    public AskQuitTableMessage(UUID tableId, UUID userID) {
    	this.tableId = tableId;
    	this.user = userID;
    }


	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {
		// It is not the correct method. We need a specific askQuitTable when the user is admin
		// dataEngine.quit(new User(new Profile(user)), new GameTable(tableId));
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {}

}
