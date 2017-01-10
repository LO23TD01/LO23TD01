package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when a player is kicked
 * @author lenovo
 *
 */
public class KickedMessage implements IMessage {

	private static final long serialVersionUID = 6709711021661501877L;
	private String msg;

	/**
	 * Constructor
	 * @param msg Message to display to the kicked player
	 */
	public KickedMessage(String msg) {
		this.msg = msg;
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
		dataEngine.kicked(msg);
	}

}
