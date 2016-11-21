package network.messages;

import java.util.UUID;


public class GetProfileMessage implements IMessage {

	private static final long serialVersionUID = 3379374689137731613L;
	private UUID user;
	
	public GetProfileMessage(UUID user) {
		this.user = user;
	}
	
	@Override
	public void process() {
		/**
		 * TODO
		 * Use interface of DATA-Server 
		 */ 
		// getProfile(user);
	}

}
