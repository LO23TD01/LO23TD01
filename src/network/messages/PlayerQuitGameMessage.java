package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.InterfaceSingleThreadData;

public class PlayerQuitGameMessage implements IMessage {

	private static final long serialVersionUID = 1308575889279693465L;
	private UUID user;
	
	public PlayerQuitGameMessage(UUID user) {
		this.user = user;
	}
	
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.playerQuitGame(new User(new Profile(user)));
	}

}
