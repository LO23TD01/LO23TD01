package network.messages;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class ShowTimerMessage implements IMessage {

	private static final long serialVersionUID = 1074808091777055500L;

	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.showTimer();
	}

}
