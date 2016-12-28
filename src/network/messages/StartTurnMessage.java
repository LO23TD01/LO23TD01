package network.messages;

import data.client.ClientDataEngine;
import data.server.InterfaceSingleThreadData;

public class StartTurnMessage implements IMessage {

	private static final long serialVersionUID = -4969602909055242890L;

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.startTurn();
	}
	
}
