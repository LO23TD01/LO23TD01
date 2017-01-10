package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when setting a new creator
 * @author lenovo
 *
 */
public class SetCreatorMessage implements IMessage {

	private static final long serialVersionUID = -7893459222716162647L;
	private UUID creator;

	/**
	 * Constructor
	 * @param creator UUID of the new creator
	 */
	public SetCreatorMessage(UUID creator) {
		this.creator = creator;
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
		dataEngine.setCreator(creator);
	}

}
