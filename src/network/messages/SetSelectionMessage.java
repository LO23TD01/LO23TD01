package network.messages;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when selecting die
 * @author lenovo
 *
 */
public class SetSelectionMessage implements IMessage {

	private static final long serialVersionUID = 7929594584292402451L;

	private boolean dice1;
	private boolean dice2;
	private boolean dice3;

	/**
	 * Constructor
	 * @param dice1 True if the first dice is selected, false otherwise
	 * @param dice2 True if the first dice is selected, false otherwise
	 * @param dice3 True if the first dice is selected, false otherwise
	 */
	public SetSelectionMessage(boolean dice1, boolean dice2, boolean dice3) {
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.dice3 = dice3;
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
		dataEngine.setSelection(dice1, dice2, dice3);
	}

}
