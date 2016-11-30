package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class PlayerQuitGameMessage implements IMessage {

	private static final long serialVersionUID = 1308575889279693465L;
	private UUID user;
	
	public PlayerQuitGameMessage(UUID user) {
		this.user = user;
	}
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// Appeler dataEngine.playerQuitGame(user)
	}

}
