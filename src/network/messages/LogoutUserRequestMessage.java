package network.messages;

import java.util.UUID;

import data.ClientDataEngine;
import data.ServerDataEngine;
import network.server.SocketClientHandler;
public class LogoutUserRequestMessage implements IMessage{

    private static final long serialVersionUID = -2428194153289587089L;

    private UUID user;

    public LogoutUserRequestMessage(UUID user){
        this.user = user;
    }

    @Override
    public void process(ServerDataEngine dataEngine) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void process(ClientDataEngine dataEngine) {
        // TODO Auto-generated method stub
        
    }
}
