package network.messages;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

import org.hildan.fxgson.FxGson;

import data.GameTable;

public class UpdateTableInfoMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private String tableInfo;

    public UpdateTableInfoMessage(GameTable tableInfo) {
        this.tableInfo = FxGson.create().toJson(tableInfo);
    }

    @Override
    public void process(ServerDataEngine dataEngine) {

    }

    @Override
    public void process(ClientDataEngine dataEngine) {
    	//UpdateTableInfo équivaut à sendTableInfo
        dataEngine.sendTableInfo((FxGson.create().fromJson(tableInfo, GameTable.class)));
    }

}
