package network.messages;

import java.util.UUID;

import data.ClientDataEngine;
import data.server.ServerDataEngine;

public class QuitGameMessage implements IMessage {

	private static final long serialVersionUID = -506628116565819592L;
	private UUID user;
	
	public QuitGameMessage(UUID user) {
		this.user = user;
	}
	
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		// dataEngine.quit(user, table); problème je n'ai pas de table, la méthode est quit(UUID user), conformément au diagramme de séquence
		// conformément au même diagramme la méthode de l'interface Data devrait prendre uniquement un User
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

}
