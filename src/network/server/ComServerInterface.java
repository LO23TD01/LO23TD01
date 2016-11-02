package network.server;

import java.util.List;
import java.util.UUID;

import mocks.GameTable;
import mocks.Profile;

public interface ComServerInterface {
	
	public void sendResult(List<UUID> receivers, int r1, int r2, int r3);
	public void sendMessage(List<UUID> receivers, String senderLogin, String msg);
	public void showTimer(UUID user);
	public void addNewTable(List<UUID> receivers, GameTable tableinfo);
	public void sendSelection(List<UUID> receivers, UUID player, boolean d1, boolean d2, boolean d3);
	public void updateChips(List<UUID> receivers, UUID player, int nb);
	public void updateChips(List<UUID> receivers, UUID win, UUID lose, int nb);
	public void hasWon(List<UUID> receivers, UUID winner);
	public void startTurn(List<UUID> receivers, UUID player, boolean isLastLaunch);
	public void sendProfileUpdate(List<UUID> receivers, UUID userUpdated, Profile data);
	public void kick(List<UUID> receivers, String msg);
	public void askStopGameEveryUser(List<UUID> receivers);
	public void stopGameAccepted(List<UUID> receivers);
	public void refreshUserList(UUID user, List<UUID> receivers);
	public void raiseException(UUID user, String msg);
	public void newPlayerOnTable(List<UUID> receivers, Profile user, UUID tableID);
	public void newSpectatorOnTable(List<UUID> receivers, Profile user, UUID tableID);
	public void hasAccepted(UUID user,List<UUID> receivers);
	public void hasRefused(UUID user,List<UUID> receivers);
	public void newUser(List<UUID> receivers, Profile user);
}
