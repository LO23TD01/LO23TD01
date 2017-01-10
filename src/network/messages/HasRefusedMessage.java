package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent if a player has refused another
 * @author lenovo
 *
 */
public class HasRefusedMessage implements IMessage {

	private static final long serialVersionUID = -5383642164826933945L;

	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the player being refused
	 */
	public HasRefusedMessage(UUID user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.hasRefusedReplay(new User(new Profile(user)));
	}

}
