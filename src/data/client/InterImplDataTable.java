package data.client;

import java.util.ArrayList;
import java.util.List;

import data.GameTable;
import data.PlayerData;
import data.User;
import data.UserRole;
import data.Vote;
import javafx.beans.property.ObjectProperty;

public class InterImplDataTable implements InterfaceDataIHMTable{
	private final ObjectProperty<GameTable> actualTable;
	private ClientDataEngine dataEngine;
	private final ObjectProperty<UserRole> actualRole;

	//constructeur  de test pour ihm table
	public InterImplDataTable() {
		super();
		this.actualTable = null;
		this.actualRole = null;
	}

	/**
	 * @param dataEngine
	 */
	//consctruetuer pour IHM MAIn
	public InterImplDataTable(ClientDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;
		this.actualTable = null;
		this.actualRole = null;
	}

	/**
	 * @param actualTable
	 * @param actualRole
	 */
	public InterImplDataTable(ObjectProperty<GameTable> actualTable, ObjectProperty<UserRole> actualRole) {
		super();
		this.actualTable = actualTable;
		this.actualRole = actualRole;
	}





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
		this.dataEngine.getComClientInterface().refuseReplay(profileManager.get().getCurrentProfile().getUUID());
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




}
