package network.messages;

import java.util.UUID;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;
import data.Profile;
import data.User;
import data.GameTable;

/**
 * Message to be sent when asking to join a table
 * @author lenovo
 *
 */
public class AskJoinTableMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private UUID userID;
    private UUID tableID;
    private boolean asPlayer;

    /**
     * Constructor
     * @param userID UUID of the user trying to join
     * @param tableID UUID Of the table
     * @param asPlayer True if it is as a player, false otherwise
     */
    public AskJoinTableMessage(UUID userID, UUID tableID, boolean asPlayer) {
        this.userID = userID;
        this.tableID = tableID;
        this.asPlayer = asPlayer;
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
     */
    @Override
    public void process(InterfaceSingleThreadData dataEngine) {
        dataEngine.askJoinTable(new User(new Profile(this.userID)), new GameTable(this.tableID), this.asPlayer);
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
     */
    @Override
    public void process(InterfaceSingleThreadDataClient dataEngine) {}

}
