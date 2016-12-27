package network.server;

import java.util.Arrays;
import java.util.List;

import org.hildan.fxgson.FxGson;

import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;
import network.messages.IMessage;

public class ExAequoMessage implements IMessage {

	private static final long serialVersionUID = 9183564164298788330L;
	private String usersExaequo;
	private boolean win;
	
	public ExAequoMessage(List<User> usersExaequo, boolean win) {
		User[] users = usersExaequo.toArray(new User[0]);
		this.usersExaequo = FxGson.create().toJson(users);
		this.win = win;
	}
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		User[] users = FxGson.create().fromJson(usersExaequo, User[].class);
		dataEngine.exAequoCase(Arrays.asList(users), win);
	}

}
