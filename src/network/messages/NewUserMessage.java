package network.messages;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;

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

public class NewUserMessage implements IMessage {

	private static final long serialVersionUID = 611407636768645351L;
	
	public String profile;
	public byte[] image;
	
	public NewUserMessage(Profile p){
		
		//Handle image serialization 
		if(p.getAvatar() != null){
			image = BufferedImageBuilder.toByteArray(p.getAvatar());
			p.setAvatar(null);
		}
				
		profile = FxGson.create().toJson(p);
	}
		
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		
		Profile p = FxGson.create().fromJson(profile, Profile.class);
		
		//Converte bytes to Image and set the profile
    	if(image != null)
			p.setAvatar(BufferedImageBuilder.toImage(image));
		
		dataEngine.updateUsers(new User(p));
	}

}
