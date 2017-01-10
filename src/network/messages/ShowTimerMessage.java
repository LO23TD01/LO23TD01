package network.messages;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent to show timer
 * @author lenovo
 *
 */
public class ShowTimerMessage implements IMessage {

	private static final long serialVersionUID = 1074808091777055500L;

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
		dataEngine.showTimer();
	}

}
