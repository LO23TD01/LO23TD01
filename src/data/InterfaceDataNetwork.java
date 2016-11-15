package data;

import java.util.UUID;

public interface InterfaceDataNetwork {
	public Profile getProfile (User user);
	public void updateUserProfile (UUID uuid, Profile profile);
	public void sendMessage (ChatMessage message);
	public void dropTable (GameTable table);
	public void quit (User user, GameTable table);
	public void askJoinTable(User user, GameTable table, boolean isPlayer);
	public void launchGame (User user);
	public void createNewTable (User user, String name, Parameters params);
	public void disconnectUser (User user);
	public int[] hasThrown (UUID uuid, boolean d1, boolean d2, boolean d3);
	public void hasSelected(UUID uuid, boolean d1, boolean d2, boolean d3);
	public void connectUser(Profile profile);
	public void hasRefusedReplay (UUID uuid);
	public void hasAcceptedReplay (UUID uuid);
	public void askRefreshUsersList (User user);
}
