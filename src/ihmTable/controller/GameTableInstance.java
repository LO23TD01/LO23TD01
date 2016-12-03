package ihmTable.controller;

import java.util.List;

import data.GameTable;
import data.Parameters;
import data.User;

public class GameTableInstance {

	private static GameTable gameTableInstance;

	public static GameTable getGameTableInstance() {
		if (gameTableInstance == null)
		{
			synchronized(GameTableInstance.class)
			{
				if (gameTableInstance == null)
				{
					//erreur car pas initialisé.
				}
			}
		}
		return gameTableInstance;
	}

	public GameTableInstance(GameTable gameTableInstance) {
		super();
		this.gameTableInstance = gameTableInstance;
	}

	public GameTableInstance(String name, User creator, Parameters parameters, List<User> playerList, List<User> spectatorList)
	{
		super();
		this.gameTableInstance = new GameTable(name, creator, parameters, playerList, spectatorList);
	}
	//todo faire les contstructeurs pour les autres cas.

}
