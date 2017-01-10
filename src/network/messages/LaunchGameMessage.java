package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.Profile;
import data.server.ServerDataEngine;
import data.User;

/**
 * Message to be sent when a game is lanched
 * @author lenovo
 *
 */
public class LaunchGameMessage implements IMessage{

	private static final long serialVersionUID = 1L;
	private UUID userUUID;

    /**
     * Constructor
     * @param userUUID UUID of the game's creator
     */
    public LaunchGameMessage(UUID userUUID){
        this.userUUID = userUUID;
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.server.ServerDataEngine)
     */
    @Override
    public void process(ServerDataEngine dataEngine) {
        User user = new User(new Profile(userUUID));
    	dataEngine.launchGame(user);
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.ClientDataEngine)
     */
    @Override
    public void process(ClientDataEngine dataEngine) {}
}
