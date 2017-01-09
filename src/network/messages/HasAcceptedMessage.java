package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class HasAcceptedMessage implements IMessage {

	private static final long serialVersionUID = 6986290106710598367L;

	private UUID user;

	public HasAcceptedMessage(UUID user) {
		this.user = user;
	}


	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.hasAcceptedReplay(new User(new Profile(user)));
	}

}
