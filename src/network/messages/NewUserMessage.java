package network.messages;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;

import data.Profile;
import data.User;
import data.server.InterfaceSingleThreadData;
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

		Image avatar = p.getAvatar();

		//Handle image serialization
		if(avatar != null){
			image = BufferedImageBuilder.toByteArray(avatar);
			p.setAvatar(null);
			profile = FxGson.create().toJson(p);
			p.setAvatar(avatar);
		}else{
			profile = FxGson.create().toJson(p);
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

		dataEngine.updateUsers(new User(p));
	}

}
