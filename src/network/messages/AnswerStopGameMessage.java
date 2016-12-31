package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class AnswerStopGameMessage implements IMessage {

	private static final long serialVersionUID = -2651256156377342660L;

    private UUID tableId;
    private boolean answer;
    private UUID user;
    
    public AnswerStopGameMessage(UUID tableId, boolean answer, UUID userID) {
    	this.tableId = tableId;
    	this.answer = answer;
    	this.user = userID;
    }
	
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// Appeler :
		// dataEngine.answerStopGame(tableId,answer,user);
	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		// TODO Auto-generated method stub

	}

}
