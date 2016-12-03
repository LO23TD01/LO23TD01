package network.messages;

import data.client.ClientDataEngine;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;

import data.Profile;
import data.User;
import data.server.ServerDataEngine;

public class NewUserMessage implements IMessage {

	private static final long serialVersionUID = 611407636768645351L;
	
	public String profile;
	
	public NewUserMessage(Profile p){
		profile = FxGson.create().toJson(p);
	}
		
	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.updateUsers(new User(FxGson.create().fromJson(profile, Profile.class)));
	}

}
