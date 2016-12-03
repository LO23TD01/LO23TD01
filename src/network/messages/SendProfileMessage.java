package network.messages;

import java.util.UUID;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.Profile;
import data.server.ServerDataEngine;

public class SendProfileMessage implements IMessage{

	private static final long serialVersionUID = 5676541305074045177L;
	
	private String profile;
	private UUID receiver;
	
	public SendProfileMessage(UUID receiver, Profile profile) {
		this.receiver = receiver;
		this.profile = FxGson.create().toJson(profile);
	}

    @Override
    public void process(ServerDataEngine dataEngine) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void process(ClientDataEngine dataEngine) {
    	//Méthode inexistante
        //Appeler dataEngine.diplayProfile(FxGson.create().fromJson(profile, Profile.class));
    }
}
