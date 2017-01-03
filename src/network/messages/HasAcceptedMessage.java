package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class HasAcceptedMessage implements IMessage {

	private static final long serialVersionUID = 6986290106710598367L;

	private UUID user;

	public HasAcceptedMessage(UUID user) {
		this.user = user;
	}


	@Override
	public void process(ServerDataEngine dataEngine) {}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// Appeler dataEngine.hasAccepted(user)
	}

}
