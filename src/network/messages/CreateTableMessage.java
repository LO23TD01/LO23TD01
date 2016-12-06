package network.messages;

import java.util.UUID;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.Parameters;
import data.Profile;
import data.Rules;
import data.server.ServerDataEngine;
import data.User;

public class CreateTableMessage implements IMessage{

	private static final long serialVersionUID = 1L;
	private UUID userUUID;
	private String name;
	// TODO : check the utility of pwd (not used by data)
	private String pwd;
	private Integer min;
	private Integer max;
	private Integer token;
	private Boolean withSpec;
	private Boolean withChat;
	private String rules;
	
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

    @Override
    public void process(ServerDataEngine dataEngine) {
        User user = new User(new Profile(userUUID));
        Parameters parameters = new Parameters(min, max, token, withSpec, withChat, FxGson.create().fromJson(rules, Rules.class)); 
    	try {
			dataEngine.createTable(user, name, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}        
    }

    @Override
    public void process(ClientDataEngine dataEngine) {}
}
