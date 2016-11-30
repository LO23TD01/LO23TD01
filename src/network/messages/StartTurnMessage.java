package network.messages;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class StartTurnMessage implements IMessage {

	private static final long serialVersionUID = -4969602909055242890L;

	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		//Quand l'interface Data client sera implémenté appeler
		//dataEngine.startTurn();
	}
	
}
