package network.messages;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message to be sent when die are being selected
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
	 * @param dice1 True if the dice is selected, false otherwise
	 * @param dice2 True if the dice is selected, false otherwise
	 * @param dice3 True if the dice is selected, false otherwise
	 */
	public SetSelectionMessage(boolean dice1, boolean dice2, boolean dice3) {
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.dice3 = dice3;
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
		dataEngine.setSelection(dice1, dice2, dice3);
	}

}
