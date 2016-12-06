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

public class ClientDataEngine implements InterfaceDataNetwork {

	//ToReview changer ces types pour en faire des non observables
	//les suel types observable devraient être ceux dans les interfaces
	private final ProfileManager profileManager;
	private final ObservableList<User> userList = FXCollections.observableArrayList();
	private final ObservableList<GameTable> tableList = FXCollections.observableArrayList();
	private final ObjectProperty<GameTable> actualTable;
	private final ObjectProperty<UserRole> actualRole;
	private final ObservableList<Boolean> selectionList = FXCollections.observableArrayList();

	private InterImplDataMain interfaceMain;
	//si on fait pas confiance à main on decommente cette ligne
	//private InterImplDataTable interfaceTable;


	/**
	 * Variable qui permet de communiquer avec le serveur, initialisÃ©e lors du login
	 */
	private ComClientInterface comClientInterface = null;

	/**
	 *
	 */
	//appelé une seule fois, dans le main !
	public ClientDataEngine() {
		super();
		this.profileManager = new ProfileManager();
		this.setUserList(null);
		this.setTableList(null);
		this.actualTable = null;
		this.actualRole = null;
		this.setSelectionList(null);
		this.interfaceMain = new InterImplDataMain(this);
		//si on fait pas confiance à main on decommente cette ligne
		//this.interfaceTable = new InterImplDataTable(this);
	}


	@Override
	public void raiseException(String msg) {
		//TODO niveau Ihm Main
		//implmenter un truc de leur coté
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




	public final ProfileManager getProfileManager() {
		return this.profileManager;
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


	public ComClientInterface getComClientInterface() {
		return comClientInterface;
	}

	public void setComClientInterface(ComClientInterface comClientInterface) {
		this.comClientInterface = comClientInterface;
	}



}
