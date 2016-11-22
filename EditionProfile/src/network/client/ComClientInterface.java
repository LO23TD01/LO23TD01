package network.client;

import java.util.UUID;

import data.Profile;

public interface ComClientInterface {
	
	public void connection(Profile user);
	public void throwDice(UUID user);
	public void sendMessage(String msg);
	public void launchGame(UUID user);
	public void createNewTable(UUID user, String name, String pwd, int min, int max, int token, boolean withSpec, boolean withChat);
	public void selectDice(UUID user, boolean d1, boolean d2, boolean d3);
	public void updateUserProfile(UUID user, Profile profile);
	public void dropTable(UUID tableId);
	public void quit(UUID user);
	public void updateUsersList(UUID user);
	public void askQuitTable(UUID tableId, UUID user);
	public void askRefreshUsersList(UUID user);
	public void askJoinTable(UUID user, UUID tableId, boolean asPlayer);
	public void acceptReplay(UUID user);
	public void refuseReplay(UUID user);
	public void logoutUserRequest(UUID user);
	public void getProfile(UUID user);
}
