package data.client;

//import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import data.ChatMessage;
//import data.Contact;
//import data.ContactCategory;
import data.GameTable;
//import data.IPData;
import data.PlayerData;
import data.Profile;
//import data.Rights;
import data.State;
import data.TurnState;
import data.User;
import data.UserRole;
import IHM_MAIN.src.IHMLobbyAPI;
import data.Vote;
//import ihmTable.api.IHMTableLobbyImpl;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import network.client.ComClient;
import network.client.ComClientInterface;

public class ClientDataEngine implements InterfaceDataNetwork {

	//ToReview changer ces types pour en faire des non observables
	//les suel types observable devraient être ceux dans les interfaces
	private final ProfileManager profileManager;
	private final ObservableList<User> userList = FXCollections.observableArrayList();
	private final ObservableList<GameTable> tableList = FXCollections.observableArrayList();
	//Fix : Instancier les properties au lieu de mettre null sinon nullPointerException d�s le premier set.
	private final ObjectProperty<GameTable> actualTable = new SimpleObjectProperty<>();
	private final ObjectProperty<UserRole> actualRole = new SimpleObjectProperty<>();
	private final ObservableList<Boolean> selectionList = FXCollections.observableArrayList();
	private final StringProperty voteText;

	private InterImplDataMain interfaceMain; //Warning correct, mais on le garde quand meme pour savoir o� il est.
	//si on fait pas confiance à main on decommente cette ligne
	//private InterImplDataTable interfaceTable;


	/**
	 * Variable qui permet de communiquer avec le serveur, initialisÃ©e lors du login
	 */
	private ComClientInterface comClientInterface = null;

	/**
	 *
	 */
	public ClientDataEngine() {
		super();
		this.profileManager = new ProfileManager();
		InterfaceSingleThreadDataClient clientThread = new InterfaceSingleThreadDataClient(this);
		this.interfaceMain = new InterImplDataMain(clientThread);
		this.voteText = new SimpleStringProperty();
		//si on fait pas confiance à main on decommente cette ligne
		//this.interfaceTable = new InterImplDataTable(this);
	}

/*
	@Override
	public void selectDice(boolean a, boolean b, boolean c) {
		comClientInterface.selectDice(profileManager.getCurrentProfile().getUUID(), a, b, c);
	}*/

	//TO CLEAN crado
	public void replay()
	{
		if(getActualTable() == null)
			System.out.println("Erreur Pas de table");
		else
		{
			getActualTable().initializeGame();
		}
	}



	@Override
	public void exAequoCase(List<User> users, boolean win) {

		if(getActualTable() == null)
			System.out.println("Erreur Pas de table");
		else
		{
			if(win && getActualTable().getGameState().getTurnState()!=TurnState.WINNER_TIE_ROUND)
				getActualTable().getGameState().setTurnState(TurnState.WINNER_TIE_ROUND);
			else if(!win && getActualTable().getGameState().getTurnState()!=TurnState.LOSER_TIE_ROUND)
				getActualTable().getGameState().setTurnState(TurnState.WINNER_TIE_ROUND);
			if(win)
				getActualTable().getGameState().setWinners(users);
			else
				getActualTable().getGameState().setLosers(users);
			List<PlayerData> newList = new ArrayList<PlayerData>();
			for(User u : users)
				newList.add(new PlayerData(u));
			getActualTable().getGameState().setDataTieList(newList);
		}
	}

	@Override
	public void setCreator(UUID u){
		User newCreator = new User(new Profile(u));
		if(this.getActualTable() !=null)
		{
			if(!this.getActualTable().getCreator().isSame(newCreator))
				this.getActualTable().setCreator(newCreator);
			if(this.profileManager.getCurrentProfile().getUUID()==u && this.getActualRole()!=UserRole.CREATOR)
				this.setActualRole(UserRole.CREATOR);
			this.getActualTable().setVote(false);
		}


	}

	@Override
	public void refreshUsersList(List<User> l) {

		updateUsersList(l);
	}

	@Override
	public void updateUsersList(List<User> l) {
		//Appeler lorsqu'un nouveau joueur se connecte
		//Action similaire à refreshUsersList mais dans le doute d'une autre utilité je laisse les deux
		ObservableList<User> userList = this.userList;
		Platform.runLater(new Runnable() {
            @Override public void run() {
        		userList.setAll(l);
            }
        });
	}

	//TODO A CLEAN
	public void refreshTableList(List<GameTable> l) {

		ObservableList<GameTable> tableList = this.tableList;
		Platform.runLater(new Runnable() {
            @Override public void run() {
            	tableList.setAll(l);
            }
        });
	}

	@Override
	public void updateTablesList(List<GameTable> l) {
		ObservableList<GameTable> tableList = this.tableList;
		Platform.runLater(new Runnable() {
            @Override public void run() {
        		tableList.setAll(l);
            }
        });
	}

	@Override
	public void updateUsers(User u) {
		User userFound = u.getSame(this.userList);
		ObservableList<User> userList = this.userList;
		if(userFound !=null){
			Platform.runLater(new Runnable() {
                @Override public void run() {
        			userList.set(userList.indexOf(userFound), u);
                }
            });
		}
		else{
			Platform.runLater(new Runnable() {
                @Override public void run() {
        			userList.add(u);
                }
            });
		}
	}

	@Override
	public void addNewTable(GameTable g) {
		if(g.getSame(this.tableList)==null){
			ObservableList<GameTable> tableList = this.tableList;
			Platform.runLater(new Runnable() {
                @Override public void run() {
        			tableList.add(g);
                }
            });
		}
//		else
//			throw new Exception("Erreur reception table. Table déjà existante.");
	}

	@Override
	public void sendTableInfo(GameTable g) {
		if(this.actualTable.isNull().get()){
			this.actualTable.set(g);

			// callback: open table with g
			//new IHMLobbyAPI().closeWaitingWindow();

			InterImplDataTable inter = new InterImplDataTable(this);
			User currentUser = new User(this.getProfileManager().getCurrentProfile());
			if(g.getCreator().isSame(currentUser))
				inter.setActualRole(UserRole.CREATOR);
			else
				inter.setActualRole(UserRole.PLAYER);

			User user = new User(this.getProfileManager().getCurrentProfile());
			Platform.runLater(new Runnable() {
		            @Override public void run() {
						new IHMLobbyAPI().displayTable(inter, user);
		            }
		     });
		}
		else if(g.isSame(this.getActualTable())) // on remplace la table qui doit contenir de nouvelles informations
			this.actualTable.set(g);
		else
			System.out.println("Erreur reception table. On est déjà à une table");
			//throw new Exception("Erreur reception table. On est déjà à une table");


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

	//WARNING JAMAIS APPELE
	//du coup la passe en obsolete car hasThrown fait la meme chose
	@Override
	public void setDice(int a, int b, int c) {

		System.out.println("Fonction obselete appel� : ClientDataEngine.setDice");
//		int[] dices = {a,b,c};
//		getActualTable().getGameState().getData(new User(this.getProfileManager().getCurrentProfile()), false).setDices(dices); //On check avec soi-meme.
//
//		//fix pour la Selection
//		List<Boolean>  newList = new ArrayList<Boolean>();
//		newList.add(false);
//		newList.add(false);
//		newList.add(false);
//		this.setSelectionList(newList);
//
//		getActualTable().getGameState().debugDisplay();
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
		getActualTable().getGameState().getData(u, false).setRerollCount(getActualTable().getGameState().getData(u, false).getRerollCount()+1);

//		//fix pour la Selection
//		List<Boolean>  newList = new ArrayList<Boolean>();
//		newList.add(false);
//		newList.add(false);
//		newList.add(false);
//		this.setSelectionList(newList);

		getActualTable().getGameState().debugDisplay();
	}

	//c'est une addition ici
	@Override
	public void updateChips(User u, int a) {
		PlayerData p1 = new PlayerData(actualTable.get().getGameState().getData(u, false));
		p1.setChip(p1.getChip()+a);
		getActualTable().getGameState().replaceData(p1);
		int newChipStack = getActualTable().getGameState().getChipStack()-a;
		getActualTable().getGameState().setChipStack(newChipStack);
		getActualTable().getGameState().nextTurn(u); //ne pas oublier de passer au prochain tour.
	}

	@Override
	public void updateChips(User u1, User u2, int a) {
		PlayerData p1 = new PlayerData(actualTable.get().getGameState().getData(u1, false));
		PlayerData p2 = new PlayerData(actualTable.get().getGameState().getData(u2, false));

		p1.setChip(p1.getChip()-a);
		p2.setChip(p2.getChip()+a);
		getActualTable().getGameState().replaceData(p1);
		getActualTable().getGameState().replaceData(p2);
		getActualTable().getGameState().nextTurn(u1); //ne pas oublier de passer au prochain tour.
	}

	@Override
	public void startTurn() {
		// TODO : Vérifier que ce soit la bonne méthode appelée car il y a aussi GameState.nextTurn(User)
		//
		User currentUser = new User(getProfileManager().getCurrentProfile());
//		if(currentUser.isSame(getActualTable().getGameState().getActualPlayer()))
//			getActualTable().getGameState().setActualPlayer(null);
		getActualTable().getGameState().setActualPlayer(currentUser.getSame(getActualTable().getGameState().getPlayerList()));

	}

	@Override
	public void isTurn(User u) {
		getActualTable().getGameState().setActualPlayer(u.getSame(getActualTable().getGameState().getPlayerList()));
	}

	@Override
	public void stopGame(boolean a) {
		if(a)
			this.setActualTable(null);
		else
			this.getActualTable().setVote(false);
	}

	@Override
	public void askStopGame() {
		this.setVoteText("Voulez-vous arretez la partie ?");
		this.getActualTable().startVote();
	}

	@Override
	public void playerQuitGame(User u) {
		if(this.getActualTable()!=null)
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

		//cahngement statiqitues profile
		User actualUser = new User(this.profileManager.getCurrentProfile());
		if(u.isSame(actualUser))
		{
			Profile newProfile = new Profile(this.profileManager.getCurrentProfile());
			newProfile.setNbGameWon(newProfile.getNbGameWon()+1);
			this.interfaceMain.changeMyProfile(newProfile);
		}
	}

	@Override
	public void hasLost(User u) {
		this.getActualTable().getGameState().setState(State.END);
		this.getActualTable().startVote();
		this.setVoteText("Voulez-vous recommencer la partie ?");
		getActualTable().getGameState().setLoserGame(u);


		//cahngement statiqitues profile
		User actualUser = new User(this.profileManager.getCurrentProfile());
		if(u.isSame(actualUser))
		{
			Profile newProfile = new Profile(this.profileManager.getCurrentProfile());
			newProfile.setNbGameLost(newProfile.getNbGameLost()+1);
			this.interfaceMain.changeMyProfile(newProfile);
		}
	}

	@Override
	public void showTimer() {
		//TODO : voir avec IHM
		getActualTable().initializeGame();
	}

	@Override
	public void newPlayerOnTable(User u) {
		getActualTable().getGameState().add(u.getLightWeightVersion()); // cette ligne doit �tre avant car sinon la ligne d' dessous trigger direct un listner sur cette valeur (qui sinon n'ets pas cr��e)
		getActualTable().getPlayerList().add(u);
	}

	@Override
	public void newSpectatorOnTable(User u) {
		getActualTable().getSpectatorList().add(u);
	}

	@Override
	public void kicked(String s) {
		this.actualTable.set(null);
	}

	@Override
	public void raiseException(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void  changeState(State s)
	{
		getActualTable().getGameState().setState(s);
		if(s == State.CHARGING)
			getActualTable().getGameState().nextTurn(getActualTable().getGameState().getFirstPlayer()); //TODO a clean bidouillage
	}

	@Override
	public void  changeTurnState(TurnState s)
	{
		getActualTable().getGameState().setTurnState(s);
		if(s==TurnState.WINNER_TIE_ROUND)

		{
			getActualTable().getGameState().setWinners(getActualTable().getGameState().getRules().getWinner(getActualTable().getGameState().getDataList()));
			List<PlayerData> newList = new ArrayList<PlayerData>();
			for (User u : getActualTable().getGameState().getWinners())
				newList.add(new PlayerData(u));
			getActualTable().getGameState().setActualPlayer(getActualTable().getGameState().getWinners().get(0));
			getActualTable().getGameState().setFirstPlayer(getActualTable().getGameState().getWinners().get(0));
		}
		else if(s==TurnState.LOSER_TIE_ROUND)
		{
			getActualTable().getGameState().setLosers(getActualTable().getGameState().getRules().getLoser(getActualTable().getGameState().getDataList()));
			List<PlayerData> newList = new ArrayList<PlayerData>();
			for (User u : getActualTable().getGameState().getLosers())
				newList.add(new PlayerData(u));
			getActualTable().getGameState().setActualPlayer(getActualTable().getGameState().getLosers().get(0));
			getActualTable().getGameState().setFirstPlayer(getActualTable().getGameState().getLosers().get(0));
		}
	}

	@Override
 	public void writeMessage(ChatMessage c){
		this.getActualTable().getLocalChat().add(c);
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
		ObservableList<User> userList = this.userList;
		Platform.runLater(new Runnable() {
            @Override public void run() {
        		userList.clear(); //pas utile ?
        		userList.addAll(users);
            }
        });
	}

	public ObservableList<GameTable> getTableList() {
		return tableList;
	}

	public void setTableList(List<GameTable> gameTables) {

		List<GameTable> tableList = this.tableList;
		Platform.runLater(new Runnable() {
            @Override public void run() {
        		tableList.clear(); //pas utile ?
        		tableList.addAll(gameTables);
            }
        });
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

	public final StringProperty voteTextProperty() {
		return this.voteText;
	}


	public final String getVoteText() {
		return this.voteTextProperty().get();
	}


	public final void setVoteText(final String voteText) {
		this.voteTextProperty().set(voteText);
	}

	public InterImplDataMain getInterfaceMain() {
		return interfaceMain;
	}




}
