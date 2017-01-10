package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent to update the number of chips during decharge
 * @author lenovo
 *
 */
public class UpdateChipsDechargeMessage implements IMessage{

	private static final long serialVersionUID = 7524145696497041209L;

	private UUID winner;
	private UUID loser;
	private int nb;

	/**
	 * Constructor
	 * @param winner UUID of the winner
	 * @param loser UUID of the loser
	 * @param nb Number of chips
	 */
	public UpdateChipsDechargeMessage(UUID winner, UUID loser, int nb) {
		this.winner = winner;
		this.loser = loser;
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
		dataEngine.updateChips(new User(new Profile(winner)), new User(new Profile(loser)), nb);
	}

}
