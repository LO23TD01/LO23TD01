package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a user selects die
 * @author lenovo
 *
 */
public class SelectDiceMessage implements IMessage{

	private static final long serialVersionUID = 5658668394311606107L;

	private UUID player;
	private boolean dice1;
	private boolean dice2;
	private boolean dice3;

	/**
	 * Constructor
	 * @param player UUID of the user selecting the die
	 * @param dice1 True if the dice is selected, false otherwise
	 * @param dice2 True if the dice is selected, false otherwise
	 * @param dice3 True if the dice is selected, false otherwise
	 */
	public SelectDiceMessage(UUID player, boolean dice1, boolean dice2, boolean dice3) {
		this.player = player;
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.dice3 = dice3;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.hasSelected(player, dice1, dice2, dice3);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {}

}
