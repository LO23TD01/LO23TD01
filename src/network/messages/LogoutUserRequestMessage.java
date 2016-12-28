package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.Profile;
import data.server.InterfaceSingleThreadData;
import data.User;
import network.server.SocketClientHandler;
public class LogoutUserRequestMessage implements IMessage{

    private static final long serialVersionUID = -2428194153289587089L;

    private UUID user;

    public LogoutUserRequestMessage(UUID user){
        this.user = user;
    }

    @Override
    public void process(InterfaceSingleThreadData dataEngine) {
    	dataEngine.disconnectUser(new User(new Profile(user)));
    }

    @Override
    public void process(ClientDataEngine dataEngine) {
        // TODO Auto-generated method stub

    }
}
