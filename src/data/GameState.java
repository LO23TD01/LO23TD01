package data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameState {
	private int chipStack;
	private List<User> playerList;
	private List<PlayerData> dataList;
	private User firstPlayer;
	private User actualPlayer;
	private State state;
	private Rules rules;
	private List<User> winners;
	private List<User> losers;
	private List<PlayerData> dataTieList;

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
		this.winners = null;
		this.losers = null;
		this.dataTieList = null;
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
//			throw new Exception("Erreur cr√©ation GameState, aucun user !");
		this.chipStack = param.getNbChip();
		this.playerList = playerList;
		this.dataList = new ArrayList<PlayerData>();
		for (User player : playerList) {
			dataList.add(new PlayerData(player));
		}
		this.firstPlayer = playerList.get(0);
		this.actualPlayer = playerList.get(0);
		this.state = State.PRESTART;
		this.rules = param.getRules(); //rÈfÈrence uniquement
		this.winners = null;
		this.losers = null;
		this.dataTieList = null;
	}

	//methode pour faciliter la tache de l'engine
	//il va remplacer la data du player par celle en arguement;
	public void replaceData(PlayerData pData, boolean tie)
	{
		if(!tie)
		{
//		if(pData.getPlayer().getSame(this.playerList)==null)
//			throw new Exception("Le joueur n'appartient pas ‡ cette partie. Le joueur doit appartient ‡ cette partie pour remplacer ses donnÈes.");
//		else if(pData.getPlayer().getSame(this.dataList.stream().map(d->d.getPlayer()).collect(Collectors.toList()))==null)
//			throw new Exception("La donnÈe du joueur n'existe pas. Le joueur doit avoir des donnÈes pour les remplacer.");
		List<PlayerData> newList = this.dataList.stream()
				.filter(d->!d.getPlayer().isSame(pData.getPlayer())) // on prends toutes les donnÈes n'appartenant pas ‡ notre joueur.
				.collect(Collectors.toList());
		newList.add(pData); 
		this.dataList = newList;
		}
		else
		{
//			if(pData.getPlayer().getSame(this.playerList)==null)
//			throw new Exception("Le joueur n'appartient pas ‡ cette partie. Le joueur doit appartient ‡ cette partie pour remplacer ses donnÈes.");
			//TODO verifier que le Tie existe
//		else if(pData.getPlayer().getSame(this.dataTielist.stream().map(d->d.getPlayer()).collect(Collectors.toList()))==null)
//			throw new Exception("La donnÈe du joueur n'existe pas. Le joueur doit avoir des donnÈes pour les remplacer.");
		List<PlayerData> newList = this.dataTieList.stream()
				.filter(d->!d.getPlayer().isSame(pData.getPlayer())) // on prends toutes les donnÈes n'appartenant pas ‡ notre joueur.
				.collect(Collectors.toList());
		newList.add(pData); 
		this.dataTieList = newList;
		}
	}
	
	//methode pour faciliter la tache de l'engine
		//il renvoie la data du player en argument;
		public PlayerData getData(User u, boolean tie)
		{
			if(!tie){
//			if(u.getSame(this.playerList)==null)
//				throw new Exception("Le joueur n'appartient pas ‡ cette partie. Le joueur doit appartient ‡ cette partie pour obtenir ses donnÈes.");
//			else if(u.getSame(this.dataList.stream().map(d->d.getPlayer()).collect(Collectors.toList()))==null)
//				throw new Exception("La donnÈe du joueur n'existe pas. Le joueur doit avoir des donnÈes pour les recuperer.");
			PlayerData data = this.dataList.stream()
			.filter(d->u.isSame(d.getPlayer()))
			.findFirst()
			.get();
			return data;
		}
			else
			{
//					if(u.getSame(this.playerList)==null)
//						throw new Exception("Le joueur n'appartient pas ‡ cette partie. Le joueur doit appartient ‡ cette partie pour obtenir ses donnÈes.");
				//TODO verifier que le TIE existe
//					else if(u.getSame(this.dataTieList.stream().map(d->d.getPlayer()).collect(Collectors.toList()))==null)
//						throw new Exception("La donnÈe du joueur n'existe pas. Le joueur doit avoir des donnÈes pour les recuperer.");
					PlayerData data = this.dataTieList.stream()
					.filter(d->u.isSame(d.getPlayer()))
					.findFirst()
					.get();
					return data;
			}
				
		}
	
		//TODO
		//remets ‡ null et ‡ 0 les valeurs de lancer de dÈs, winner,loser , etc ...
		//mets le player actuel et first player ‡ la bonne valeur
		public void nextTurn(User firstPlayer)
		{
			//TODO
		}
	
	/*
	 * TO REVIEW : v√©rification selon le state actuel, add user fonctionne
	 * seulement en phase de prestart ? + v√©rifier l'unicit√©
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
	 * Permet de r√©cup√©rer le joueur suivant dans la suite. Renvoit le premier
	 * joueur dans la liste si on est arriv√© √† la fin.
	 */
	public User getNextPlayer() {
		int nextIndex = (playerList.indexOf(this.actualPlayer) + 1) % playerList.size();
		return playerList.get(nextIndex);
	}
	
	//TODO
	//renvoie le joueurSuivant qui est dans la liste des Winners
	public User getNextPlayer() {
		//TODO
	}
	
	

	//OBSOLETE
//	/*
//	 * TODO Le joueur demande lancer les d√©s. Renvoit une exception si le joueur
//	 * qui demande n'est pas le joueur actuel.
//	 */
//	public void askRoll(User user) {
//
//	}
//
//	/*
//	 * TODO Le joueur demande relancer les d√©s. Renvoit une exception si le
//	 * joueur qui demande n'est pas le joueur actuel.
//	 */
//	public void askReroll(User user, boolean diceOne, boolean diceTwo, boolean diceThree) {
//
//	}

	//OBSOLETE : utiliser replaceData ‡ la place
//	/*
//	 * TODO Indique que le joueur √† lanc√© les d√©s, et met √† jour le playerData
//	 * avec les valeurs des d√©s.
//	 */
//	public void hasRolled(User user, int diceOne, int diceTwo, int DiceThree) {
//
//	}

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

	
	
	public List<User> getWinners() {
		return winners;
	}

	public void setWinners(List<User> winners) {
		this.winners = winners;
	}

	public List<User> getLosers() {
		return losers;
	}

	public void setLosers(List<User> losers) {
		this.losers = losers;
	}

	public List<PlayerData> getDataTieList() {
		return dataTieList;
	}

	public void setDataTieList(List<PlayerData> dataTieList) {
		this.dataTieList = dataTieList;
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
