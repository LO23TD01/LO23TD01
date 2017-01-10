package network.messages;

import data.client.ClientDataEngine;
import data.server.ServerDataEngine;

import org.hildan.fxgson.FxGson;

import data.Profile;
import data.User;

/**
 * Message to be sent when a player joins a table
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
     * @see network.messages.IMessage#process(data.server.ServerDataEngine)
     */
    @Override
    public void process(ServerDataEngine dataEngine) {}

    /* (non-Javadoc)
     * @see network.messages.IMessage#process(data.client.ClientDataEngine)
     */
    @Override
    public void process(ClientDataEngine dataEngine) {
        dataEngine.newPlayerOnTable(new User(FxGson.create().fromJson(userProfile, Profile.class)));
    }

}
