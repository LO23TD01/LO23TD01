package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class IsTurnMessage implements IMessage{

	private static final long serialVersionUID = -1022076184764195866L;

	private UUID player;
	
	public IsTurnMessage(UUID player) {
		this.player = player;
	}

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.isTurn(new User(new Profile(player)));
	}

}
