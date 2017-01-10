package network.messages;

import java.util.UUID;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.Parameters;
import data.Profile;
import data.Rules;
import data.server.InterfaceSingleThreadData;
import data.User;

/**
 * Message to be sent when creating a table
 * @author lenovo
 *
 */
public class CreateTableMessage implements IMessage{

	private static final long serialVersionUID = 1L;
	private UUID userUUID;
	private String name;
	// TODO : check the utility of pwd (not used by data)
	private Integer min;
	private Integer max;
	private Integer token;
	private Boolean withSpec;
	private Boolean withChat;
	private String rules;

    /**
     * Constructor
     * @param userUUID UUID of the user creating the table
     * @param name Name of the table
     * @param min Minimum number of players
     * @param max Maximum number of players
     * @param token Token
     * @param withSpec True if accepting spectator, false otherwise
     * @param withChat True if chat enbale, false otherwise
     * @param rules Rules of the game
     */
    public CreateTableMessage(UUID userUUID, String name, int min, int max, int token, boolean withSpec, boolean withChat, Rules rules){
        this.userUUID = userUUID;
        this.name = name;
        this.min = min;
        this.max = max;
        this.token = token;
        this.withSpec = withSpec;
        this.withChat = withChat;
        this.rules = FxGson.create().toJson(rules);
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
     */
    @Override
    public void process(InterfaceSingleThreadData dataEngine) {
    	System.out.println("Appel du process c�t� serveur");
        User user = new User(new Profile(userUUID));
        Parameters parameters = new Parameters(min, max, token, withSpec, withChat, FxGson.create().fromJson(rules, Rules.class));
    	try {
			dataEngine.createNewTable(user, name, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
     */
    @Override
    public void process(InterfaceSingleThreadDataClient dataEngine) {}
}
