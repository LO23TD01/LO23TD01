package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class KickedMessage implements IMessage {

	private static final long serialVersionUID = 6709711021661501877L;
	private String msg;
	
	public KickedMessage(String msg) {
		this.msg = msg;
	}
	
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.kicked(msg);
	}

}
