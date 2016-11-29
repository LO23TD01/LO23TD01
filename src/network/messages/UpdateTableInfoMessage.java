package network.messages;

import data.ClientDataEngine;
import data.server.ServerDataEngine;
import data.GameTable;

public class UpdateTableInfoMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private GameTable tableInfo;

    public UpdateTableInfoMessage(GameTable tableInfo) {
        this.tableInfo = tableInfo;
    }

    @Override
    public void process(ServerDataEngine dataEngine) {

    }

    @Override
    public void process(ClientDataEngine dataEngine) {
        //Décommenter quand clientDataEngine sera fini
        //dataEngine.updateTableInfo(this.tableID);
    }

}
