package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class HasRefusedMessage implements IMessage {

	private static final long serialVersionUID = -5383642164826933945L;

	private UUID user;
	
	public HasRefusedMessage(UUID user) {
		this.user = user;
	}
	
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.hasRefusedReplay(new User(new Profile(user)));
	}

}
