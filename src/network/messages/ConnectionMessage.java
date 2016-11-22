package network.messages;

import data.Profile;

public class ConnectionMessage implements IMessage{

    private static final long serialVersionUID = -2428194153289587089L;

    Profile profile;

    public ConnectionMessage(Profile p) {
        profile = p;
    }

    @Override
    public void process() {}

}
