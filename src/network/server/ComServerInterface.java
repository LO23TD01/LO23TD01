package network.server;

import java.util.List;
import java.util.UUID;

import data.ChatMessage;
import data.GameTable;
import data.Profile;
import data.User;

public interface ComServerInterface {

	public void sendResult(List<UUID> receivers, int r1, int r2, int r3);
	public void sendMessage(List<UUID> receivers, ChatMessage msg);
	public void showTimer(List<UUID> receivers); // ajouté List<UUID> au lieu de UUID : corrigé avec le diagramme de sequence "Commencer Partie"
	public void addNewTable(UUID receiver,List<UUID> receivers, GameTable tableinfo); // ajouté l'UUID du receiver unique
	public void sendSelection(List<UUID> receivers, UUID player, boolean d1, boolean d2, boolean d3);
	public void updateChips(List<UUID> receivers, UUID player, int nb);
	public void updateChips(List<UUID> receivers, UUID win, UUID lose, int nb);
	public void hasWon(List<UUID> receivers, UUID winner);
	public void startTurn(List<UUID> receivers, UUID player, boolean isLastLaunch);
	public void sendProfileUpdate(List<UUID> receivers, UUID userUpdated, Profile data); // plus necessaire (changement lié au professeur et à "modifier profil")
	public void sendProfile(UUID receivers, Profile data);
	public void kick(List<UUID> receivers, String msg);
	public void askStopGameEveryUser(List<UUID> receivers);
	public void stopGameAccepted(List<UUID> receivers);
	public void refreshUserList(UUID user, List<User> userList);
	public void raiseException(UUID user, String msg);
	public void raiseException(List<UUID> receivers, String msg);
	public void newPlayerOnTable(List<UUID> receivers, Profile user, GameTable tableInfo);
	public void newSpectatorOnTable(List<UUID> receivers, Profile user, GameTable tableInfo);
	public void hasAccepted(UUID user,List<UUID> receivers);
	public void hasRefused(UUID user,List<UUID> receivers);
	public void newUser(List<UUID> receivers, Profile user);
	public void sendTablesUsers(List<User> userList, List<GameTable> tableList, Profile user);
	public void playerQuitGame(List<UUID> receivers, UUID user);
	public void stopGame(List<UUID> receivers, boolean answer);
	public void hasLost(List<UUID> receivers, UUID winner);
}
