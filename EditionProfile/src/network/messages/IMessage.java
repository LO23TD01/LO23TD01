package network.messages;

import java.io.Serializable;

public interface IMessage extends Serializable{
	
	public void process();
}
