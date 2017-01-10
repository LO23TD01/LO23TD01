package network.server;

import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;
import network.messages.IMessage;

/**
 * Message to be sent when replaying a game
 * @author lenovo
 *
 */
public class ReplayMessage implements IMessage {

	private static final long serialVersionUID = -1691367847247708531L;

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
		dataEngine.replay();
	}

}
