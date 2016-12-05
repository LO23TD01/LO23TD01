package network.client;

import java.util.UUID;

import data.GameTable;
import data.Profile;
import data.Rules;

public interface ComClientInterface {
	
	public void connection(Profile user);
	public void throwDice(UUID user, boolean d1, boolean d2, boolean d3);
	public void sendMessage(String msg);
	public void launchGame(UUID user);
	public void createNewTable(UUID user, String name, String pwd, int min, int max, int token, boolean withSpec, boolean withChat, Rules rules);
	public void selectDice(UUID user, boolean d1, boolean d2, boolean d3);
	public void updateUserProfile(UUID user, Profile profile);
	//DropTable supprimé
	public void updateUsersList(UUID user);
	public void askQuitTable(UUID tableId, UUID user);
	public void askRefreshUsersList(UUID user);
	public void askJoinTable(UUID user, UUID tableId, boolean asPlayer);
	public void acceptReplay(UUID user);
	public void refuseReplay(UUID user);
	public void logoutUserRequest(UUID user);
	//Changement de l'interface pour recuperer le profile 
    //getProfile(UUID user) --> getProfile(UUID user, UUID sender)
	public void getProfile(UUID user, UUID sender);
	public void answerStopGame(UUID tableId, boolean answer, UUID user);
	public void quit(UUID user); // Pour phases d'avant/après la partie (BESOIN A VERIFIER PAR DATA)
	public void quit(UUID user, UUID tableId); // Pendant une partie
}
