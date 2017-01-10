package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when throwing die
 * @author lenovo
 *
 */
public class ThrowDiceMessage implements IMessage{

	private static final long serialVersionUID = 6487827669027387150L;

	private UUID user;
	private boolean d1;
	private boolean d2;
	private boolean d3;

	/**
	 * Constructor
	 * @param user UUID of the throwing user
	 * @param d1 True if the first dice is being thrown, false otherwise
	 * @param d2 True if the second dice is being thrown, false otherwise
	 * @param d3 True if the second dice is being thrown, false otherwise
	 */
	public ThrowDiceMessage(UUID user, boolean d1, boolean d2, boolean d3) {
		this.user = user;
		this.d1 = d1;
		this.d2 = d2;
		this.d3 = d3;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		dataEngine.hasThrown(user, d1, d2, d3);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {}

}
