package network.messages;

import data.TurnState;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when the turn changes state
 * @author lenovo
 *
 */
public class ChangeTurnStateMessage implements IMessage {

	private static final long serialVersionUID = 5206001651371255612L;

	private TurnState turnState;

	/**
	 * Constructor
	 * @param turnState State to which the state of the turn changes
	 */
	public ChangeTurnStateMessage(TurnState turnState) {
		this.turnState = turnState;
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
		dataEngine.changeTurnState(turnState);
	}

}
