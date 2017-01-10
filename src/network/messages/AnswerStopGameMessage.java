package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when aswer wether the game must be stopped or not
 * @author lenovo
 *
 */
public class AnswerStopGameMessage implements IMessage {

	private static final long serialVersionUID = -2651256156377342660L;

    private UUID tableId;
    private boolean answer;
    private UUID user;

    /**
     * Constructor
     * @param tableId UUID of the table
     * @param answer True if the answer if yes, false otherwise
     * @param userID UUID of the user answering
     */
    public AnswerStopGameMessage(UUID tableId, boolean answer, UUID userID) {
    	this.tableId = tableId;
    	this.answer = answer;
    	this.user = userID;
    }

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		dataEngine.answerStopGame(tableId,answer,user);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {}

}
