package network.messages;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.Profile;
import data.User;
import data.server.ServerDataEngine;
import network.messages.utils.BufferedImageBuilder;

/**
 * Message to be sent to send a profile to a given user
 * @author lenovo
 *
 */
public class SendProfileMessage implements IMessage{

	private static final long serialVersionUID = 5676541305074045177L;

	private String profile;
	private byte[] image;
	private UUID receiver;

	/**
	 * Constructor
	 * @param receiver UUID of the player receiving the profile
	 * @param profile Profile sent
	 */
	public SendProfileMessage(UUID receiver, Profile profile) {
		this.receiver = receiver;

		//Handle image serialization
		if(profile.getAvatar() != null){
			image = BufferedImageBuilder.toByteArray(profile.getAvatar());
			profile.setAvatar(null);
		}

		this.profile = FxGson.create().toJson(profile);
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
    	Profile p = FxGson.create().fromJson(profile, Profile.class);

    	//Converts bytes to Image and set the profile
    	if(image != null)
			p.setAvatar(BufferedImageBuilder.toImage(image));

    	//Display profile can use updateUsers to display the profile
        dataEngine.updateUsers(new User(p));
    }
}
