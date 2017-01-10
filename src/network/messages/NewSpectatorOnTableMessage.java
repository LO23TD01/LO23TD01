package network.messages;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

import org.hildan.fxgson.FxGson;

import data.Profile;
import data.User;

/**
 * Message to be sent when a spectator joins the table
 * @author lenovo
 *
 */
public class NewSpectatorOnTableMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private String userProfile;

    /**
     * Constructor
     * @param userProfile Profile of the new spectator
     */
    public NewSpectatorOnTableMessage(Profile userProfile) {
        this.userProfile = FxGson.create().toJson(userProfile);
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
        dataEngine.newSpectatorOnTable(new User(FxGson.create().fromJson(userProfile, Profile.class)));
    }

}
