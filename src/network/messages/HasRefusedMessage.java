package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a user is refused
 * @author lenovo
 *
 */
public class HasRefusedMessage implements IMessage {

	private static final long serialVersionUID = -5383642164826933945L;

	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the refused user
	 */
	public HasRefusedMessage(UUID user) {
		this.user = user;
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
		dataEngine.hasRefusedReplay(new User(new Profile(user)));
	}

}
