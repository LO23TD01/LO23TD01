package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message being sent when a user refuses to replay
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
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		dataEngine.hasRefusedReplay(user);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {}

}
