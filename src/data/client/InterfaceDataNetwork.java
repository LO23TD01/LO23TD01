package data.client;

import java.util.List;

import data.GameTable;
import data.State;
import data.TurnState;
import data.User;

public interface InterfaceDataNetwork {

	public void raiseException(String msg);

	public void refreshUsersList(List<User> l);

	public void updateUsersList(List<User> l);

	public void updateTablesList(List<GameTable> l);

	public void updateUsers(User u);

	public void addNewTable(GameTable g);

	public void sendTableInfo(GameTable g);

	public void setSelection(boolean a, boolean b, boolean c);

	public void setDice(int a, int b, int c);

	public void hasSelected(User u, boolean a, boolean b, boolean c);

	public void hasThrown(User u, int a, int b, int c);

	public void updateChips(User u, int a);

	public void updateChips(User u1, User u2, int a);

	public void startTurn();

	public void isTurn(User u);

	public void stopGame(boolean a);

	public void askStopGame();

	public void playerQuitGame(User u);

	public void hasAcceptedReplay(User u);

	public void hasRefusedReplay(User u);

	public void hasWon(User u);

	public void hasLost(User u);

	public void showTimer();

	public void newPlayerOnTable(User u);

	public void newSpectatorOnTable(User u);

	public void kicked(String s);

	public void changeState(State s) ;

	public void changeTurnState(TurnState s) ;

}
