package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when setting the creator of a table
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
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.setCreator(creator);
	}

}
