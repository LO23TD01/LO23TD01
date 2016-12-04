package network.messages;

import java.util.UUID;

import data.GameTable;
import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class QuitGameMessage implements IMessage {

	private static final long serialVersionUID = -506628116565819592L;
	private UUID user;
	private UUID tableId;
	
	public QuitGameMessage(UUID user) {
		this.user = user;
	}
	
	public QuitGameMessage(UUID user, UUID tableId) {
		this.user = user;
		this.tableId = tableId;
	}
	
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		// dataEngine.quit(user, table); problème je n'ai pas de table, la méthode est quit(UUID user), conformément au diagramme de séquence
		// conformément au même diagramme la méthode de l'interface Data devrait prendre uniquement un User
		
		// Quitter partie (avant/apr�s) -> quit(user) => la m�thode quit(user) n'existe pas
		// Quitter partie en cours -> quit(user,table)
		dataEngine.quit(new User(new Profile(user)), new GameTable(tableId));
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

}
