package data.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import data.Contact;
import data.ContactCategory;
import data.GameTable;
import data.IPData;
import data.PlayerData;
import data.Profile;
import data.User;
import data.UserRole;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import network.client.ComClient;
import network.client.ComClientInterface;

public class ClientDataEngine implements InterfaceDataIHMLobby, InterfaceDataIHMTable, InterfaceDataNetwork {

	private final ObjectProperty<ProfileManager> profileManager = new SimpleObjectProperty<ProfileManager>();
	private final ObservableList<User> userList = FXCollections.observableArrayList();
	private final ObservableList<GameTable> tableList = FXCollections.observableArrayList();
	private final ObjectProperty<GameTable> actualTable;
	private final ObjectProperty<UserRole> actualRole;
	/**
	 * Variable qui permet de communiquer avec le serveur, initialisée lors du login
	 */
	private ComClientInterface comClientInterface = null;

	/**
	 * 
	 */
	public ClientDataEngine() {
		super();
		this.profileManager.set(new ProfileManager());;
		this.actualTable = null;
		this.actualRole = null;
	}

	@Override
	public void throwDice(boolean a, boolean b, boolean c) {
		comClientInterface.throwDice(profileManager.get().getCurrentProfile().getUUID(), a, b, c);

	}

	@Override
	public void selectDice(boolean a, boolean b, boolean c) {
		comClientInterface.selectDice(profileManager.get().getCurrentProfile().getUUID(), a, b, c);
	}

	@Override
	public void launchGame() {
		comClientInterface.launchGame(profileManager.get().getCurrentProfile().getUUID());

	}

	@Override
	public void quitGame() {
		comClientInterface.quit(profileManager.get().getCurrentProfile().getUUID());

	}

	@Override
	public void sendMessage(String msg) {
		comClientInterface.sendMessage(msg);
	}

	@Override
	public void acceptReplay() {
		comClientInterface.acceptReplay(profileManager.get().getCurrentProfile().getUUID());
	}

	@Override
	public void refuseReplay() {
		comClientInterface.refuseReplay(profileManager.get().getCurrentProfile().getUUID());
	}

	@Override
	public void getListTable() {
		// TODO Auto-generated method stub

	}

	/**
	 * Cette méthode vérifie si le login/mot de passe est correct. Elle connecte ensuite le client au serveur de jeu.
	 * 
	 * @param login
	 * @param password
	 * @param ipd
	 * @throws Exception
	 */
	@Override
	public void login(String login, String password, IPData ipd) throws Exception {
		if (!profileManager.get().checkPassword(login, password))
			throw new Exception("Mauvais mot de passe");
		comClientInterface = new ComClient(ipd.getValue(), 4000);
		// TODO choisir un port
		comClientInterface.connection(this.getLocalProfile(login, password));
	}

	@Override
	public void logout() {
		comClientInterface.logoutUserRequest(profileManager.get().getCurrentProfile().getUUID());
		profileManager.get().logout(getLocalProfile());
		this.tableList.clear();
		this.userList.clear();
	}

	@Override
	public void createProfile(Object... args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getTableInfo(GameTable g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNewTable(GameTable g) {
		// TODO Auto-generated method stub
		// A voir avec la team IHM Lobby
	}

	@Override
	public void askJoinTable(GameTable g, boolean b) {
		comClientInterface.askJoinTable(this.getProfileManager().getCurrentProfile().getUUID(), g.getUid(), b);
	}

	@Override
	public Profile getLocalProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profile getLocalProfile(String login, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profile getLocalProfile(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profile changeMyProfile(Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getListUsers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void askRefreshUsersList() {
		comClientInterface.askRefreshUsersList(this.getProfileManager().getCurrentProfile().getUUID());
	}

	@Override
	public void getProfileFromOtherUser(User other) {
		comClientInterface.getProfile(other.getPublicData().getUUID(), this.getProfileManager().getCurrentProfile().getUUID());
	}

	@Override
	public boolean addContact(UUID uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteContact(UUID uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Contact> getContactList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addCategory(String name, Object... rights) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCategory(UUID uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ContactCategory> getCategoryList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modifyCategory(UUID uuid, String name, Object... rights) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addContactToCategory(UUID uuidContact, UUID uuidCategory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeContactFromCategory(UUID uuidContact, UUID uuidCategory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserRole getUserRole() {
		return this.actualRole.get();
	}

	@Override
	public void raiseException(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshUsersList(List<User> l) {
		userList.setAll(l);
	}

	@Override
	public void updateUsersList(List<User> l) {
		//Appeler lorsqu'un nouveau joueur se connecte
		//Action similaire � refreshUsersList mais dans le doute d'une autre utilit� je laisse les deux
		userList.setAll(l);
	}

	@Override
	public void updateTablesList(List<GameTable> l) {
		tableList.setAll(l);
	}

	@Override
	public void updateUsers(User u) {
		userList.add(u);
	}

	@Override
	public void updateUserProfile(User u) {
		comClientInterface.updateUserProfile(u.getPublicData().getUUID(), u.getPublicData());

	}

	@Override
	public void sendTableInfo(GameTable g) {
		actualTable.set(g);
	}

	@Override
	public void setSelection(boolean a, boolean b, boolean c) {
		//TODO : Pas s�r de ce qu'il faut modifier dans le mod�le de donn�es
	}

	@Override
	public void setDice(int a, int b, int c) {
		int[] dices = {a,b,c};
		getActualTable().getGameState().getData(getActualTable().getGameState().getActualPlayer(), false).setDices(dices);
	}

	@Override
	public void hasSelected(User u, boolean a, boolean b, boolean c) {
		//TODO : Pas s�r de ce qu'il faut modifier dans le mod�le de donn�es
	}

	@Override
	public void hasThrown(User u, int a, int b, int c) {
		int[] dices = {a,b,c};
		getActualTable().getGameState().getData(u, false).setDices(dices);
	}

	@Override
	public void updateChips(User u, int a) {
		getActualTable().getGameState().getData(u, false).setChip(a);
	}

	@Override
	public void updateChips(User u1, User u2, int a) {
		PlayerData p1 = actualTable.get().getGameState().getData(u1, false);
		PlayerData p2 = actualTable.get().getGameState().getData(u2, false);
		
		p1.setChip(p1.getChip()-a);
		p2.setChip(p2.getChip()+a);
	}

	@Override
	public void startTurn() {
		// TODO : V�rifier que ce soit la bonne m�thode appel�e car il y a aussi GameState.nextTurn(User)
		//
		User currentUser = new User(getProfileManager().getCurrentProfile());
		getActualTable().getGameState().getData(currentUser, false).newTurn();
	}

	@Override
	public void isTurn(User u) {
		// TODO : Idem m�thode pr�c�dente v�rifier que ce soit la bonne m�thode appel�e
		//
		getActualTable().getGameState().getData(u, false).newTurn();
	}

	@Override
	public void stopGame(boolean a) {
		// TODO : M�thode stopGame non impl�ment�e : En attente ? ou Une autre m�thode est � appeler ?
		//
		getActualTable().stopGame();
	}

	@Override
	public void askStopGame() {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : askStopGame()
	}

	@Override
	public void playerQuitGame(User u) {
		getActualTable().disconnect(u);
	}

	@Override
	public void hasAcceptedReplay(User u) {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : updateViewReplay(UUID)
	}

	@Override
	public void hasRefusedReplay(User u) {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : removeViewReplay(UUID)
	}

	@Override
	public void hasWon(User u) {
		getActualTable().getGameState().setWinnerGame(u);
	}

	@Override
	public void showTimer() {
		//TODO : A v�rifier qu'il n'y ai pas d'autres action � faire
		getActualTable().initializeGame();
	}

	@Override
	public void newPlayerOnTable(User u) {
		getActualTable().connect(u, false);
	}

	@Override
	public void newSpectatorOnTable(User u) {
		getActualTable().connect(u, true);
	}

	public final ObjectProperty<ProfileManager> profileManagerProperty() {
		return this.profileManager;
	}

	public final ProfileManager getProfileManager() {
		return this.profileManagerProperty().get();
	}

	public final void setProfileManager(final ProfileManager profileManager) {
		this.profileManagerProperty().set(profileManager);
	}

	public final ObjectProperty<GameTable> actualTableProperty() {
		return this.actualTable;
	}

	public final GameTable getActualTable() {
		return this.actualTableProperty().get();
	}

	public final void setActualTable(final GameTable actualTable) {
		this.actualTableProperty().set(actualTable);
	}

	public final ObjectProperty<UserRole> actualRoleProperty() {
		return this.actualRole;
	}

	public final UserRole getActualRole() {
		return this.actualRoleProperty().get();
	}

	public final void setActualRole(final UserRole actualRole) {
		this.actualRoleProperty().set(actualRole);
	}

	public ObservableList<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> users) {
		this.userList.clear();
		this.userList.addAll(users);
	}

	public ObservableList<GameTable> getTableList() {
		return tableList;
	}

	public void setTableList(List<GameTable> gameTables) {
		this.tableList.clear();
		this.tableList.addAll(gameTables);
	}

}
