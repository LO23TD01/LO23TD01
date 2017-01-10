package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when a player has selected die
 * @author lenovo
 *
 */
public class HasSelectedMessage implements IMessage {

	private static final long serialVersionUID = -5674362211027759000L;

	private UUID player;
	private boolean dice1;
	private boolean dice2;
	private boolean dice3;

	/**
	 * Constructor
	 * @param player UUID of the selecting player
	 * @param dice1 True if the first dice has been selected, false otherwise
	 * @param dice2 True if the second dice has been selected, false otherwise
	 * @param dice3 True if the second dice has been selected, false otherwise
	 */
	public HasSelectedMessage(UUID player, boolean dice1, boolean dice2, boolean dice3) {
		this.player = player;
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.dice3 = dice3;
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
		dataEngine.hasSelected(new User(new Profile(player)), dice1, dice2, dice3);
	}

}
