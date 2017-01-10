package network.messages;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;

import org.hildan.fxgson.FxGson;

import data.Profile;
import data.User;

/**
 * Message to be sent when a new player joisn a table
 * @author lenovo
 *
 */
public class NewPlayerOnTableMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private String userProfile;

    /**
     * Constructor
     * @param userProfile Profile of the new player
     */
    public NewPlayerOnTableMessage(Profile userProfile) {
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
        dataEngine.newPlayerOnTable(new User(FxGson.create().fromJson(userProfile, Profile.class)));
    }

}
