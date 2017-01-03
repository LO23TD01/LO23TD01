package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when a user is accepted in a table
 * @author lenovo
 *
 */
public class HasAcceptedMessage implements IMessage {

	private static final long serialVersionUID = 6986290106710598367L;

	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the user accepted into the table
	 */
	public HasAcceptedMessage(UUID user) {
		this.user = user;
	}


	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {
		// Appeler dataEngine.hasAccepted(user)
	}

}
