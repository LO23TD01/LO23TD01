package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a user accepts to replay
 * @author lenovo
 */
public class AcceptReplayMessage implements IMessage {

	private static final long serialVersionUID = 5399551477085698202L;
	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the user who accepted the message
	 */
	public AcceptReplayMessage(UUID user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.hasAcceptedReplay(user);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {}

}
