package network.messages;

import java.util.UUID;

import org.hildan.fxgson.FxGson;

import data.GameTable;
import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a player quits a game
 * @author lenovo
 *
 */
public class QuitGameMessage implements IMessage {

	private static final long serialVersionUID = -506628116565819592L;
	private UUID user;
	private UUID tableId;

	/**
	 * Constructor
	 * @param user UUID of the quitting player
	 */
	public QuitGameMessage(UUID user) {
		this.user = user;
	}

	/**
	 * Constructor
	 * @param user UUID of the quitting player
	 * @param tableId UUID of the table the user quits
	 */
	public QuitGameMessage(UUID user, UUID tableId) {
		this.user = user;
		this.tableId = tableId;
	}


	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {
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

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {}

}
