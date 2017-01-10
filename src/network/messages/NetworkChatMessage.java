package network.messages;

import data.ChatMessage;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;

import java.util.List;
import java.util.UUID;

import org.hildan.fxgson.FxGson;

import com.sun.media.sound.FFT;

import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when a message is sent to the chat
 * @author lenovo
 *
 */
public class NetworkChatMessage implements IMessage{

	private static final long serialVersionUID = 1L;
	public String msg;

	/**
	 * Constructor
	 * @param msg Message to display in chat
	 */
	public NetworkChatMessage(ChatMessage msg){
		this.msg = FxGson.create().toJson(msg);
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		dataEngine.sendMessage(FxGson.create().fromJson(msg, ChatMessage.class));
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.writeMessage(FxGson.create().fromJson(msg, ChatMessage.class));
	}

}
