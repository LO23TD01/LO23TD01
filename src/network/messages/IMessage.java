package network.messages;

import java.io.Serializable;

import data.client.ClientDataEngine;
import data.server.InterfaceSingleThreadData;

public interface IMessage extends Serializable{

	public void process(InterfaceSingleThreadData dataEngine);
	public void process(ClientDataEngine dataEngine);
}
