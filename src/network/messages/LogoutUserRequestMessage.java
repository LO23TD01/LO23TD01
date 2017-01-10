package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.Profile;
import data.server.ServerDataEngine;
import data.User;
import network.server.SocketClientHandler;
/**
 * Message to be sent when a user wants to logout
 * @author lenovo
 *
 */
public class LogoutUserRequestMessage implements IMessage{

    private static final long serialVersionUID = -2428194153289587089L;

    private UUID user;

    /**
     * Constructor
     * @param user UUID of the user requesting the logout
     */
    public LogoutUserRequestMessage(UUID user){
        this.user = user;
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.server.ServerDataEngine)
     */
    @Override
    public void process(ServerDataEngine dataEngine) {
    	dataEngine.disconnect(new User(new Profile(user)));
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.ClientDataEngine)
     */
    @Override
    public void process(ClientDataEngine dataEngine) {}
}
