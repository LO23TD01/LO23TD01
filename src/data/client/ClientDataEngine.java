package data.client;

import java.util.List;
import java.util.UUID;

import data.Contact;
import data.ContactCategory;
import data.GameTable;
import data.IPData;
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

	private final ObjectProperty<ProfileManager> profileManager;
	private final ObservableList<User> userList = FXCollections.observableArrayList();
	private final ObservableList<GameTable> tableList = FXCollections.observableArrayList();
	private final ObjectProperty<GameTable> actualTable;
	private final ObjectProperty<UserRole> actualRole;
	/**
	 * Variable qui permet de communiquer avec le serveur, initialisÃ©e lors du login
	 */
	private ComClientInterface comClientInterface = null;

	/**
	 *
	 */
	public ClientDataEngine() {
		this.profileManager = new SimpleObjectProperty<ProfileManager>();
		this.actualTable = new SimpleObjectProperty<GameTable>();
		this.actualRole = new SimpleObjectProperty<UserRole>();
	}

	@Override
	public void throwDice(boolean a, boolean b, boolean c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectDice(boolean a, boolean b, boolean c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void launchGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void quitGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptReplay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refuseReplay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getListTable() {
		// TODO Auto-generated method stub

	}

	/**
	 * Cette mÃ©thode vÃ©rifie si le login/mot de passe est correct. Elle connecte ensuite le client au serveur de jeu.
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
		// TODO Auto-generated method stub

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

	}

	@Override
	public void askJoinTable(GameTable g, boolean b) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void getProfileFromOtherUser(User other) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void raiseException(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshUsersList(List<User> l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUsersList(List<User> l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTablesList(List<GameTable> l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUsers(User u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUserProfile(User u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendTableInfo(GameTable g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSelection(boolean a, boolean b, boolean c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDice(int a, int b, int c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasSelected(User u, boolean a, boolean b, boolean c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasThrown(User u, int a, int b, int c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateChips(User u, int a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateChips(User u1, User u2, int a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void isTurn(User u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopGame(boolean a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void askStopGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerQuitGame(User u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasAcceptedReplay(User u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasRefusedReplay(User u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasWon(User u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showTimer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void newPlayerOnTable(User u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newSpectatorOnTable(User u) {
		// TODO Auto-generated method stub

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
