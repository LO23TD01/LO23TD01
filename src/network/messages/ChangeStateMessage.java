package network.messages;

import data.State;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class ChangeStateMessage implements IMessage {

	private static final long serialVersionUID = -656713210432341649L;

	private State state;

	public ChangeStateMessage(State state) {
		this.state = state;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.changeState(state);
	}

}
