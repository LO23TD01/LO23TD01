package network.messages;

import java.util.UUID;

import data.GameTable;
import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class AskQuitTableMessage implements IMessage {

	private static final long serialVersionUID = 2143481984334543591L;
    private UUID tableId;
    private UUID user;

    public AskQuitTableMessage(UUID tableId, UUID userID) {
    	this.tableId = tableId;
    	this.user = userID;
    }


	@Override
	public void process(ServerDataEngine dataEngine) {
		// Ce n'est pas la bonne méthode, il faut une méthode askQuitTable spécifique lorsque le user est admin selon le diagramme
		// dataEngine.quit(new User(new Profile(user)), new GameTable(tableId));
	}

	@Override
	public void process(ClientDataEngine dataEngine) {}

}
