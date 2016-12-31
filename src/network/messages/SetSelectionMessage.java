package network.messages;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class SetSelectionMessage implements IMessage {

	private static final long serialVersionUID = 7929594584292402451L;

	private boolean dice1;
	private boolean dice2;
	private boolean dice3;
	
	public SetSelectionMessage(boolean dice1, boolean dice2, boolean dice3) {
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.dice3 = dice3;
	}

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.setSelection(dice1, dice2, dice3);
	}

}
