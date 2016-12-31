package network.messages;

import java.util.UUID;

import org.hildan.fxgson.FxGson;

import data.GameTable;
import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

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
	public void process(InterfaceSingleThreadData dataEngine) {
		// dataEngine.quit(user, table); problème je n'ai pas de table, la méthode est quit(UUID user), conformément au diagramme de sÃ©quence
		// conformément au même diagramme la méthode de l'interface Data devrait prendre uniquement un User
		
		// Quitter partie (avant/après) -> quit(user) => la méthode quit(user) n'existe pas
		// Quitter partie en cours -> quit(user,table)
		if( tableId != null )
			dataEngine.quit(new User(new Profile(user)), new GameTable(tableId));
		
		// Attente de méthode quit de DATA si besoin
		//else
		//	dataEngine.quit(new User(new Profile(user)));			
	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		// TODO Auto-generated method stub
		
	}

}
