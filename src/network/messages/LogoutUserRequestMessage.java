package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.Profile;
import data.server.InterfaceSingleThreadData;
import data.User;
import network.server.SocketClientHandler;

/**
 * Message to be sent when a user asks to logout
 * @author lenovo
 *
 */
public class LogoutUserRequestMessage implements IMessage{

    private static final long serialVersionUID = -2428194153289587089L;

    private UUID user;

    /**
     * Constructor
     * @param user UUID of the user trying to logout
     */
    public LogoutUserRequestMessage(UUID user){
        this.user = user;
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
     */
    @Override
    public void process(InterfaceSingleThreadData dataEngine) {
    	dataEngine.disconnectUser(new User(new Profile(user)));
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
     */
    @Override
    public void process(InterfaceSingleThreadDataClient dataEngine) {}
}
