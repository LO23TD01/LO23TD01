package network.messages;

import org.hildan.fxgson.FxGson;

import data.GameTable;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent to display table infos
 * @author lenovo
 *
 */
public class SendTableInfoMessage implements IMessage {

	private static final long serialVersionUID = 8650617390791474761L;
	private String tableInfo;


	/**
	 * Constructor
	 * @param tableInfo Info on the table
	 */
	public SendTableInfoMessage(GameTable tableInfo) {
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
		dataEngine.sendTableInfo(FxGson.create().fromJson(tableInfo, GameTable.class));
	}

}
