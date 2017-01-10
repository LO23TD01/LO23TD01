package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when a player has won a game
 * @author lenovo
 *
 */
public class HasWonMessage implements IMessage{

	private static final long serialVersionUID = -5930391832092033148L;
	private UUID user;

	/**
	 * Construcor
	 * @param user UUID of the winner
	 */
	public HasWonMessage(UUID user) {
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
		dataEngine.hasWon(new User(new Profile(this.user)));
	}

}
