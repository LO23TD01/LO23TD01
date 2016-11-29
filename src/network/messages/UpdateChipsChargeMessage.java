package network.messages;

import java.util.UUID;

import data.ClientDataEngine;
import data.server.ServerDataEngine;

public class UpdateChipsChargeMessage implements IMessage {

	private static final long serialVersionUID = -8893873668079976483L;

	private UUID player;
	private int nb;
	
	
	
	public UpdateChipsChargeMessage(UUID player, int nb) {
		this.player = player;
		this.nb = nb;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		//Quand l'interface Data client sera implémenté appeler
		//dataEngine.updateChips(player, nb);
	}

}
