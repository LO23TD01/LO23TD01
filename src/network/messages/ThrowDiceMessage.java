package network.messages;

import java.util.UUID;

import data.ClientDataEngine;
import data.ServerDataEngine;

public class ThrowDiceMessage implements IMessage{

	private static final long serialVersionUID = 6487827669027387150L;

	private UUID user;
	private int d1;
	private int d2;
	private int d3;
	
	public ThrowDiceMessage(UUID user, int d1, int d2, int d3) {
		this.user = user;
		this.d1 = d1;
		this.d2 = d2;
		this.d3 = d3;
	}
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.hasThrown(user, d1, d2, d3);
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// TODO Auto-generated method stub
		
	}

}
