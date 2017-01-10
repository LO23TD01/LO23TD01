package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when a players quits the game
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
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.playerQuitGame(new User(new Profile(user)));
	}

}
