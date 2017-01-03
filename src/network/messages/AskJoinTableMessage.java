package network.messages;

import java.util.UUID;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;
import data.Profile;
import data.User;
import data.GameTable;

/**
 * Message to be sent when a user asks to join a table
 * @author lenovo
 */
public class AskJoinTableMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private UUID userID;
    private UUID tableID;
    private boolean asPlayer;

    /**
     * Constructor
     * @param userID UUID of the user asking to join the table
     * @param tableID UUID of the table the user wants to join
     * @param asPlayer True if the user joins as a player
     */
    public AskJoinTableMessage(UUID userID, UUID tableID, boolean asPlayer) {
        this.userID = userID;
        this.tableID = tableID;
        this.asPlayer = asPlayer;
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.server.ServerDataEngine)
     */
    @Override
    public void process(ServerDataEngine dataEngine) {
        dataEngine.askJoinTable(new User(new Profile(this.userID)), new GameTable(this.tableID), this.asPlayer);
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.ClientDataEngine)
     */
    @Override
    public void process(ClientDataEngine dataEngine) {}

}
