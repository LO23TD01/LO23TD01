package data.client;

import java.util.ArrayList;
import java.util.List;

import data.ChatMessage;
import data.GameTable;
import data.Parameters;
import data.PlayerData;
import data.Profile;
import data.Rules;
import data.State;
//import data.State;
import data.TurnState;
import data.User;
import data.UserRole;
import data.Variant;
//import data.Vote;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InterImplDataTable implements InterfaceDataIHMTable{
	private final ObjectProperty<GameTable> actualTable;
	private ClientDataEngine dataEngine;
	private final ObjectProperty<UserRole> actualRole;
	private final ObservableList<Boolean> selectionList = FXCollections.observableArrayList();
	private ObjectProperty<Profile> currentProfile; //Warning faux car utilis� par IHM
	//aufinal votetext pas utilis�
	private StringProperty voteText; //Warning faux car utilis� par IHM

	//constructeur  de test pour ihm table
	public InterImplDataTable() {
		super();
		this.actualTable = new SimpleObjectProperty<GameTable>();
		User u1 = new User(new Profile("lol","Jeanlaque","AHAH","Pro","Gamer",42));
		User u2 = new User(new Profile("hackzorDu60","xXDeathKillerXx","AHAH","Kevin","Louzeur",10));
		List<User> uList = new ArrayList<User>();
		uList.add(u1);
		uList.add(u2);
		this.setActualTable(new GameTable("Table Test pour Ihm", u1, new Parameters(2,6,21,true,true,new Rules(Variant.CONSTRAINED_DISCHARGE,3)), uList, new ArrayList<User>()));
		this.actualRole = new SimpleObjectProperty<UserRole>();
		this.setSelectionList(this.dataEngine.getSelectionList());
		//NE PAS UTILISER
		this.currentProfile = new SimpleObjectProperty<Profile>();
		this.voteText = new SimpleStringProperty();
	}

	/**
	 * @param dataEngine
	 */
	//consctruetuer pour IHM MAIn
	public InterImplDataTable(ClientDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;

		if(this.dataEngine.getActualTable()==null)
			System.out.println("FAILURE INSTANCIATION INTERFACE TABLE : Table");//throw exception
		else if(this.dataEngine.getActualRole()==null)
			;//regle jsute apres la creation//System.out.println("FAILURE INSTANCIATION INTERFACE TABLE : Role");//thwor new Escpetion
		else if(this.dataEngine.getSelectionList()==null)
			System.out.println("FAILURE INSTANCIATION INTERFACE TABLE : SelectionList");//thwor new Escpetion

		this.actualTable = this.dataEngine.actualTableProperty();
		this.actualRole = this.dataEngine.actualRoleProperty();
		this.setSelectionList(this.dataEngine.getSelectionList());
		this.currentProfile = this.dataEngine.getProfileManager().currentProfileProperty();
		this.voteText = this.dataEngine.voteTextProperty();
	}


	/**
	 * @param dataEngine
	 * @param actualTable
	 * @param actualRole
	 * @param list
	 */
	//Constructeur en copie
public InterImplDataTable(ClientDataEngine dataEngine, UserRole actualRole,
			GameTable actualTable, List<Boolean> list) {
		super();
		this.dataEngine = dataEngine;
		this.actualTable = new SimpleObjectProperty<GameTable>(actualTable);
		this.actualRole = new SimpleObjectProperty<UserRole>(actualRole);
		this.setSelectionList(list);
		this.currentProfile = this.dataEngine.getProfileManager().currentProfileProperty();
		this.voteText = this.dataEngine.voteTextProperty();
}

 //ne renvoie que le premier Winner
 	public PlayerData getBest(){
 		if(this.getActualTable()==null)
 			;//exception
 		boolean tie =(this.getActualTable().getGameState().getTurnState()==TurnState.WINNER_TIE_ROUND || this.getActualTable().getGameState().getTurnState()==TurnState.LOSER_TIE_ROUND);
 		User winner = (tie? this.getActualTable().getGameState().getRules().getWinner(this.getActualTable().getGameState().getDataTieList()).get(0)
 				: this.getActualTable().getGameState().getRules().getWinner(this.getActualTable().getGameState().getDataList()).get(0));
 			return this.getActualTable().getGameState().getData(winner, tie);
 	}

 	//ne renvoie que le premier loser
 		public PlayerData getWorst(){
 			if(this.getActualTable()==null)
 				;//exception
 			boolean tie =(this.getActualTable().getGameState().getTurnState()==TurnState.WINNER_TIE_ROUND || this.getActualTable().getGameState().getTurnState()==TurnState.LOSER_TIE_ROUND);
 			User loser = (tie? this.getActualTable().getGameState().getRules().getLoser(this.getActualTable().getGameState().getDataTieList()).get(0)
 					: this.getActualTable().getGameState().getRules().getLoser(this.getActualTable().getGameState().getDataList()).get(0));
 				return this.getActualTable().getGameState().getData(loser, tie);
 		}

 		public int getValueCurrentTurn()
 		{
 			if(this.getActualTable()==null)
 				;//exception
 			return this.getActualTable().getGameState().getRules().getChip(this.getActualTable().getGameState().getDataList());
 		}


 		public void vote(boolean vote)
 		{
 			if(this.actualTable.get().getGameState().getState() == State.END)
 			{
 				if(vote) {
 					this.acceptReplay();
 				} else {
 					this.refuseReplay();
 				}
 			}
 			else
 				this.answerStopGame(vote);
 		}


///IMPLEMENTATION INTERFACE ICI
/////////////////////////////////////////////////////////////


	@Override
	public UserRole getUserRole() {
		return this.actualRole.get();
	}


	@Override
	public void throwDice(boolean a, boolean b, boolean c) {
		this.dataEngine.getComClientInterface().throwDice(this.dataEngine.getProfileManager().getCurrentProfile().getUUID(), a, b, c);

	}

	@Override
	public void selectDice(boolean a, boolean b, boolean c) {
		//pas besoin de changer icic la selection, le server va renvoyer � tout le monde y coimpris le joueur
		//sinon risque de conflit et de trucs pas beau
		//en plus ca permet de voir le temps de r�ponse du server

		//Debug :
		//this.dataEngine.getComClientInterface().selectDice(this.dataEngine.getProfileManager().getCurrentProfile().getUUID(), a, b, c);
	}

	@Override
	public void launchGame() {
		this.dataEngine.getComClientInterface().launchGame(this.dataEngine.getProfileManager().getCurrentProfile().getUUID());

	}

	@Override
	public void quitGame() {

		if(this.actualTable.get() != null)
		{
			// cahngement statiqitues profile
			if ((this.getActualTable().getGameState().getState() != State.PRESTART)
					&& (this.getActualTable().getGameState().getState() != State.END)) {

				Profile newProfile = new Profile(this.dataEngine.getProfileManager().getCurrentProfile());
				newProfile.setNbGameAbandonned(newProfile.getNbGameAbandonned() + 1);
				this.dataEngine.getInterfaceMain().changeMyProfile(newProfile);
			}

			this.dataEngine.getComClientInterface().quit(this.dataEngine.getProfileManager().getCurrentProfile().getUUID(), this.actualTable.get().getUid());
			this.setActualTable(null);
		}
		//fait au niveau serveur pour eviter les prob
//		if(this.getActualRole() == UserRole.CREATOR && this.getActualTable() != null && this.getActualTable().getGameState().getState() != State.PRESTART)
//			this.dataEngine.getComClientInterface().askQuitTable(this.actualTable.get().getUid(),this.dataEngine.getProfileManager().getCurrentProfile().getUUID());

	}

	@Override
	public void sendMessage(ChatMessage msg) {
		this.dataEngine.getComClientInterface().sendMessage(msg);
	}

	@Override
	public void acceptReplay() {
		this.dataEngine.getComClientInterface().acceptReplay(this.dataEngine.getProfileManager().getCurrentProfile().getUUID());
	}

	@Override
	public void refuseReplay() {
		this.dataEngine.getComClientInterface().refuseReplay(this.dataEngine.getProfileManager().getCurrentProfile().getUUID());
	}

	@Override
	public void answerStopGame(boolean answer) {
		this.dataEngine.getComClientInterface().answerStopGame(this.getActualTable().getUid(), answer, this.dataEngine.getProfileManager().getCurrentProfile().getUUID());
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


	public ObservableList<Boolean> getSelectionList() {
		return this.selectionList;
	}

	public void setSelectionList(List<Boolean> selection) {
		this.selectionList.clear(); //pas utile ?
		this.selectionList.addAll(selection);
	}



}
