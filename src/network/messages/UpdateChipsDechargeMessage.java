package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class UpdateChipsDechargeMessage implements IMessage{

	private static final long serialVersionUID = 7524145696497041209L;

	private UUID winner;
	private UUID loser;
	private int nb;

	public UpdateChipsDechargeMessage(UUID winner, UUID loser, int nb) {
		this.winner = winner;
		this.loser = loser;
		this.nb = nb;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.updateChips(new User(new Profile(winner)), new User(new Profile(loser)), nb);
	}

}
