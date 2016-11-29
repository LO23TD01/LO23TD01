package network.messages;

import java.util.UUID;
import data.ClientDataEngine;
import data.ServerDataEngine;

public class UpdateTableInfoMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private UUID tableID;

    public UpdateTableInfoMessage(UUID tableID) {
        this.tableID = tableID;
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
