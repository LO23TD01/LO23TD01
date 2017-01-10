package network.messages;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

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
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.stopGame(answer);
	}

}
