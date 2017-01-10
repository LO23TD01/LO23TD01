package network.messages;

import java.io.Serializable;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message
 * @author lenovo
 *
 */
public interface IMessage extends Serializable{

	/**
	 * Method called when the message is being received by the other end
	 * @param dataEngine
	 */
	public void process(InterfaceSingleThreadData dataEngine);

	/**
	 * Method called when the message is being received by the other end
	 * @param dataEngine
	 */
	public void process(InterfaceSingleThreadDataClient dataEngine);
}
