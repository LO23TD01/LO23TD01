package data.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import data.Contact;
import data.ContactCategory;
import data.GameTable;
import data.IPData;
import data.Profile;
import data.User;
import data.UserRole;
import network.client.ComClient;
import network.client.ComClientInterface;

public class ClientDataEngine implements InterfaceDataIHMLobby, InterfaceDataIHMTable, InterfaceDataNetwork {

	private ProfileManager profileManager;
	private List<User> userList;
	private List<GameTable> tableList;
	private GameTable actualTable;
	private UserRole actualRole;
	/**
	 * Variable qui permet de communiquer avec le serveur, initialisée lors du login
	 */
	private ComClientInterface comClientInterface = null;

	/**
	 * 
	 */
	public ClientDataEngine() {
		super();
		this.profileManager = new ProfileManager();
		this.userList = new ArrayList<User>();
		this.tableList = new ArrayList<GameTable>();
		this.actualTable = null;
		this.actualRole = null;
	}

	/**
	 * @return the profileManager
	 */
	public ProfileManager getProfileManager() {
		return profileManager;
	}

	/**
	 * @param profileManager
	 *            the profileManager to set
	 */
	public void setProfileManager(ProfileManager profileManager) {
		this.profileManager = profileManager;
	}

	/**
	 * @return the userList
	 */
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * @param userList
	 *            the userList to set
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	/**
	 * @return the tableList
	 */
	public List<GameTable> getTableList() {
		return tableList;
	}

	/**
	 * @param tableList
	 *            the tableList to set
	 */
	public void setTableList(List<GameTable> tableList) {
		this.tableList = tableList;
	}

	/**
	 * @return the actualTable
	 */
	public GameTable getActualTable() {
		return actualTable;
	}

	/**
	 * @param actualTable
	 *            the actualTable to set
	 */
	public void setActualTable(GameTable actualTable) {
		this.actualTable = actualTable;
	}

	/**
	 * @return the actualRole
	 */
	public UserRole getActualRole() {
		return actualRole;
	}

	/**
	 * @param actualRole
	 *            the actualRole to set
	 */
	public void setActualRole(UserRole actualRole) {
		this.actualRole = actualRole;
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
	 * Cette méthode vérifie si le login/mot de passe est correct. Elle connecte ensuite le client au serveur de jeu.
	 * 
	 * @param login
	 * @param password
	 * @param ipd
	 * @throws Exception
	 */
	@Override
	public void login(String login, String password, IPData ipd) throws Exception {
		if (!profileManager.checkPassword(login, password))
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

}
