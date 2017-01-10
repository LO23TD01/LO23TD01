package network.messages;

import java.util.UUID;
import data.User;
import data.Profile;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when retrieving a profile
 * @author lenovo
 *
 */
public class GetProfileMessage implements IMessage {

	private static final long serialVersionUID = 3379374689137731613L;
	private UUID user;
	private UUID sender;

	/**
	 * Constructor
	 * @param user UUID of the desired profile
	 * @param sender UUID of the one sending the profile
	 */
	public GetProfileMessage(UUID user, UUID sender) {
		this.user = user;
		this.sender = sender;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		Profile profile = dataEngine.getProfile(new User(new Profile(user)));
		//dataEngine.getComServer().sendProfile(sender, profile);
	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {}

}
