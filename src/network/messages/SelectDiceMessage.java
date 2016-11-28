package network.messages;

import java.util.UUID;

import data.ClientDataEngine;
import data.ServerDataEngine;

public class SelectDiceMessage implements IMessage{

	private static final long serialVersionUID = 5658668394311606107L;
	
	private UUID player;
	private boolean dice1;
	private boolean dice2;
	private boolean dice3;
	
	public SelectDiceMessage(UUID player, boolean dice1, boolean dice2, boolean dice3) {
		this.player = player;
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.dice3 = dice3;
	}

	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.hasSelected(player, dice1, dice2, dice3);
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

}
