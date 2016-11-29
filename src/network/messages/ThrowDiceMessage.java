package network.messages;

import java.util.UUID;

import data.ClientDataEngine;
import data.server.ServerDataEngine;

public class ThrowDiceMessage implements IMessage{

	private static final long serialVersionUID = 6487827669027387150L;

	private UUID user;
	private boolean d1;
	private boolean d2;
	private boolean d3;
	
	public ThrowDiceMessage(UUID user, boolean d1, boolean d2, boolean d3) {
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
