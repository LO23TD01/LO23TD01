package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent to update the number of chips during the charge
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
	 * @param nb Number of chips
	 */
	public UpdateChipsChargeMessage(UUID player, int nb) {
		this.player = player;
		this.nb = nb;
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
		dataEngine.updateChips(new User(new Profile(player)), nb);
	}

}
