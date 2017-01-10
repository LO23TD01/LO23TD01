package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent to notify it's a specific user's turn
 * @author lenovo
 *
 */
public class IsTurnMessage implements IMessage{

	private static final long serialVersionUID = -1022076184764195866L;

	private UUID player;

	/**
	 * Constructor
	 * @param player UUID of the player whose turn it is
	 */
	public IsTurnMessage(UUID player) {
		this.player = player;
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
		dataEngine.isTurn(new User(new Profile(player)));
	}

}
