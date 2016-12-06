package network.messages;

import data.client.ClientDataEngine;

import java.util.UUID;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.Profile;
import data.server.ServerDataEngine;

public class ConnectionMessage implements IMessage{

	private static final long serialVersionUID = -2428194153289587089L;
	public String profile;
	public UUID uuid;
	
	public ConnectionMessage(Profile p){
		uuid = p.getUUID();
		profile = FxGson.create().toJson(p);
	}
	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.connectUser(FxGson.create().fromJson(profile, Profile.class));
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

}
