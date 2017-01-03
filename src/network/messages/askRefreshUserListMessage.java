package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.Profile;
import data.server.ServerDataEngine;
import data.User;

/**
 * Message to be sent when a user asks for a refresh of his user list
 * @author lenovo
 *
 */
public class askRefreshUserListMessage implements IMessage{

	private static final long serialVersionUID = -6157527910635620253L;
	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the user asking for the refresh
	 */
	public askRefreshUserListMessage(UUID user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.askRefreshUsersList(new User(new Profile(this.user)));
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {}

}
