package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent to notify it is a certain player's turn
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
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.isTurn(new User(new Profile(player)));
	}

}
