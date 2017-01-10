package network.messages;

import java.io.Serializable;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

/**
 * Messages
 * @author lenovo
 *
 */
public interface IMessage extends Serializable{

	/**
	 * Method being called when the message is received on the server
	 * @param dataEngine Engine on server side
	 */
	public void process(ServerDataEngine dataEngine);
	/**
	 * Method being called when the message is received on a client
	 * @param dataEngine Engine on client side
	 */
	public void process(ClientDataEngine dataEngine);
}
