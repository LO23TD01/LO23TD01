package network.messages;

import data.client.ClientDataEngine;
import data.Profile;
import data.server.ServerDataEngine;

public class ConnectionMessage implements IMessage{

	private static final long serialVersionUID = -2428194153289587089L;
	
	public Profile profile;
	
	public ConnectionMessage(Profile p){
		profile = p;
	}
	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.connectUser(profile);
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

}
