package network.messages;

import data.ChatMessage;
import data.client.ClientDataEngine;

import java.util.List;
import java.util.UUID;

import org.hildan.fxgson.FxGson;

import com.sun.media.sound.FFT;

import data.server.InterfaceSingleThreadData;

public class NetworkChatMessage implements IMessage{
	
	private static final long serialVersionUID = 1L;
	public String msg;
	
	public NetworkChatMessage(ChatMessage msg){
		this.msg = FxGson.create().toJson(msg);
	}
	
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		dataEngine.sendMessage(FxGson.create().fromJson(msg, ChatMessage.class));
	}

	@Override
	public void process(ClientDataEngine dataEngine) {
		// En attente de DATA : issue #126
		//dataEngine.writeMessage(msg);
	}

}
