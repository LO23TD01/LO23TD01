package network.messages;

import java.util.UUID;

import data.ClientDataEngine;
import data.server.ServerDataEngine;

public class HasRefusedMessage implements IMessage {

	private static final long serialVersionUID = -5383642164826933945L;

	private UUID user;
	
	public HasRefusedMessage(UUID user) {
		this.user = user;
	}
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// Appeler dataEngine.hasRefused(user)
	}

}
