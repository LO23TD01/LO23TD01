package network.messages;

import data.ChatMessage;
import data.client.ClientDataEngine;

import java.util.List;
import java.util.UUID;

import data.server.ServerDataEngine;

public class NetworkChatMessage implements IMessage{
	
	private static final long serialVersionUID = 1L;
	public ChatMessage msg;
	
	public NetworkChatMessage(ChatMessage msg){
		this.msg = msg;
	}
	
	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.sendMessage(msg);
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// En attente de DATA : issue #126
		//dataEngine.writeMessage(msg);
	}

}