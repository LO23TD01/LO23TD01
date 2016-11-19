package network.messages;

public class GetProfileMessage implements IMessage {

	private static final long serialVersionUID = 3379374689137731613L;

	@Override
	public void process() {
		System.out.println("Coucou, je suis un message de type GetProfile !");
	}

}
