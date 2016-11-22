package network.messages;

import java.io.Serializable;

import data.ClientDataEngine;
import data.ServerDataEngine;

public interface IMessage extends Serializable{
	
	public void process(ServerDataEngine dataEngine);
	public void process(ClientDataEngine dataEngine);
}
