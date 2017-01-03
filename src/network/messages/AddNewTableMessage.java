package network.messages;

import org.hildan.fxgson.FxGson;

import data.GameTable;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a new table is created
 * @author lenovo
 */
public class AddNewTableMessage implements IMessage {

	private static final long serialVersionUID = 210924804738395566L;
	private String tableInfo;

	/**
	 * Constructor
	 * @param tableInfo Info of the newly created table
	 */
	public AddNewTableMessage(GameTable tableInfo) {
		this.tableInfo = FxGson.create().toJson(tableInfo);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.addNewTable(FxGson.create().fromJson(tableInfo, GameTable.class));
	}

}
