package network.messages;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.Profile;
import data.server.InterfaceSingleThreadData;
import network.messages.utils.BufferedImageBuilder;
import sun.nio.ch.IOUtil;

/**
 * Message to be sent when a player connects
 * @author lenovo
 *
 */
public class ConnectionMessage implements IMessage{

	private static final long serialVersionUID = -2428194153289587089L;
	public String profile;
	public byte[] image;
	public UUID uuid;

	/**
	 * Constructor
	 * @param p Profile of the user connecting
	 */
	public ConnectionMessage(Profile p){
		uuid = p.getUUID();

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
	public void process(InterfaceSingleThreadData dataEngine) {

		Profile p = FxGson.create().fromJson(profile, Profile.class);

		//Converte bytes to Image and set the profile
		if(image != null)
			p.setAvatar(BufferedImageBuilder.toImage(image));

		dataEngine.connectUser(p);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {}

}
