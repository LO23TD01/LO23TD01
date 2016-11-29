package network.messages;

import data.ClientDataEngine;
import data.Profile;
import data.server.ServerDataEngine;

public class NewUserMessage implements IMessage {

	private static final long serialVersionUID = 611407636768645351L;
	
	public Profile profile;
	
	public NewUserMessage(Profile p){
		profile = p;
	}
		
	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		//Appeler dataEngine.updateUsers(Profile); quand l'interface data sera implementee
	}

}
