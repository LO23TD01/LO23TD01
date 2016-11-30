package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class RefuseReplayMessage implements IMessage {

	private static final long serialVersionUID = -6693270949227931714L;
	private UUID user;
	
	public RefuseReplayMessage(UUID user) {
		this.user = user;
	}
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.hasRefusedReplay(user);
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// TODO Auto-generated method stub

	}

}
