package network.messages;

import java.util.UUID;

import data.ClientDataEngine;
import data.Profile;
import data.server.ServerDataEngine;
import data.User;

public class LaunchGameMessage implements IMessage{

	private static final long serialVersionUID = 1L;
	private UUID userUUID;

    public LaunchGameMessage(UUID userUUID){
        this.userUUID = userUUID;
    }

    @Override
    public void process(ServerDataEngine dataEngine) {
        User user = new User(new Profile(userUUID));
    	dataEngine.launchGame(user);        
    }

    @Override
    public void process(ClientDataEngine dataEngine) {}
}
