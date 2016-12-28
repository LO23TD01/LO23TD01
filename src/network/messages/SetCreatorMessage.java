package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.InterfaceSingleThreadData;

public class SetCreatorMessage implements IMessage {

	private static final long serialVersionUID = -7893459222716162647L;
	private UUID creator;
	
	public SetCreatorMessage(UUID creator) {
		this.creator = creator;
	}

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.setCreator(creator);
	}

}
