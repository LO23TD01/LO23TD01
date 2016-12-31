package network.messages;

import java.util.UUID;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.Profile;
import data.server.InterfaceSingleThreadData;
import network.messages.utils.BufferedImageBuilder;

public class UpdateProfileMessage implements IMessage {


	private static final long serialVersionUID = 633369383726455631L;

	private String profile;
	private byte[] image;
	private UUID user;
	
	/**
	 * @param user
	 * @param profile
	 */
	public UpdateProfileMessage(UUID user, Profile profile) {
		//Handle image serialization 
		if(profile.getAvatar() != null){
			image = BufferedImageBuilder.toByteArray(profile.getAvatar());
			profile.setAvatar(null);
		}
				
		this.user = user;
		this.profile = FxGson.create().toJson(profile);
	}

    @Override
    public void process(InterfaceSingleThreadData dataEngine) {
    	Profile p = FxGson.create().fromJson(profile, Profile.class);
    	
    	//Converte bytes to Image and set the profile
		if(image != null)
			p.setAvatar(BufferedImageBuilder.toImage(image));
		
    	dataEngine.updateUserProfile(user, p);
        
    }

    @Override
    public void process(InterfaceSingleThreadDataClient dataEngine) {
        // TODO Auto-generated method stub
        
    }
}
