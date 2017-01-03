package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class IsTurnMessage implements IMessage{

	private static final long serialVersionUID = -1022076184764195866L;

	private UUID player;

	public IsTurnMessage(UUID player) {
		this.player = player;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.isTurn(new User(new Profile(player)));
	}

}
