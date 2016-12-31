package network.messages;

import data.State;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class ChangeStateMessage implements IMessage {

	private static final long serialVersionUID = -656713210432341649L;
	
	private State state;

	public ChangeStateMessage(State state) {
		this.state = state;
	}

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.changeState(state);
	}

}
