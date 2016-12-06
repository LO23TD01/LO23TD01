package data.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import data.Contact;
import data.ContactCategory;
import data.GameTable;
import data.IPData;
import data.PlayerData;
import data.Profile;
import data.Rights;
import data.User;
import data.UserRole;
import data.Vote;
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
	private final ObservableList<Boolean> selectionList = FXCollections.observableArrayList();
	/**
	 * Variable qui permet de communiquer avec le serveur, initialisÃ©e lors du login
	 */
	private ComClientInterface comClientInterface = null;

	/**
	 * 
	 */
	public ClientDataEngine() {
		super();
		this.profileManager.set(new ProfileManager());
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
		//	A voir avec team Reseau, pas de mÃ©thode quit(user) sur serverDataEngine
		//comClientInterface.quit(profileManager.get().getCurrentProfile().getUUID(), getActualTable().getUid());

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
		comClientInterface.logoutUserRequest(profileManager.get().getCurrentProfile().getUUID());
		profileManager.get().logout(getLocalProfile());
		this.tableList.clear();
		this.userList.clear();
	}

	@Override
	// Cette fonction est appelée par IHM lobby lors de la création d'un profil c'est pour cela que l'on xmlise ce profile.
	public void createProfile(String login, String psw) {
		getProfileManager().createProfile(login,psw).Xmlise();
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
		return this.getProfileManager().getCurrentProfile();
	}

	@Override
	public Profile getLocalProfile(String login, String password) {
		return this.getProfileManager().getLocalProfile(login,password);
	}

	@Override
	public Profile getLocalProfile(UUID id) {
		return this.getProfileManager().getLocalProfile(id);
	}

	@Override
	// Cette fonction est appelée par IHM lobby lors de la modification d'un profil c'est pour cela que l'on xmlise ce profile.
	public Profile changeMyProfile(Profile new_profile) {
		// Si jamais l'utilisateur veut changer son mdp/login on supprime l'ancien profile XML et on en crée un nouveau.
		if (getLocalProfile().getLogin() != new_profile.getLogin() || getLocalProfile().getPsw() != new_profile.getPsw()){
			String path = "MesProfiles\\"+getLocalProfile().getLogin()+"-"+getLocalProfile().getPsw()+".xml";
			File file = new File(path);
			file.delete();
		}
		Profile p = this.getProfileManager().modifyProfile(getLocalProfile(), new_profile);
		p.Xmlise();
		return p;
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
		this.userList.setAll(l);
	}

	@Override
	public void updateUsersList(List<User> l) {
		//Appeler lorsqu'un nouveau joueur se connecte
		//Action similaire à refreshUsersList mais dans le doute d'une autre utilité je laisse les deux
		this.userList.setAll(l);
	}

	@Override
	public void updateTablesList(List<GameTable> l) {
		this.tableList.setAll(l);
	}

	@Override
	public void updateUsers(User u) {
		User userFound = u.getSame(this.userList);
		if(userFound !=null)
			this.userList.set(this.userList.indexOf(userFound), u);
		else
			this.userList.add(u);
	}

	@Override
	public void addNewTable(GameTable g) {
		if(g.getSame(this.tableList)==null)
			this.tableList.add(g);
//		else
//			throw new Exception("Erreur reception table. Table déjà existante.");
	}

	@Override
	public void sendTableInfo(GameTable g) {
		if(this.actualTable==null)
			this.actualTable.set(g);
		else if(g.isSame(this.getActualTable())) // on remplace la table qui doit contenir de nouvelles informations
			this.actualTable.set(g);
//		else
//			throw new Exception("Erreur reception table. On est déjà à une table");
	}

	@Override
	public void setSelection(boolean a, boolean b, boolean c) {
		//TODO : Voir avec IHM

		//fix proposé
		List<Boolean>  newList = new ArrayList<Boolean>();
		newList.add(a);
		newList.add(b);
		newList.add(c);
		this.setSelectionList(newList);
	}

	//Attention perte d'information ici
	//Le modèle du serveur contient plus d'information que le modèle client aprsè cette fonction.
	//TOREVIEW : p-être renvoyer la table complète pour eviter ce problème.
	//TOREVIEW : autre solution, faire environ 500 lignes de codes pour retro enginneerer l'état.
	@Override
	public void setDice(int a, int b, int c) {
		int[] dices = {a,b,c};
		getActualTable().getGameState().getData(new User(this.getProfileManager().getCurrentProfile()), false).setDices(dices); //On check avec soi-meme.

		//fix pour la Selection
		List<Boolean>  newList = new ArrayList<Boolean>();
		newList.add(false);
		newList.add(false);
		newList.add(false);
		this.setSelectionList(newList);
	}

	@Override
	public void hasSelected(User u, boolean a, boolean b, boolean c) {
		//TODO : Voir avec IHM

		//fix proposé
		List<Boolean>  newList = new ArrayList<Boolean>();
		newList.add(a);
		newList.add(b);
		newList.add(c);
		this.setSelectionList(newList);

	}


	//Attention perte d'information ici
	//TOREVIEW : voir setDices
	@Override
	public void hasThrown(User u, int a, int b, int c) {
		int[] dices = {a,b,c};
		getActualTable().getGameState().getData(u, false).setDices(dices);

		//fix pour la Selection
		List<Boolean>  newList = new ArrayList<Boolean>();
		newList.add(false);
		newList.add(false);
		newList.add(false);
		this.setSelectionList(newList);
	}

	//c'est une addition ici
	@Override
	public void updateChips(User u, int a) {
		PlayerData p1 = actualTable.get().getGameState().getData(u, false);
		p1.setChip(p1.getChip()+a);
		getActualTable().getGameState().nextTurn(u); //ne pas oublier de passer au prochain tour.
	}

	@Override
	public void updateChips(User u1, User u2, int a) {
		PlayerData p1 = actualTable.get().getGameState().getData(u1, false);
		PlayerData p2 = actualTable.get().getGameState().getData(u2, false);
		
		p1.setChip(p1.getChip()-a);
		p2.setChip(p2.getChip()+a);
		getActualTable().getGameState().nextTurn(u1); //ne pas oublier de passer au prochain tour.
	}

	@Override
	public void startTurn() {
		// TODO : Vérifier que ce soit la bonne méthode appelée car il y a aussi GameState.nextTurn(User)
		//
		User currentUser = new User(getProfileManager().getCurrentProfile());
		getActualTable().getGameState().setActualPlayer(currentUser);
	}

	@Override
	public void isTurn(User u) {
		getActualTable().getGameState().setActualPlayer(u);
	}

	@Override
	public void stopGame(boolean a) {
		if(a)
		this.setActualTable(null);
		//TODO une fois merge on pourra decommenter
//		else
//			this.getActualTable().setVote(false);
	}

	@Override
	public void askStopGame() {
		//TODO une fois merge on pourra decommenter
//		this.getActualTable().setVote(true);
	}

	@Override
	public void playerQuitGame(User u) {
		this.getActualTable().disconnect(u);
	}

	@Override
	public void hasAcceptedReplay(User u) {
		//TODO verifier dans merge que l'on vierfir bien qu'il y ai pas de doublon.
		this.getActualTable().castVote(new Vote(u,true,this.getActualTable().getEmptyVersion()));
	}

	@Override
	public void hasRefusedReplay(User u) {
		//TODO verifier dans merge que l'on vierfir bien qu'il y ai pas de doublon.
		this.getActualTable().castVote(new Vote(u,false,this.getActualTable().getEmptyVersion()));
	}

	@Override
	public void hasWon(User u) {
		getActualTable().getGameState().setWinnerGame(u);
	}

	@Override
	public void hasLost(User u) {
		getActualTable().getGameState().setLoserGame(u);
	}

	@Override
	public void showTimer() {
		//TODO : voir avec IHM
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

	@Override
	public void kicked(String s) {
		this.actualTable.set(null);
	}


	/////////////////////////////////////////////////////////////////////////////////





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
		this.userList.clear(); //pas utile ?
		this.userList.addAll(users);
	}

	public ObservableList<GameTable> getTableList() {
		return tableList;
	}

	public void setTableList(List<GameTable> gameTables) {
		this.tableList.clear(); //pas utile ?
		this.tableList.addAll(gameTables);
	}

	public ObservableList<Boolean> getSelectionList() {
		return this.selectionList;
	}

	public void setSelectionList(List<Boolean> selection) {
		this.selectionList.clear(); //pas utile ?
		this.selectionList.addAll(selection);
	}

}
