package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class IsTurnMessage implements IMessage{

	private static final long serialVersionUID = -1022076184764195866L;

	private UUID player;
	
	public IsTurnMessage(UUID player) {
		this.player = player;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		//Quand l'interface Data client sera implémenté appeler
		//dataEngine.isTurn(player);
	}

}
