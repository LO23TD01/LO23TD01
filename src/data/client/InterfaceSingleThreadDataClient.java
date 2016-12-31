package data.client;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import data.ChatMessage;
import data.GameTable;
import data.Parameters;
import data.Profile;
import data.State;
import data.TurnState;
import data.User;
import data.UserRole;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import network.client.ComClientInterface;

public class InterfaceSingleThreadDataClient implements InterfaceDataNetwork {

	private ExecutorService es;
	private ClientDataEngine client;

	//le seul et unique constructeur appelée UNE SEULE FOIS (pour avoir qu'un seul thread hein;)
	public InterfaceSingleThreadDataClient(ClientDataEngine client)
	{
		this.es =  Executors.newSingleThreadExecutor();
		this.client = client;
	}
	
	@Override
	public void exAequoCase(List<User> users, boolean win) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.exAequoCase(users, win);
		    }
		});
	}

	@Override
	public void setCreator(UUID u) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.setCreator(u);
		    }
		});
	}

	@Override
	public void raiseException(String msg) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.raiseException(msg);
		    }
		});
	}

	@Override
	public void refreshUsersList(List<User> l) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.refreshUsersList(l);
		    }
		});
	}

	@Override
	public void updateUsersList(List<User> l) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.updateUsersList(l);
		    }
		});
	}

	@Override
	public void updateTablesList(List<GameTable> l) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.updateTablesList(l);
		    }
		});
	}

	@Override
	public void updateUsers(User u) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.updateUsers(u);
		    }
		});
	}

	@Override
	public void addNewTable(GameTable g) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.addNewTable(g);
		    }
		});
	}

	@Override
	public void sendTableInfo(GameTable g) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.sendTableInfo(g);
		    }
		});
	}

	@Override
	public void setSelection(boolean a, boolean b, boolean c) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.setSelection(a, b, c);
		    }
		});
	}

	@Override
	public void setDice(int a, int b, int c) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.setDice(a, b, c);
		    }
		});
	}

	@Override
	public void hasSelected(User u, boolean a, boolean b, boolean c) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.hasSelected(u, a, b, c);
		    }
		});
	}

	@Override
	public void hasThrown(User u, int a, int b, int c) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.hasThrown(u, a, b, c);
		    }
		});
	}

	@Override
	public void updateChips(User u, int a) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.updateChips(u, a);
		    }
		});
	}

	@Override
	public void updateChips(User u1, User u2, int a) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.updateChips(u1, u2, a);
		    }
		});
	}

	@Override
	public void startTurn() {
		es.execute(new Runnable() {
		    public void run() {
		    	client.startTurn();
		    }
		});
	}

	@Override
	public void isTurn(User u) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.isTurn(u);
		    }
		});
	}

	@Override
	public void stopGame(boolean a) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.stopGame(a);
		    }
		});
	}

	@Override
	public void askStopGame() {
		es.execute(new Runnable() {
		    public void run() {
		    	client.askStopGame();
		    }
		});
	}

	@Override
	public void playerQuitGame(User u) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.playerQuitGame(u);
		    }
		});
	}

	@Override
	public void hasAcceptedReplay(User u) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.hasAcceptedReplay(u);
		    }
		});
	}

	@Override
	public void hasRefusedReplay(User u) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.hasRefusedReplay(u);
		    }
		});
	}

	@Override
	public void hasWon(User u) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.hasWon(u);
		    }
		});
	}

	@Override
	public void hasLost(User u) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.hasLost(u);
		    }
		});
	}

	@Override
	public void showTimer() {
		es.execute(new Runnable() {
		    public void run() {
		    	client.showTimer();
		    }
		});
	}

	@Override
	public void newPlayerOnTable(User u) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.newPlayerOnTable(u);
		    }
		});
	}

	@Override
	public void newSpectatorOnTable(User u) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.newSpectatorOnTable(u);
		    }
		});
	}

	@Override
	public void kicked(String s) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.kicked(s);
		    }
		});
	}

	@Override
	public void changeState(State s) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.changeState(s);
		    }
		});
	}

	@Override
	public void changeTurnState(TurnState s) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.changeTurnState(s);
		    }
		});
	}

	@Override
	public void writeMessage(ChatMessage c) {
		es.execute(new Runnable() {
		    public void run() {
		    	client.writeMessage(c);
		    }
		});
	}
	
	public final ProfileManager getProfileManager() {
		return this.client.getProfileManager();
	}

	public final ObjectProperty<GameTable> actualTableProperty() {
		return this.client.actualTableProperty();
	}

	public final GameTable getActualTable() {
		return this.client.actualTableProperty().get();
	}

	public final void setActualTable(final GameTable actualTable) {
		this.client.actualTableProperty().set(actualTable);
	}

	public final ObjectProperty<UserRole> actualRoleProperty() {
		return this.client.actualRoleProperty();
	}

	public final UserRole getActualRole() {
		return this.client.actualRoleProperty().get();
	}

	public final void setActualRole(final UserRole actualRole) {
		this.client.actualRoleProperty().set(actualRole);
	}

	public ObservableList<User> getUserList() {
		return this.client.getUserList();
	}

	public void setUserList(List<User> users) {
		this.client.setUserList(users);
	}

	public ObservableList<GameTable> getTableList() {
		return this.client.getTableList();
	}

	public void setTableList(List<GameTable> gameTables) {

		this.client.setTableList(gameTables);
	}

	public ObservableList<Boolean> getSelectionList() {
		return this.client.getSelectionList();
	}

	public void setSelectionList(List<Boolean> selection) {
		this.client.setSelectionList(selection);
	}


	public ComClientInterface getComClientInterface() {
		return this.client.getComClientInterface();
	}

	public void setComClientInterface(ComClientInterface comClientInterface) {
		this.client.setComClientInterface(comClientInterface);
	}

	public final StringProperty voteTextProperty() {
		return this.client.voteTextProperty();
	}


	public final String getVoteText() {
		return this.client.getVoteText();
	}


	public final void setVoteText(final String voteText) {
		this.client.setVoteText(voteText);
	}

}
