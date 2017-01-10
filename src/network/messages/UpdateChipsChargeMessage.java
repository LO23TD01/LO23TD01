package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent to change the charge of a given player
 * @author lenovo
 *
 */
public class UpdateChipsChargeMessage implements IMessage {

	private static final long serialVersionUID = -8893873668079976483L;

	private UUID player;
	private int nb;


	/**
	 * Constructor
	 * @param player UUID of the player whose number of chips is updated
	 * @param nb Number of chips during charge
	 */
	public UpdateChipsChargeMessage(UUID player, int nb) {
		this.player = player;
		this.nb = nb;
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
		dataEngine.updateChips(new User(new Profile(player)), nb);
	}

}
