package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.InterfaceSingleThreadData;

public class RaiseExceptionMessage implements IMessage{

	private static final long serialVersionUID = 9221582005454257005L;
	private String errorMessage;
	private UUID user;
	
	public RaiseExceptionMessage(UUID user, String error) {
		this.user = user;
		this.errorMessage = error;
	}
	
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// dataEngine.raiseException(error);
	}

}
