package network.messages;

import org.hildan.fxgson.FxGson;

import data.GameTable;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when sending a table's info
 * @author lenovo
 *
 */
public class SendTableInfoMessage implements IMessage {

	private static final long serialVersionUID = 8650617390791474761L;
	private String tableInfo;


	/**
	 * Constructor
	 * @param tableInfo Info of the table
	 */
	public SendTableInfoMessage(GameTable tableInfo) {
		this.tableInfo = FxGson.create().toJson(tableInfo);
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
		dataEngine.sendTableInfo(FxGson.create().fromJson(tableInfo, GameTable.class));
	}

}
