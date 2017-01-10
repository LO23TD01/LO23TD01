package network.messages;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hildan.fxgson.FxGson;

import data.client.ClientDataEngine;
import data.client.InterfaceSingleThreadDataClient;
import data.server.InterfaceSingleThreadData;
import network.messages.utils.BufferedImageBuilder;
import data.User;

/**
 * Message to be sent when the list of users is refreshed
 * @author lenovo
 *
 */
public class refreshUserListMessage implements IMessage{

	private static final long serialVersionUID = 2537929372539873650L;

	private String usersToRefresh;
	byte[][] images;

	/**
	 * Constructor
	 * @param userList List of all users
	 */
	public refreshUserListMessage(List<User> userList) {
		images = new byte[userList.size()][];

		User[] users = userList.toArray(new User[0]);

		//Handle image serialization
		Image[] temps = new Image[userList.size()];

		for (User user : userList) {

			Image avatar = user.getPublicData().getAvatar();
			temps[userList.indexOf(user)] = avatar;

			if(avatar != null){
				images[userList.indexOf(user)] = BufferedImageBuilder.toByteArray(avatar);
				user.getPublicData().setAvatar(null);
			}else{
				images[userList.indexOf(user)] = null;
			}
		}

		this.usersToRefresh = FxGson.create().toJson(users);

		for (User user : userList) {
			user.getPublicData().setAvatar(temps[userList.indexOf(user)]);
		}
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
    	User[] users = FxGson.create().fromJson(usersToRefresh, User[].class);

    	for (int i = 0; i < users.length; i++) {
			//Converte bytes to Image and set the profile
	    	if(images[i] != null)
				users[i].getPublicData().setAvatar(BufferedImageBuilder.toImage(images[i]));
		}

    	dataEngine.refreshUsersList(Arrays.asList(users));
    }
}
