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
		// dataEngine.quit(user, table); probl�me je n'ai pas de table, la m�thode est quit(UUID user), conform�ment au diagramme de s�quence
		// conform�ment au m�me diagramme la m�thode de l'interface Data devrait prendre uniquement un User
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

}
