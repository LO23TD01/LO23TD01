package network.server;

import java.util.Arrays;
import java.util.List;

import org.hildan.fxgson.FxGson;

import data.GameTable;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;
import network.messages.IMessage;

public class RefreshTableListMessage implements IMessage {

	private static final long serialVersionUID = -3205345062306064086L;
	private String tableList;

	public RefreshTableListMessage(List<GameTable> tableList) {
		
		GameTable[] tables = tableList.toArray(new GameTable[0]);
		this.tableList = FxGson.create().toJson(tables);
	}

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		GameTable[] tables = FxGson.create().fromJson(tableList, GameTable[].class);
		dataEngine.refreshTableList(Arrays.asList(tables));
	}

}
