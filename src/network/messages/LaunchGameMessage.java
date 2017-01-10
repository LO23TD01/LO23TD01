package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.Profile;
import data.server.InterfaceSingleThreadData;
import data.User;

/**
 * Message to be sent when a game is launched
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
     * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
     */
    @Override
    public void process(InterfaceSingleThreadData dataEngine) {
        User user = new User(new Profile(userUUID));
    	dataEngine.launchGame(user);
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
     */
    @Override
    public void process(InterfaceSingleThreadDataClient dataEngine) {}
}
