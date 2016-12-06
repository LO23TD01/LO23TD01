package data.client;

import java.util.ArrayList;
import java.util.List;

import data.GameTable;
<<<<<<< HEAD
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
=======
import data.PlayerData;
import data.User;
import data.UserRole;
import data.Vote;
import javafx.beans.property.ObjectProperty;
>>>>>>> 516ab4195242c3be60b4c23c1cd42e33a94ab269

public class InterImplDataTable implements InterfaceDataIHMTable{
	private final ObjectProperty<GameTable> actualTable;
	private ClientDataEngine dataEngine;
	private final ObjectProperty<UserRole> actualRole;

	//constructeur  de test pour ihm table
	public InterImplDataTable() {
		super();
<<<<<<< HEAD

		this.actualTable = new SimpleObjectProperty<GameTable>();
		User u1 = new User(new Profile("lol","Jeanlaque","AHAH","Pro","Gamer",42));
		User u2 = new User(new Profile("hackzorDu60","xXDeathKillerXx","AHAH","Kevin","Louzeur",10));
		List<User> uList = new ArrayList<User>();
		uList.add(u1);
		uList.add(u2);
		this.setActualTable(new GameTable("Table Test pour Ihm", u1, new Parameters(2,6,21,true,true,new Rules(Variant.CONSTRAINED_DISCHARGE,3)), uList, new ArrayList<User>()));
		this.actualRole = new SimpleObjectProperty<UserRole>();
=======
		this.actualTable = null;
		this.actualRole = null;
>>>>>>> 516ab4195242c3be60b4c23c1cd42e33a94ab269
	}

	/**
	 * @param dataEngine
	 */
	//consctruetuer pour IHM MAIn
	public InterImplDataTable(ClientDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;
<<<<<<< HEAD
		this.actualTable = new SimpleObjectProperty<GameTable>();
		this.actualRole = new SimpleObjectProperty<UserRole>();
		if(this.dataEngine.getActualTable()==null)
			;//throw exception
		if(this.dataEngine.getActualRole()==null)
			;//thwor new Escpetion
		this.setActualTable(this.dataEngine.getActualTable());
		this.setActualRole(this.dataEngine.getActualRole());
=======
		this.actualTable = null;
		this.actualRole = null;
>>>>>>> 516ab4195242c3be60b4c23c1cd42e33a94ab269
	}

	/**
	 * @param actualTable
	 * @param actualRole
	 */
<<<<<<< HEAD
	//Constructeur en copie
	public InterImplDataTable(GameTable actualTable, UserRole actualRole) {
		super();
		this.actualTable = new SimpleObjectProperty<GameTable>(actualTable);
		this.actualRole = new SimpleObjectProperty<UserRole>(actualRole);
=======
	public InterImplDataTable(ObjectProperty<GameTable> actualTable, ObjectProperty<UserRole> actualRole) {
		super();
		this.actualTable = actualTable;
		this.actualRole = actualRole;
>>>>>>> 516ab4195242c3be60b4c23c1cd42e33a94ab269
	}



<<<<<<< HEAD
///IMPLEMENTATION INTERFACE ICI
/////////////////////////////////////////////////////////////
=======

>>>>>>> 516ab4195242c3be60b4c23c1cd42e33a94ab269

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
<<<<<<< HEAD
		this.dataEngine.getComClientInterface().refuseReplay(this.dataEngine.getProfileManager().getCurrentProfile().getUUID());
	}

/////////////////////////////////////////////////////////////

=======
		this.dataEngine.getComClientInterface().refuseReplay(profileManager.get().getCurrentProfile().getUUID());
	}

>>>>>>> 516ab4195242c3be60b4c23c1cd42e33a94ab269


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

<<<<<<< HEAD
=======



>>>>>>> 516ab4195242c3be60b4c23c1cd42e33a94ab269
}
