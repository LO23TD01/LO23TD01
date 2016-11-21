package data;

import java.util.ArrayList;
import java.util.List;

public class GameState {
	private int chipStack;
	private List<User> playerList;
	private List<PlayerData> dataList;
	private User firstPlayer;
	private User actualPlayer;
	private State state;
	private Rules rules;

	/**
	 * @param chipStack
	 * @param playerList
	 * @param dataList
	 * @param firstPlayer
	 * @param actualPlayer
	 * @param state
	 * @param rules
	 */
	public GameState(int chipStack, List<User> playerList, List<PlayerData> dataList, User firstPlayer,
			User actualPlayer, State state, Rules rules) {
		super();
		this.chipStack = chipStack;
		this.playerList = playerList;
		this.dataList = dataList;
		this.firstPlayer = firstPlayer;
		this.actualPlayer = actualPlayer;
		this.state = state;
		this.rules = rules;
	}

	/**
	 * @param param
	 * @param playerList
	 * @throws Exception 
	 * 
	 */
	public GameState(Parameters param, List<User> playerList)  {
		super();
//		if (playerList.isEmpty())
//			throw new Exception("Erreur création GameState, aucun user !");
		this.chipStack = param.getNbChip();
		this.playerList = playerList;
		this.dataList = new ArrayList<PlayerData>();
		for (User player : playerList) {
			dataList.add(new PlayerData(player));
		}
		this.firstPlayer = playerList.get(0);
		this.actualPlayer = playerList.get(0);
		this.state = State.PRESTART;
		this.rules = param.getRules();
	}

	/*
	 * TO REVIEW : vérification selon le state actuel, add user fonctionne
	 * seulement en phase de prestart ? + vérifier l'unicité
	 */
	public void add(User user) {
		playerList.add(user);
		dataList.add(new PlayerData(user));
	}

	/*
	 * 
	 */
	public void remove(User user) {
		for (User player : playerList) {
			if (player.isSame(user))
				playerList.remove(player);
		}
		for (PlayerData data : dataList) {
			if (data.getPlayer().isSame(user))
				dataList.remove(data);
		}
	}

	/*
	 * Permet de récupérer le joueur suivant dans la suite. Renvoit le premier
	 * joueur dans la liste si on est arrivé à la fin.
	 */
	public User getNextPlayer() {
		int nextIndex = (playerList.indexOf(this.actualPlayer) + 1) % playerList.size();
		return playerList.get(nextIndex);
	}

	/*
	 * TODO Le joueur demande lancer les dés. Renvoit une exception si le joueur
	 * qui demande n'est pas le joueur actuel.
	 */
	public void askRoll(User user) {

	}

	/*
	 * TODO Le joueur demande relancer les dés. Renvoit une exception si le
	 * joueur qui demande n'est pas le joueur actuel.
	 */
	public void askReroll(User user, boolean diceOne, boolean diceTwo, boolean diceThree) {

	}

	/*
	 * TODO Indique que le joueur à lancé les dés, et met à jour le playerData
	 * avec les valeurs des dés.
	 */
	public void hasRolled(User user, int diceOne, int diceTwo, int DiceThree) {

	}

	/**
	 * @return the chipStack
	 */
	public int getChipStack() {
		return chipStack;
	}

	/**
	 * @param chipStack
	 *            the chipStack to set
	 */
	public void setChipStack(int chipStack) {
		this.chipStack = chipStack;
	}

	/**
	 * @return the playerList
	 */
	public List<User> getPlayerList() {
		return playerList;
	}

	/**
	 * @param playerList
	 *            the playerList to set
	 */
	public void setPlayerList(List<User> playerList) {
		this.playerList = playerList;
	}

	/**
	 * @return the dataList
	 */
	public List<PlayerData> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList
	 *            the dataList to set
	 */
	public void setDataList(List<PlayerData> dataList) {
		this.dataList = dataList;
	}

	/**
	 * @return the firstPlayer
	 */
	public User getFirstPlayer() {
		return firstPlayer;
	}

	/**
	 * @param firstPlayer
	 *            the firstPlayer to set
	 */
	public void setFirstPlayer(User firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	/**
	 * @return the actualPlayer
	 */
	public User getActualPlayer() {
		return actualPlayer;
	}

	/**
	 * @param actualPlayer
	 *            the actualPlayer to set
	 */
	public void setActualPlayer(User actualPlayer) {
		this.actualPlayer = actualPlayer;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return the rules
	 */
	public Rules getRules() {
		return rules;
	}

	/**
	 * @param rules
	 *            the rules to set
	 */
	public void setRules(Rules rules) {
		this.rules = rules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualPlayer == null) ? 0 : actualPlayer.hashCode());
		result = prime * result + chipStack;
		result = prime * result + ((dataList == null) ? 0 : dataList.hashCode());
		result = prime * result + ((firstPlayer == null) ? 0 : firstPlayer.hashCode());
		result = prime * result + ((playerList == null) ? 0 : playerList.hashCode());
		result = prime * result + ((rules == null) ? 0 : rules.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameState other = (GameState) obj;
		if (actualPlayer == null) {
			if (other.actualPlayer != null)
				return false;
		} else if (!actualPlayer.equals(other.actualPlayer))
			return false;
		if (chipStack != other.chipStack)
			return false;
		if (dataList == null) {
			if (other.dataList != null)
				return false;
		} else if (!dataList.equals(other.dataList))
			return false;
		if (firstPlayer == null) {
			if (other.firstPlayer != null)
				return false;
		} else if (!firstPlayer.equals(other.firstPlayer))
			return false;
		if (playerList == null) {
			if (other.playerList != null)
				return false;
		} else if (!playerList.equals(other.playerList))
			return false;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		if (state != other.state)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GameState [chipStack=" + chipStack + ", playerList=" + playerList + ", dataList=" + dataList
				+ ", firstPlayer=" + firstPlayer + ", actualPlayer=" + actualPlayer + ", state=" + state + ", rules="
				+ rules + "]";
	}

}
