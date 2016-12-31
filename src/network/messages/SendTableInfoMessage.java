package network.messages;

import org.hildan.fxgson.FxGson;

import data.GameTable;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class SendTableInfoMessage implements IMessage {

	private static final long serialVersionUID = 8650617390791474761L;
	private String tableInfo;

	
	public SendTableInfoMessage(GameTable tableInfo) {
		this.tableInfo = FxGson.create().toJson(tableInfo);
	}

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.sendTableInfo(FxGson.create().fromJson(tableInfo, GameTable.class));
	}

}
