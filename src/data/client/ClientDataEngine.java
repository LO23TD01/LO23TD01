package data.client;

import java.util.List;
import java.util.UUID;

import data.Contact;
import data.ContactCategory;
import data.GameTable;
import data.IPData;
import data.Profile;
import data.Rights;
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
	 * Variable qui permet de communiquer avec le serveur, initialisée lors du
	 * login
	 */
	private ComClientInterface comClientInterface = null;

	/**
	 *
	 */
	public ClientDataEngine() {
		super();
		this.profileManager.set(new ProfileManager());
		;
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
	 * Cette méthode vérifie si le login/mot de passe est correct. Elle connecte
	 * ensuite le client au serveur de jeu.
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
		comClientInterface.createNewTable(this.getProfileManager().getCurrentProfile().getUUID(), g.getName(), null,
				g.getParameters().getNbPlayerMin(), g.getParameters().getNbPlayerMax(), g.getParameters().getNbChip(),
				g.getParameters().isAuthorizeSpec(), g.getParameters().isAuthorizeSpecToChat(),
				g.getParameters().getRules());
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
		comClientInterface.updateUsersList(this.getProfileManager().getCurrentProfile().getUUID());

	}

	@Override
	public void askRefreshUsersList() {
		comClientInterface.askRefreshUsersList(this.getProfileManager().getCurrentProfile().getUUID());
	}

	@Override
	public void getProfileFromOtherUser(User other) {
		comClientInterface.getProfile(other.getPublicData().getUUID(),
				this.getProfileManager().getCurrentProfile().getUUID());
	}

	@Override
	public boolean addContact(UUID uuid) {
		return this.getProfileManager().getCurrentProfile().getClient()
				.addContact(new Contact(uuid, null, null, null, 0));
	}

	@Override
	public boolean deleteContact(UUID uuid) {
		return this.getProfileManager().getCurrentProfile().getClient().removeContact(uuid);
	}

	@Override
	public List<Contact> getContactList() {
		return this.getProfileManager().getCurrentProfile().getClient().getContactList();
	}

	@Override
	public boolean addCategory(String name, Object... rights) {
		return this.getProfileManager().getCurrentProfile().getClient().addCategory(name, (Rights) rights[0]);
	}

	@Override
	public boolean deleteCategory(UUID uuid) {
		return this.getProfileManager().getCurrentProfile().getClient().deleteCategoryByUuid(uuid);
	}

	@Override
	public List<ContactCategory> getCategoryList() {
		return this.getProfileManager().getCurrentProfile().getClient().getCategoryList();
	}

	@Override
	public boolean modifyCategory(UUID uuid, String name, Object... rights) {
		return this.getProfileManager().getCurrentProfile().getClient().modifyCategory(uuid, name, (Rights) rights[0]);
	}

	@Override
	public boolean addContactToCategory(UUID uuidContact, UUID uuidCategory) {
		return this.getProfileManager().getCurrentProfile().getClient().addContactToCategory(uuidContact, uuidCategory);
	}

	@Override
	public boolean removeContactFromCategory(UUID uuidContact, UUID uuidCategory) {
		return this.getProfileManager().getCurrentProfile().getClient().removeContactFromCategory(uuidContact,
				uuidCategory);
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
		// TODO Auto-generated method stub
		// A voir avec IHM Main (observer)
	}

	@Override
	public void updateUsersList(List<User> l) {
		// TODO Auto-generated method stub
		// A voir avec IHM Main (observer)
	}

	@Override
	public void updateTablesList(List<GameTable> l) {
		// TODO Auto-generated method stub
		// A voir avec IHM Main (observer)
	}

	@Override
	public void updateUsers(User u) {
		// TODO Auto-generated method stub
		// A voir avec IHM Main (observer)
	}

	@Override
	public void updateUserProfile(User u) {
		comClientInterface.updateUserProfile(u.getPublicData().getUUID(), u.getPublicData());

	}

	@Override
	public void sendTableInfo(GameTable g) {
		// TODO Auto-generated method stub
		// A voir avec IHM Main (observer)
	}

	@Override
	public void setSelection(boolean a, boolean b, boolean c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDice(int a, int b, int c) {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : showDice(1, 2, 3)
	}

	@Override
	public void hasSelected(User u, boolean a, boolean b, boolean c) {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : updateSelection(UUID, 1, 2, 3)
	}

	@Override
	public void hasThrown(User u, int a, int b, int c) {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : updateResult(UUID, 1, 2, 3)
	}

	@Override
	public void updateChips(User u, int a) {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : updateChips(Profile, nb)
	}

	@Override
	public void updateChips(User u1, User u2, int a) {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : updateChips(Profile_winner, Profile_loser,
		// nb)
	}

	@Override
	public void startTurn() {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : showDice()
	}

	@Override
	public void isTurn(User u) {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : showTurn(Profile)
	}

	@Override
	public void stopGame(boolean a) {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : showStopGame(a)
	}

	@Override
	public void askStopGame() {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : askStopGame()
	}

	@Override
	public void playerQuitGame(User u) {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : updateView()
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
		// TODO Auto-generated method stub
		// A voir avec IHM Table : gameWon() / hasWon(Profile)
	}

	@Override
	public void showTimer() {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : showTimer()
	}

	@Override
	public void newPlayerOnTable(User u) {
		// TODO Auto-generated method stub
		// A voir avec IHM Table : newPlayerOnTable(Profile)
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
