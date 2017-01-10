package network.messages;

import data.ChatMessage;
import data.client.ClientDataEngine;

import java.util.List;
import java.util.UUID;

import org.hildan.fxgson.FxGson;

import com.sun.media.sound.FFT;

import data.server.ServerDataEngine;

/**
 * Message to be sent when someone chats
 * @author lenovo
 *
 */
public class NetworkChatMessage implements IMessage{

	private static final long serialVersionUID = 1L;
	public String msg;

	/**
	 * Constructor
	 * @param msg Message to be sent to the chat
	 */
	public NetworkChatMessage(ChatMessage msg){
		this.msg = FxGson.create().toJson(msg);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.ServerDataEngine)
	 */
	@Override
	public void process(ServerDataEngine dataEngine) {
		dataEngine.sendMessage(FxGson.create().fromJson(msg, ChatMessage.class));
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.ClientDataEngine)
	 */
	@Override
	public void process(ClientDataEngine dataEngine) {
		// En attente de DATA : issue #126
		//dataEngine.writeMessage(msg);
	}

}
