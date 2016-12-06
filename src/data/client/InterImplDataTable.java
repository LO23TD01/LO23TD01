package data.client;

import java.util.ArrayList;
import java.util.List;

import data.GameTable;
import data.Parameters;
import data.PlayerData;
import data.Profile;
import data.Rules;
import data.User;
import data.UserRole;
import data.Variant;
import data.Vote;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InterImplDataTable implements InterfaceDataIHMTable{

	private ClientDataEngine dataEngine;
	private final ObjectProperty<UserRole> actualRole;
	private final ObjectProperty<GameTable> actualTable;
	private final ObservableList<Boolean> selectionList = FXCollections.observableArrayList();

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
		//TODO selection list ?
	}

	/**
	 * @param dataEngine
	 */
	//consctruetuer pour IHM MAIn
	public InterImplDataTable(ClientDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;

		if(this.dataEngine.getActualTable()==null)
			;//throw exception
		if(this.dataEngine.getActualRole()==null)
			;//thwor new Escpetion
		if(this.dataEngine.getSelectionList()==null)
			;//thwor new Escpetion

		this.actualTable = new SimpleObjectProperty<GameTable>(this.dataEngine.getActualTable());
		this.actualRole = new SimpleObjectProperty<UserRole>(this.dataEngine.getActualRole());

		this.setSelectionList(this.dataEngine.getSelectionList());
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
		this.dataEngine.getComClientInterface().selectDice(this.dataEngine.getProfileManager().getCurrentProfile().getUUID(), a, b, c);
	}

	@Override
	public void launchGame() {
		this.dataEngine.getComClientInterface().launchGame(this.dataEngine.getProfileManager().getCurrentProfile().getUUID());
	}

	@Override
	public void quitGame() {
		this.dataEngine.getComClientInterface().quit(this.dataEngine.getProfileManager().getCurrentProfile().getUUID());
	}

	@Override
	public void sendMessage(String msg) {
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