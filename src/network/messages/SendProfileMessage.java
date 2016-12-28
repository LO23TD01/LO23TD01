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
import data.server.InterfaceSingleThreadData;
import network.messages.utils.BufferedImageBuilder;

public class SendProfileMessage implements IMessage{

	private static final long serialVersionUID = 5676541305074045177L;
	
	private String profile;
	private byte[] image;
	private UUID receiver;
	
	public SendProfileMessage(UUID receiver, Profile profile) {
		this.receiver = receiver;
		
		//Handle image serialization 
		if(profile.getAvatar() != null){
			image = BufferedImageBuilder.toByteArray(profile.getAvatar());
			profile.setAvatar(null);
		}
				
		this.profile = FxGson.create().toJson(profile);
	}

    @Override
    public void process(InterfaceSingleThreadData dataEngine) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void process(ClientDataEngine dataEngine) {
    	Profile p = FxGson.create().fromJson(profile, Profile.class);
    	
    	//Converte bytes to Image and set the profile
    	if(image != null)
			p.setAvatar(BufferedImageBuilder.toImage(image));
    	
    	//Display profile peut utiliser updateUsers pour afficher le profile
        dataEngine.updateUsers(new User(p));
    }
}
