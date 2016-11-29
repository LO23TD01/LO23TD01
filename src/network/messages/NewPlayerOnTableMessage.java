package network.messages;

import data.ClientDataEngine;
import data.ServerDataEngine;
import data.Profile;

public class NewPlayerOnTableMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private Profile userProfile;

    public NewPlayerOnTableMessage(Profile user) {
        this.userProfile = userProfile;
    }

    @Override
    public void process(ServerDataEngine dataEngine) {

    }

    @Override
    public void process(ClientDataEngine dataEngine) {
        //D�commenter quand clientDataEngine sera fini
        //dataEngine.newPlayerOnTable(this.userProfile);
    }

}