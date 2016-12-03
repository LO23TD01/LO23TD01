package network.messages;

import java.util.UUID;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.Profile;
import data.server.ServerDataEngine;

public class UpdateProfileMessage implements IMessage {


	private static final long serialVersionUID = 633369383726455631L;

	private String profile;
	private UUID user;
	
	/**
	 * @param user
	 * @param profile
	 */
	public UpdateProfileMessage(UUID user, Profile profile) {
		this.user = user;
		this.profile = FxGson.create().toJson(profile);
	}

    @Override
    public void process(ServerDataEngine dataEngine) {
    	dataEngine.updateUserProfile(user, FxGson.create().fromJson(profile, Profile.class));
        
    }

    @Override
    public void process(ClientDataEngine dataEngine) {
        // TODO Auto-generated method stub
        
    }
}
