package network.messages;

import java.util.UUID;

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
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		//Quand l'interface Data client sera implémenté appeler
		//dataEngine.updateChips(winner, loser, nb);
	}

}
