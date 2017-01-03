package network.messages;

import java.util.UUID;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

public class AskStopGameMessage implements IMessage {

	private static final long serialVersionUID = 3413708401686105382L;

	public AskStopGameMessage() {
    }

	@Override
	public void process(ServerDataEngine dataEngine) {}

	@Override
	public void process(ClientDataEngine dataEngine) {
		dataEngine.askStopGame();
	}

}
