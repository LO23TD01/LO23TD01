package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class HasSelectedMessage implements IMessage {

	private static final long serialVersionUID = -5674362211027759000L;

	private UUID player;
	private boolean dice1;
	private boolean dice2;
	private boolean dice3;

	public HasSelectedMessage(UUID player, boolean dice1, boolean dice2, boolean dice3) {
		this.player = player;
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.dice3 = dice3;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.hasSelected(new User(new Profile(player)), dice1, dice2, dice3);
	}

}
