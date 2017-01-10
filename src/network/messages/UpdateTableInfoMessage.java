package network.messages;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

import org.hildan.fxgson.FxGson;

import data.GameTable;

/**
 * Message to be sent when updating the info of a table
 * @author lenovo
 *
 */
public class UpdateTableInfoMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private String tableInfo;

    /**
     * Constructor
     * @param tableInfo New info of the table
     */
    public UpdateTableInfoMessage(GameTable tableInfo) {
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
    	//UpdateTableInfo is equivalent to sendTableInfo
        dataEngine.sendTableInfo((FxGson.create().fromJson(tableInfo, GameTable.class)));
    }

}
