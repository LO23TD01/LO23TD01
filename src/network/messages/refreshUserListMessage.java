package network.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.server.InterfaceSingleThreadData;
import data.User;

public class refreshUserListMessage implements IMessage{

	private static final long serialVersionUID = 2537929372539873650L;

	private String usersToRefresh;
	
	public refreshUserListMessage(List<User> usersToRefresh) {
		User[] users = usersToRefresh.toArray(new User[0]);
		this.usersToRefresh = FxGson.create().toJson(users);
	}

    @Override
    public void process(InterfaceSingleThreadData dataEngine) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void process(ClientDataEngine dataEngine) {
    	User[] users = FxGson.create().fromJson(usersToRefresh, User[].class);
    	dataEngine.refreshUsersList(Arrays.asList(users));
    }
}
