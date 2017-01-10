package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when accepting to replay a game
 * @author lenovo
 *
 */
public class AcceptReplayMessage implements IMessage {

	private static final long serialVersionUID = 5399551477085698202L;
	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the user accepting
	 */
	public AcceptReplayMessage(UUID user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		dataEngine.hasAcceptedReplay(user);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		// TODO Auto-generated method stub

	}

}
