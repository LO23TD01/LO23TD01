package network.server;

import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;
import network.messages.IMessage;

public class ReplayMessage implements IMessage {

	private static final long serialVersionUID = -1691367847247708531L;

	@Override
	public void process(InterfaceSingleThreadData dataEngine) {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.replay();
	}

}
