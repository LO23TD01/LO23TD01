package network.messages;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;

import data.TurnState;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class ChangeTurnStateMessage implements IMessage {

	private static final long serialVersionUID = 5206001651371255612L;
	
	private String turnStateStr;

	public ChangeTurnStateMessage(TurnState turnState) {
		this.turnStateStr = FxGson.create().toJson(turnState);
	}

	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.changeTurnState(FxGson.create().fromJson(turnStateStr, TurnState.class));
	}
}
