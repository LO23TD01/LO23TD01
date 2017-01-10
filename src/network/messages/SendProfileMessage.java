package network.messages;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.Profile;
import data.User;
import data.server.InterfaceSingleThreadData;
import network.messages.utils.BufferedImageBuilder;

/**
 * Message to be sent when sending a profile
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
	 * @param receiver UUID of the receiver
	 * @param p Profile being sent
	 */
	public SendProfileMessage(UUID receiver, Profile p) {
		this.receiver = receiver;

		Image avatar = p.getAvatar();

		//Handle image serialization
		if(avatar != null){
			image = BufferedImageBuilder.toByteArray(avatar);
			p.setAvatar(null);
			this.profile = FxGson.create().toJson(p);
			p.setAvatar(avatar);
		}else{
			this.profile = FxGson.create().toJson(p);
		}
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
    	Profile p = FxGson.create().fromJson(profile, Profile.class);

    	//Converte bytes to Image and set the profile
    	if(image != null)
			p.setAvatar(BufferedImageBuilder.toImage(image));

    	//Display profile peut utiliser updateUsers pour afficher le profile
        dataEngine.updateUsers(new User(p));
    }
}
