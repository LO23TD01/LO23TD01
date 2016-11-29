package network.messages;

import java.util.List;

import data.ClientDataEngine;
import data.server.ServerDataEngine;
import data.User;

public class refreshUserListMessage implements IMessage{

	private static final long serialVersionUID = 2537929372539873650L;

	private List<User> usersToRefresh;
	
	public refreshUserListMessage(List<User> usersToRefresh) {
		this.usersToRefresh = usersToRefresh;
	}

    @Override
    public void process(ServerDataEngine dataEngine) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void process(ClientDataEngine dataEngine) {
    	/*
    	 * TODO
    	 * Need interface of ClientDataEngine
    	 */
    }
}
