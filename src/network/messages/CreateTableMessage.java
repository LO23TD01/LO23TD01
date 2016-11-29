package network.messages;

import java.util.UUID;

import data.ClientDataEngine;
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
	private Rules rules;
	
    public CreateTableMessage(UUID userUUID, String name, String pwd, int min, int max, int token, boolean withSpec, boolean withChat, Rules rules){
        this.userUUID = userUUID;
        this.name = name;
        this.pwd = pwd;
        this.min = min;
        this.max = max;
        this.token = token;
        this.withSpec = withSpec;
        this.withChat = withChat;
        this.rules = rules;
    }

    @Override
    public void process(ServerDataEngine dataEngine) {
        User user = new User(new Profile(userUUID));
        Parameters parameters = new Parameters(min, max, token, withSpec, withChat, rules); 
    	try {
			dataEngine.createTable(user, name, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}        
    }

    @Override
    public void process(ClientDataEngine dataEngine) {}
}
