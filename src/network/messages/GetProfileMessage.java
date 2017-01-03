package network.messages;

import java.util.UUID;
import data.User;
import data.Profile;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a user consults a profile
 * @author lenovo
 *
 */
public class GetProfileMessage implements IMessage {

	private static final long serialVersionUID = 3379374689137731613L;
	private UUID user;
	private UUID sender;

	/**
	 * Constructor
	 * @param user UUID of the user that consults the profile
	 * @param sender UUID of the user whose profile is consulted
	 */
	public GetProfileMessage(UUID user, UUID sender) {
		this.user = user;
		this.sender = sender;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {
		Profile profile = dataEngine.getProfile(new User(new Profile(user)));
		dataEngine.getComServer().sendProfile(sender, profile);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {}

}
