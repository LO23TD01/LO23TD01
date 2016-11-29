package network.messages;

import java.util.UUID;
import data.ClientDataEngine;
import data.server.ServerDataEngine;
import data.Profile;
import data.User;
import data.GameTable;

public class AskJoinTableMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private UUID userID;
    private UUID tableID;
    private boolean asPlayer;

    public AskJoinTableMessage(UUID userID, UUID tableID, boolean asPlayer) {
        this.userID = userID;
        this.tableID = tableID;
        this.asPlayer = asPlayer;
    }

    @Override
    public void process(ServerDataEngine dataEngine) {
        // TODO Auto-generated method stub
        dataEngine.askJoinTable(new User(new Profile(this.userID)), new GameTable(this.tableID), this.asPlayer);
    }

    @Override
    public void process(ClientDataEngine dataEngine) {
        // TODO Auto-generated method stub

    }

}
