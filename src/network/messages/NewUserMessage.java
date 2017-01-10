package network.messages;

import data.client.ClientDataEngine;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;

import data.Profile;
import data.User;
import data.server.ServerDataEngine;
import network.messages.utils.BufferedImageBuilder;

/**
 * Message to be sent when a new user logs in
 * @author lenovo
 *
 */
public class NewUserMessage implements IMessage {

	private static final long serialVersionUID = 611407636768645351L;

	public String profile;
	public byte[] image;

	/**
	 * Constructor
	 * @param p Profile of the new user
	 */
	public NewUserMessage(Profile p){

		//Handle image serialization
		if(p.getAvatar() != null){
			image = BufferedImageBuilder.toByteArray(p.getAvatar());
			p.setAvatar(null);
		}

		profile = FxGson.create().toJson(p);
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

		dataEngine.updateUsers(new User(p));
	}

}
