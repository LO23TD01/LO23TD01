package network.messages;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

import org.hildan.fxgson.FxGson;

import data.GameTable;

/**
 * Message to be sent when update the info of a table
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
     * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
     */
    @Override
    public void process(InterfaceSingleThreadData dataEngine) {

    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
     */
    @Override
    public void process(InterfaceSingleThreadDataClient dataEngine) {
    	//UpdateTableInfo = sendTableInfo
        dataEngine.sendTableInfo((FxGson.create().fromJson(tableInfo, GameTable.class)));
    }

}
