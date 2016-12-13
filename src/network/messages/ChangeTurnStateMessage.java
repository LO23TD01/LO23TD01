package network.messages;

import data.TurnState;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class ChangeTurnStateMessage implements IMessage {

	private static final long serialVersionUID = 5206001651371255612L;
	
	private TurnState turnState;

	public ChangeTurnStateMessage(TurnState turnState) {
		this.turnState = turnState;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.changeTurnState(turnState);
	}

}
