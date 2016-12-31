package network.messages;

import java.util.Date;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;

import data.TurnState;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class ChangeTurnStateMessage implements IMessage {

	private static final long serialVersionUID = 5206001651371255612L;
	
	private String turnStateStr;

	public ChangeTurnStateMessage(TurnState turnState) {
		TurnState t = TurnState.valueOf(turnState.toString());
		System.out.println(t+" | "+turnState);
		System.out.println(turnState.name());
		this.turnStateStr = FxGson.create().toJson(turnState);
		System.out.println(this.turnStateStr);
	}

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		System.out.println("ChangeTurnMessage : "+new Date().getTime());
		dataEngine.changeTurnState(FxGson.create().fromJson(turnStateStr, TurnState.class));
	}
}
