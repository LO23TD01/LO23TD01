package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class HasThrownMessage implements IMessage{

	private static final long serialVersionUID = -502547680784512937L;

	private UUID user;
	private int result1;
	private int result2;
	private int result3;

	public HasThrownMessage(UUID user, int res1, int res2, int res3) {
		this.user = user;
		this.result1 = res1;
		this.result2 = res2;
		this.result3 = res3;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.hasThrown(new User(new Profile(user)), this.result1, this.result2, this.result3);
	}

}
