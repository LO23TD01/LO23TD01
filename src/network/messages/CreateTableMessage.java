package network.messages;

import java.util.UUID;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.Parameters;
import data.Profile;
import data.Rules;
import data.server.ServerDataEngine;
import data.User;

/**
 * Message to be sent when a table is created
 * @author lenovo
 *
 */
public class CreateTableMessage implements IMessage{

	private static final long serialVersionUID = 1L;
	private UUID userUUID;
	private String name;
	private String pwd;
	private Integer min;
	private Integer max;
	private Integer token;
	private Boolean withSpec;
	private Boolean withChat;
	private String rules;

    /**
     * Constructor
     * @param userUUID UUID of the creator
     * @param name Name of the table
     * @param pwd (optional)
     * @param min Minimum number of players
     * @param max Maximum number of players
     * @param token Token of the table
     * @param withSpec True if the table accepts spectators, false otherwise
     * @param withChat True if the table has a chat, false otherwise
     * @param rules Rules of 421 to be applied
     */
    public CreateTableMessage(UUID userUUID, String name, String pwd, int min, int max, int token, boolean withSpec, boolean withChat, Rules rules){
        this.userUUID = userUUID;
        this.name = name;
        this.pwd = pwd;
        this.min = min;
        this.max = max;
        this.token = token;
        this.withSpec = withSpec;
        this.withChat = withChat;
        this.rules = FxGson.create().toJson(rules);
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.server.ServerDataEngine)
     */
    @Override
    public void process(ServerDataEngine dataEngine) {
    	System.out.println("Appel du process côté serveur");
        User user = new User(new Profile(userUUID));
        Parameters parameters = new Parameters(min, max, token, withSpec, withChat, FxGson.create().fromJson(rules, Rules.class));
    	try {
			dataEngine.createNewTable(user, name, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.ClientDataEngine)
     */
    @Override
    public void process(ClientDataEngine dataEngine) {}
}
