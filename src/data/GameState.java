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
	private TurnState turnState;
	private User winnerGame; //le premier avec des jetons qui n'en a plus
	private User loserGame; //le dernier avec des jetons.

	
	
	
	public GameState(int chipStack, List<User> playerList, List<PlayerData> dataList, User firstPlayer,
			User actualPlayer, State state, Rules rules, List<User> winners, List<User> losers,
			List<PlayerData> dataTieList, TurnState turnState, User winnerGame, User loserGame) {
		super();
		this.chipStack = chipStack;
		this.playerList = playerList;
		this.dataList = dataList;
		this.firstPlayer = firstPlayer;
		this.actualPlayer = actualPlayer;
		this.state = state;
		this.rules = rules;
		this.winners = winners;
		this.losers = losers;
		this.dataTieList = dataTieList;
		this.turnState = turnState;
		this.winnerGame = winnerGame;
		this.loserGame = loserGame;
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
		this.turnState = TurnState.INIT;
		this.winnerGame = null;
		this.loserGame = null;
	}

	//methode pour faciliter la tache de l'engine
	//Va automatiquement l‡ o˘ necessaire : dataList dans le premier tour de table, dataTieList dans les tours de Tie Breaker.
	//il va remplacer la data du player par celle en argument;
	public void replaceData(PlayerData pData)
	{
		if(this.turnState==TurnState.INIT || this.turnState==TurnState.FIRST_ROUND)
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
		//tie en argument spÈcifie quelle donnÈe on recherche
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
	
		//remets ‡ null et ‡ 0 les valeurs de lancer de dÈs, winner,loser , etc ...
		//mets le player actuel et first player ‡ la bonne valeur
		public void nextTurn(User firstPlayer)
		{
			List<PlayerData> newList = new ArrayList<PlayerData>();
			for(PlayerData pd : this.dataList)
				newList.add(new PlayerData(pd,true));
			this.dataList = newList;
			this.firstPlayer = firstPlayer;
			this.actualPlayer = firstPlayer;
			this.winners = null;
			this.losers = null;
			this.dataTieList = null;
			this.turnState = TurnState.INIT;
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
	 * S'adapte au type de tour et ‡ la pahse du tour.
	 */
	public User getNextPlayer() {
		int nextIndex;
		if (this.turnState ==  TurnState.WINNER_TIE_ROUND)
		{
			nextIndex  = this.winners.indexOf(this.actualPlayer); //-1 si pas trouvÈ
			nextIndex = (nextIndex + 1) % this.winners.size();
			return this.winners.get(nextIndex);
		}	
		else if(this.turnState == TurnState.LOSER_TIE_ROUND){
			nextIndex  = this.losers.indexOf(this.actualPlayer); //-1 si pas trouvÈ
			nextIndex = (nextIndex + 1) % this.losers.size();
			return this.losers.get(nextIndex);
		}
		else{
			switch(this.state)
			{
			case PRESTART:
			case SELECTION:
			case CHARGING: //tous les 3 les mÍmes
				nextIndex = (this.playerList.indexOf(this.actualPlayer) + 1) % this.playerList.size();
				return playerList.get(nextIndex);
			case DISCHARGING:
				nextIndex = (this.playerList.indexOf(this.actualPlayer) + 1) % this.playerList.size();
				while(this.getData(this.playerList.get(nextIndex), false).getChip()!=0)
					nextIndex = (nextIndex + 1) % this.playerList.size();
				return this.playerList.get(nextIndex);
			case END:
				//TOREVIEW envoyer exception ? ca devrait pas Ítre ici
				default:
					//TOREVIEW envoyer exception ? ca devrait pas Ítre ici
					nextIndex = (this.playerList.indexOf(this.actualPlayer) + 1) % this.playerList.size();
					return this.playerList.get(nextIndex);
			}
		
		}
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

	
	
	
	public TurnState getTurnState() {
		return turnState;
	}


	public void setTurnState(TurnState turnState) {
		this.turnState = turnState;
	}


	public User getWinnerGame() {
		return winnerGame;
	}


	public void setWinnerGame(User winnerGame) {
		this.winnerGame = winnerGame;
	}


	public User getLoserGame() {
		return loserGame;
	}


	public void setLoserGame(User loserGame) {
		this.loserGame = loserGame;
	}

	

}
