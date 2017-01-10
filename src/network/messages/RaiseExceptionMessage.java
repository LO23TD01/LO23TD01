package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when an exception is raised
 * @author lenovo
 *
 */
public class RaiseExceptionMessage implements IMessage{

	private static final long serialVersionUID = 9221582005454257005L;
	private String errorMessage;
	private UUID user;

	/**
	 * Constructor
	 * @param user UUID of the user raising the exception
	 * @param error Message of the error
	 */
	public RaiseExceptionMessage(UUID user, String error) {
		this.user = user;
		this.errorMessage = error;
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
		// dataEngine.raiseException(error);
	}

}
