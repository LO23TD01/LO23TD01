package network.messages;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a stating a new turn
 * @author lenovo
 *
 */
public class StartTurnMessage implements IMessage {

	private static final long serialVersionUID = -4969602909055242890L;

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
		dataEngine.startTurn();
	}

}
