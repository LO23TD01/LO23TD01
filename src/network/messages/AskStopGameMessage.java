package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a game is stopped
 * @author lenovo
 *
 */
public class AskStopGameMessage implements IMessage {

	private static final long serialVersionUID = 3413708401686105382L;

	/**
	 * Constructor
	 */
	public AskStopGameMessage() {
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
		dataEngine.askStopGame();
	}

}
