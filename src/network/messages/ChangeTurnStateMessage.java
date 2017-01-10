package network.messages;

import java.util.Date;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;

import data.TurnState;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when the turn changes state
 * @author lenovo
 *
 */
public class ChangeTurnStateMessage implements IMessage {

	private static final long serialVersionUID = 5206001651371255612L;

	private String turnStateStr;

	/**
	 * Constructor
	 * @param turnState State to which the turn changes
	 */
	public ChangeTurnStateMessage(TurnState turnState) {
		TurnState t = TurnState.valueOf(turnState.toString());
		this.turnStateStr = FxGson.create().toJson(turnState);
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
		dataEngine.changeTurnState(FxGson.create().fromJson(turnStateStr, TurnState.class));
	}
}
