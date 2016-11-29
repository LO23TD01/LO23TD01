package network.messages;

import data.ClientDataEngine;
import data.ServerDataEngine;
import data.Profile;

public class NewSpectatorOnTableMessage implements IMessage {

    private static final long serialVersionUID = 3379374689137731613L;
    private Profile userProfile;

    public NewSpectatorOnTableMessage(Profile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public void process(ServerDataEngine dataEngine) {

    }

    @Override
    public void process(ClientDataEngine dataEngine) {
        //Décommenter quand clientDataEngine sera fini
        //dataEngine.newSpectatorOnTable(this.userProfile);
    }

}
