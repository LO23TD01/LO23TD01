package network.messages;

import org.hildan.fxgson.FxGson;

import data.GameTable;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class AddNewTableMessage implements IMessage {

	private static final long serialVersionUID = 210924804738395566L;
	private String tableInfo;
	
	public AddNewTableMessage(GameTable tableInfo) {
		this.tableInfo = FxGson.create().toJson(tableInfo);
	}

	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.addNewTable(FxGson.create().fromJson(tableInfo, GameTable.class));
	}

}
