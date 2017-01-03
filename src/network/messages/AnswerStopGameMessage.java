package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Message communicating the answer given when receiving an AskStopGameMessage
 * @author lenovo
 */
public class AnswerStopGameMessage implements IMessage {

	private static final long serialVersionUID = -2651256156377342660L;

    private UUID tableId;
    private boolean answer;
    private UUID user;

    /**
     * Constructor
     * @param tableId UUID of the table
     * @param answer Answer given
     * @param userID UUID of the answering user
     */
    public AnswerStopGameMessage(UUID tableId, boolean answer, UUID userID) {
    	this.tableId = tableId;
    	this.answer = answer;
    	this.user = userID;
    }

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {
		// Appeler :
		// dataEngine.answerStopGame(tableId,answer,user);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {}

}
