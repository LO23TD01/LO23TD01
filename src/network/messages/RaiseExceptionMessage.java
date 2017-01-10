package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when a exception is being raised
 * @author lenovo
 *
 */
public class RaiseExceptionMessage implements IMessage{

	private static final long serialVersionUID = 9221582005454257005L;
	private String errorMessage;
	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the raising user
	 * @param error Error message
	 */
	public RaiseExceptionMessage(UUID user, String error) {
		this.user = user;
		this.errorMessage = error;
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
		dataEngine.raiseException(errorMessage);
	}

}
