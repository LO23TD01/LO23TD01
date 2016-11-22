package network.messages;

import java.util.UUID;
import network.server.SocketClientHandler;
public class LogoutUserRequestMessage implements IMessage{

    private static final long serialVersionUID = -2428194153289587089L;

    private UUID user;

    public LogoutUserRequestMessage(UUID user){
        this.user = user;
    }

    @Override
    public void process() {
        //Appel de disconnectUser du ServerDataEngine à partir de SocketClientHandler
    }

}
