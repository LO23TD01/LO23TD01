package network.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;
import data.User;

/**
 * Message to be sent when the list of users is refreshed
 * @author lenovo
 *
 */
public class refreshUserListMessage implements IMessage{

	private static final long serialVersionUID = 2537929372539873650L;

	private String usersToRefresh;

	/**
	 * Constructor
	 * @param usersToRefresh List os the users to be refreshed
	 */
	public refreshUserListMessage(List<User> usersToRefresh) {
		User[] users = usersToRefresh.toArray(new User[1]);
		this.usersToRefresh = FxGson.create().toJson(users);
	}

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.server.ServerDataEngine)
     */
    @Override
    public void process(ServerDataEngine dataEngine) {}

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.ClientDataEngine)
     */
    @Override
    public void process(ClientDataEngine dataEngine) {
    	User[] users = FxGson.create().fromJson(usersToRefresh, User[].class);
    	dataEngine.refreshUsersList(Arrays.asList(users));
    }
}
