package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.InterfaceSingleThreadData;

public class AcceptReplayMessage implements IMessage {

	private static final long serialVersionUID = 5399551477085698202L;
	private UUID user;
	
	public AcceptReplayMessage(UUID user) {
		this.user = user;
	}
	
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		dataEngine.hasAcceptedReplay(user);
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// TODO Auto-generated method stub

	}

}
