package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class SetCreatorMessage implements IMessage {

	private static final long serialVersionUID = -7893459222716162647L;
	private UUID creator;
	
	public SetCreatorMessage(UUID creator) {
		this.creator = creator;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.setCreator(creator);
	}

}
