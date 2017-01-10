package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when a player has lost
 * @author lenovo
 *
 */
public class HasLostMessage implements IMessage{

    private static final long serialVersionUID = -5930391832092033148L;
    private UUID user;

    /**
     * Constructor
     * @param user Losing player
     */
    public HasLostMessage(UUID user) {
        this.user = user;
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
     */
    @Override
    public void process(InterfaceSingleThreadData dataEngine) {}

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
     */
    @Override
    public void process(InterfaceSingleThreadDataClient dataEngine) {
        dataEngine.hasLost(new User(new Profile(this.user)));
    }

}
