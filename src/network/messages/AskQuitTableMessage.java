package network.messages;

import java.util.UUID;

import data.GameTable;
import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class AskQuitTableMessage implements IMessage {

	private static final long serialVersionUID = 2143481984334543591L;
    private UUID tableId;
    private UUID user;
    
    public AskQuitTableMessage(UUID tableId, UUID userID) {
    	this.tableId = tableId;
    	this.user = userID;
    }

	
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// Ce n'est pas la bonne méthode, il faut une méthode askQuitTable spécifique lorsque le user est admin selon le diagramme
		// dataEngine.quit(new User(new Profile(user)), new GameTable(tableId));
	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		// TODO Auto-generated method stub

	}

}
