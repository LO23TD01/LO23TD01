package network.messages;

import java.util.UUID;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.Profile;
import data.server.ServerDataEngine;
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
	public UpdateProfileMessage(UUID user, Profile profile) {
		//Handle image serialization
		if(profile.getAvatar() != null){
			image = BufferedImageBuilder.toByteArray(profile.getAvatar());
			profile.setAvatar(null);
		}

		this.user = user;
		this.profile = FxGson.create().toJson(profile);
	}

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.server.ServerDataEngine)
     */
    @Override
    public void process(ServerDataEngine dataEngine) {
    	Profile p = FxGson.create().fromJson(profile, Profile.class);

    	//Converts bytes to Image and set the profile
		if(image != null)
			p.setAvatar(BufferedImageBuilder.toImage(image));

    	dataEngine.updateUserProfile(user, p);

    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.ClientDataEngine)
     */
    @Override
    public void process(ClientDataEngine dataEngine) {}
}
