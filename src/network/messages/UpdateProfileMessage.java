package network.messages;

import java.util.UUID;

import data.ClientDataEngine;
import data.Profile;
import data.server.ServerDataEngine;

public class UpdateProfileMessage implements IMessage {


	private static final long serialVersionUID = 633369383726455631L;

	private Profile profile;
	private UUID user;
	
	/**
	 * @param user
	 * @param profile
	 */
	public UpdateProfileMessage(UUID user, Profile profile) {
		this.user = user;
		this.profile = profile;
	}

    @Override
    public void process(ServerDataEngine dataEngine) {
    	dataEngine.updateUserProfile(user, profile);
        
    }

    @Override
    public void process(ClientDataEngine dataEngine) {
        // TODO Auto-generated method stub
        
    }
}
