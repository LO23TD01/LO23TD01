package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class HasWonMessage implements IMessage{

	private static final long serialVersionUID = -5930391832092033148L;
	private UUID user;

	public HasWonMessage(UUID user) {
		this.user = user;
	}
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.hasWon(new User(new Profile(this.user)));
	}

}
