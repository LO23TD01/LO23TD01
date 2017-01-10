package network.messages;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when stopping a game
 * @author lenovo
 *
 */
public class StopGameMessage implements IMessage {

	private static final long serialVersionUID = -4869219421925002125L;

	private boolean answer;

	/**
	 * Constructor
	 * @param answer True if the game is stopped, false otherwise
	 */
	public StopGameMessage(boolean answer) {
		this.answer = answer;
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
		dataEngine.stopGame(answer);
	}

}
