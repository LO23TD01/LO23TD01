package network.messages;

import data.client.ClientDataEngine;
import data.server.InterfaceSingleThreadData;

import org.hildan.fxgson.FxGson;

import data.Profile;
import data.User;

public class NewSpectatorOnTableMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private String userProfile;

    public NewSpectatorOnTableMessage(Profile userProfile) {
        this.userProfile = FxGson.create().toJson(userProfile);
    }

    @Override
    public void process(InterfaceSingleThreadData dataEngine) {

    }

    @Override
    public void process(ClientDataEngine dataEngine) {
        dataEngine.newSpectatorOnTable(new User(FxGson.create().fromJson(userProfile, Profile.class)));
    }

}
