package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a player has won
 * @author lenovo
 *
 */
public class HasWonMessage implements IMessage{

	private static final long serialVersionUID = -5930391832092033148L;
	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the winner
	 */
	public HasWonMessage(UUID user) {
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
		dataEngine.hasWon(new User(new Profile(this.user)));
	}

}
