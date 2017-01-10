package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when a user accepts a player into the game
 * @author lenovo
 *
 */
public class HasAcceptedMessage implements IMessage {

	private static final long serialVersionUID = 6986290106710598367L;

	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the user accpeting
	 */
	public HasAcceptedMessage(UUID user) {
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
		dataEngine.hasAcceptedReplay(new User(new Profile(user)));
	}

}
