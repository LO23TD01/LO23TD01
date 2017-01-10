package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

/**
 * Message to be sent when a user has thrown some die
 * @author lenovo
 *
 */
public class HasThrownMessage implements IMessage{

	private static final long serialVersionUID = -502547680784512937L;

	private UUID user;
	private int result1;
	private int result2;
	private int result3;

	/**
	 * Constructor
	 * @param user UUID of the throwing player
	 * @param res1 Result of the first dice
	 * @param res2 Result of the second dice
	 * @param res3 Result of the third dice
	 */
	public HasThrownMessage(UUID user, int res1, int res2, int res3) {
		this.user = user;
		this.result1 = res1;
		this.result2 = res2;
		this.result3 = res3;
	}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.server.InterfaceSingleThreadData)
	 */
	@Override
	public void process(InterfaceSingleThreadData dataEngine) {}

	/* (non-Javadoc)
	 * @see network.messages.IMessage#process(data.client.InterfaceSingleThreadDataClient)
	 */
	@Override
	public void process(InterfaceSingleThreadDataClient dataEngine) {
		dataEngine.hasThrown(new User(new Profile(user)), this.result1, this.result2, this.result3);
	}

}
