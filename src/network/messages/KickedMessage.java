package network.messages;

import java.util.UUID;

import data.ClientDataEngine;
import data.ServerDataEngine;

public class KickedMessage implements IMessage {

	private static final long serialVersionUID = 6709711021661501877L;
	private String msg;
	
	public KickedMessage(String msg) {
		this.msg = msg;
	}
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// Appeler dataEngine.kicked(msg)
	}

}
