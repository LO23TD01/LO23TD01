package network.messages;

import java.util.UUID;

import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

public class HasLostMessage implements IMessage{

    private static final long serialVersionUID = -5930391832092033148L;
    private UUID user;

    public HasLostMessage(UUID user) {
        this.user = user;
    }

    @Override
    public void process(InterfaceSingleThreadData dataEngine) {
        // TODO Auto-generated method stub

    }

    @Override
    public void process(InterfaceSingleThreadDataClient dataEngine) {
        dataEngine.hasLost(new User(new Profile(this.user)));
    }

}
