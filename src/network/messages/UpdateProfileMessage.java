package network.messages;

import java.awt.Image;
import java.util.UUID;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.Profile;
import data.server.InterfaceSingleThreadData;
import network.messages.utils.BufferedImageBuilder;

/**
 * Message to be sent when updating a profile
 * @author lenovo
 *
 */
public class UpdateProfileMessage implements IMessage {

	private static final long serialVersionUID = 633369383726455631L;

	private String profile;
	private byte[] image;
	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the user whose profile is updated
	 * @param profile New profile
	 */
	public UpdateProfileMessage(UUID user, Profile p) {

		Image avatar = p.getAvatar();
		//Handle image serialization
		if(avatar != null){
			image = BufferedImageBuilder.toByteArray(avatar);
			p.setAvatar(null);
			this.profile = FxGson.create().toJson(profile);
			p.setAvatar(avatar);
		}else{
			this.profile = FxGson.create().toJson(profile);
		}

		this.user = user;
	}

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
     */
    @Override
    public void process(InterfaceSingleThreadData dataEngine) {
    	Profile p = FxGson.create().fromJson(profile, Profile.class);

    	//Converte bytes to Image and set the profile
		if(image != null)
			p.setAvatar(BufferedImageBuilder.toImage(image));

    	dataEngine.updateUserProfile(user, p);

    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
     */
    @Override
    public void process(InterfaceSingleThreadDataClient dataEngine) {}
}
