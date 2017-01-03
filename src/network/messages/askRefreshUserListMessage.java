package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.Profile;
import data.server.ServerDataEngine;
import data.User;

public class askRefreshUserListMessage implements IMessage{

	private static final long serialVersionUID = -6157527910635620253L;
	private UUID user;

	public askRefreshUserListMessage(UUID user) {
		this.user = user;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.askRefreshUsersList(new User(new Profile(this.user)));
	}

	@Override
	public void process(ClientDataEngine dataEngine) {}

}
