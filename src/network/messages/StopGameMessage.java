package network.messages;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class StopGameMessage implements IMessage {

	private static final long serialVersionUID = -4869219421925002125L;

	private boolean answer;
	
	public StopGameMessage(boolean answer) {
		this.answer = answer;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.stopGame(answer);
	}

}
