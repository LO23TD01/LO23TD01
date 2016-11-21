package network.messages;

import java.util.UUID;

import data.Profile;

public class SendProfileMessage implements IMessage{

	private static final long serialVersionUID = 5676541305074045177L;
	
	private Profile profile;
	private UUID receiver;
	
	public SendProfileMessage(UUID receiver, Profile profile) {
		this.receiver = receiver;
		this.profile = profile;
	}
	
	@Override
	public void process() {
		/**
		 * TODO
		 * Use interface of DATA-Local
		 */
		// displayProfile(profile);
		
		
	}

}
