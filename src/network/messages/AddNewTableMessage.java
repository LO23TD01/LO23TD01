package network.messages;

import org.hildan.fxgson.FxGson;

import data.GameTable;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when add a new table
 * @author lenovo
 *
 */
public class AddNewTableMessage implements IMessage {

	private static final long serialVersionUID = 210924804738395566L;
	private String tableInfo;

	/**
	 * Constructor
	 * @param tableInfo Info a the new table
	 */
	public AddNewTableMessage(GameTable tableInfo) {
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
		dataEngine.addNewTable(FxGson.create().fromJson(tableInfo, GameTable.class));
	}

}
