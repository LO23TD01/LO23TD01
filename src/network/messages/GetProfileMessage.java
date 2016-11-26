package network.messages;

import java.util.UUID;
import data.ClientDataEngine;
import data.server.ServerDataEngine;

public class GetProfileMessage implements IMessage {

	private static final long serialVersionUID = 3379374689137731613L;
	private UUID user;
	
	public GetProfileMessage(UUID user) {
		this.user = user;
	}
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

}
