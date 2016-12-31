package network.messages;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class StopGameMessage implements IMessage {

	private static final long serialVersionUID = -4869219421925002125L;

	private boolean answer;
	
	public StopGameMessage(boolean answer) {
		this.answer = answer;
	}

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.stopGame(answer);
	}

}
