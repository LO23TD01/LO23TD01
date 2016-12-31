package network.messages;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

import org.hildan.fxgson.FxGson;

import data.Profile;
import data.User;

public class NewPlayerOnTableMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private String userProfile;

    public NewPlayerOnTableMessage(Profile userProfile) {
        this.userProfile = FxGson.create().toJson(userProfile);
    }

    @Override
    public void process(InterfaceSingleThreadData dataEngine) {

    }

    @Override
    public void process(InterfaceSingleThreadDataClient dataEngine) {
        dataEngine.newPlayerOnTable(new User(FxGson.create().fromJson(userProfile, Profile.class)));
    }

}
