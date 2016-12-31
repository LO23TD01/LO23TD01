package network.messages;

import org.hildan.fxgson.FxGson;

import data.GameTable;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class AddNewTableMessage implements IMessage {

	private static final long serialVersionUID = 210924804738395566L;
	private String tableInfo;
	
	public AddNewTableMessage(GameTable tableInfo) {
		this.tableInfo = FxGson.create().toJson(tableInfo);
	}

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.addNewTable(FxGson.create().fromJson(tableInfo, GameTable.class));
	}

}
