package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a player refuses to replay
 * @author lenovo
 *
 */
public class RefuseReplayMessage implements IMessage {

	private static final long serialVersionUID = -6693270949227931714L;
	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the refusing player
	 */
	public RefuseReplayMessage(UUID user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.hasRefusedReplay(user);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {}

}
