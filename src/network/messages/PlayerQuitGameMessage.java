package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a players quits a game
 * @author lenovo
 *
 */
public class PlayerQuitGameMessage implements IMessage {

	private static final long serialVersionUID = 1308575889279693465L;
	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the quitting player
	 */
	public PlayerQuitGameMessage(UUID user) {
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
		dataEngine.playerQuitGame(new User(new Profile(user)));
	}

}
