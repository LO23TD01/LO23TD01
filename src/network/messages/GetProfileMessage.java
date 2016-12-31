package network.messages;

import java.util.UUID;
import data.User;
import data.Profile;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class GetProfileMessage implements IMessage {

	private static final long serialVersionUID = 3379374689137731613L;
	private UUID user;
	private UUID sender;

	public GetProfileMessage(UUID user, UUID sender) {
		this.user = user;
		this.sender = sender;
	}

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		Profile profile = dataEngine.getProfile(new User(new Profile(user)));
		//TODO ????
		//dataEngine.getComServer().sendProfile(sender, profile);
	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		// TODO Auto-generated method stub

	}

}
