package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.Profile;
import data.server.InterfaceSingleThreadData;
import data.User;

/**
 * Message to be sent when asking to refresh the list of users
 * @author lenovo
 *
 */
public class askRefreshUserListMessage implements IMessage{

	private static final long serialVersionUID = -6157527910635620253L;
	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the user
	 */
	public askRefreshUserListMessage(UUID user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		dataEngine.askRefreshUsersList(new User(new Profile(this.user)));
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {}

}
