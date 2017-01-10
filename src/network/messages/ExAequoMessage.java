package network.messages;

import java.util.Arrays;
import java.util.List;

import org.hildan.fxgson.FxGson;

import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent if the game ends in a draw
 * @author lenovo
 *
 */
public class ExAequoMessage implements IMessage {

	private static final long serialVersionUID = 9183564164298788330L;
	private String usersExaequo;
	private boolean win;

	/**
	 * COnstructor
	 * @param usersExaequo Users involved in draw
	 * @param win True if they won, false otherwise
	 */
	public ExAequoMessage(List<User> usersExaequo, boolean win) {
		User[] users = usersExaequo.toArray(new User[0]);
		this.usersExaequo = FxGson.create().toJson(users);
		this.win = win;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		User[] users = FxGson.create().fromJson(usersExaequo, User[].class);
		dataEngine.exAequoCase(Arrays.asList(users), win);
	}

}
