package network.messages;

import data.State;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a game changes state
 * @author lenovo
 *
 */
public class ChangeStateMessage implements IMessage {

	private static final long serialVersionUID = -656713210432341649L;

	private State state;

	/**
	 * Constructor
	 * @param state State to which the game changes
	 */
	public ChangeStateMessage(State state) {
		this.state = state;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.changeState(state);
	}

}
