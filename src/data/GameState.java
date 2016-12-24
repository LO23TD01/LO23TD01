package data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameState {
	private final IntegerProperty chipStack;
	private final ObservableList<User> playerList = FXCollections.observableArrayList();
	private final ObservableList<PlayerData> dataList = FXCollections.observableArrayList();
	private final ObjectProperty<User> firstPlayer;
	private final ObjectProperty<User> actualPlayer;
	private final ObjectProperty<State> state;
	private final ObjectProperty<Rules> rules;
	private ObservableList<User> winners = FXCollections.observableArrayList();
	private ObservableList<User> losers = FXCollections.observableArrayList();
	private ObservableList<PlayerData> dataTieList = FXCollections.observableArrayList();
	private ObjectProperty<TurnState> turnState;
	private ObjectProperty<User> winnerGame; // le premier avec des jetons qui n'en a plus
	private ObjectProperty<User> loserGame; // le dernier avec des jetons.

	public GameState(int chipStack, List<User> playerList, List<PlayerData> dataList, User firstPlayer, User actualPlayer, State state, Rules rules,
			List<User> winners, List<User> losers, List<PlayerData> dataTieList, TurnState turnState, User winnerGame, User loserGame) {
		this.chipStack = new SimpleIntegerProperty(chipStack);
		this.playerList.addAll(playerList);
		this.dataList.addAll(dataList);
		this.firstPlayer = new SimpleObjectProperty<User>(firstPlayer);
		this.actualPlayer = new SimpleObjectProperty<User>(actualPlayer);
		this.state = new SimpleObjectProperty<State>(state);
		this.rules = new SimpleObjectProperty<Rules>(rules);
		this.winners.addAll(winners);
		this.losers.addAll(losers);
		this.dataTieList.addAll(dataTieList);
		this.turnState = new SimpleObjectProperty<TurnState>(turnState);
		this.winnerGame = new SimpleObjectProperty<User>(winnerGame);
		this.loserGame = new SimpleObjectProperty<User>(loserGame);
	}

	/**
	 * @param param
	 * @param playerList
	 * @throws Exception
	 *
	 */
	public GameState(Parameters param, List<User> playerList) {
		// if (playerList.isEmpty())
		// throw new Exception("Erreur création GameState, aucun user !");

		this.chipStack = new SimpleIntegerProperty(param.getNbChip());
		this.playerList.addAll(playerList);
		this.firstPlayer = new SimpleObjectProperty<User>(playerList.get(0));
		this.actualPlayer = new SimpleObjectProperty<User>(playerList.get(0));
		this.state = new SimpleObjectProperty<State>(State.PRESTART);
		this.rules = new SimpleObjectProperty<Rules>(param.getRules());
		this.turnState = new SimpleObjectProperty<TurnState>(TurnState.INIT);
		this.winnerGame = new SimpleObjectProperty<User>();
		this.loserGame = new SimpleObjectProperty<User>();

		for (User player : playerList) {
			dataList.add(new PlayerData(player));
		}

	}

	// methode pour faciliter la tache de l'engine
	// Va automatiquement l� o� necessaire : dataList dans le premier tour de table, dataTieList dans les tours de Tie Breaker.
	// il va remplacer la data du player par celle en argument;
	public void replaceData(PlayerData pData) {
		if (this.turnState.get() == TurnState.INIT || this.turnState.get() == TurnState.FIRST_ROUND) {
			// if(pData.getPlayer().getSame(this.playerList)==null)
			// throw new Exception("Le joueur n'appartient pas � cette partie. Le joueur doit appartient � cette partie pour remplacer ses donn�es.");
			// else if(pData.getPlayer().getSame(this.dataList.stream().map(d->d.getPlayer()).collect(Collectors.toList()))==null)
			// throw new Exception("La donn�e du joueur n'existe pas. Le joueur doit avoir des donn�es pour les remplacer.");
			List<PlayerData> newList = this.dataList.stream().filter(d -> !d.getPlayer().isSame(pData.getPlayer())) // on prends toutes les donn�es
																													// n'appartenant pas � notre joueur.
					.collect(Collectors.toList());
			newList.add(pData);
			this.setDataList(newList);
		} else {
			// if(pData.getPlayer().getSame(this.playerList)==null)
			// throw new Exception("Le joueur n'appartient pas � cette partie. Le joueur doit appartient � cette partie pour remplacer ses donn�es.");
			// TODO verifier que le Tie existe
			// else if(pData.getPlayer().getSame(this.dataTielist.stream().map(d->d.getPlayer()).collect(Collectors.toList()))==null)
			// throw new Exception("La donn�e du joueur n'existe pas. Le joueur doit avoir des donn�es pour les remplacer.");
			List<PlayerData> newList = this.dataTieList.stream().filter(d -> !d.getPlayer().isSame(pData.getPlayer())) // on prends toutes les donn�es
																														// n'appartenant pas � notre joueur.
					.collect(Collectors.toList());
			newList.add(pData);
			this.setDataTieList(newList);
		}
	}

	// methode pour faciliter la tache de l'engine
	// il renvoie la data du player en argument;
	// tie en argument sp�cifie quelle donn�e on recherche
	public PlayerData getData(User u, boolean tie) {
		if (!tie) {
			// if(u.getSame(this.playerList)==null)
			// throw new Exception("Le joueur n'appartient pas � cette partie. Le joueur doit appartient � cette partie pour obtenir ses donn�es.");
			// else if(u.getSame(this.dataList.stream().map(d->d.getPlayer()).collect(Collectors.toList()))==null)
			// throw new Exception("La donn�e du joueur n'existe pas. Le joueur doit avoir des donn�es pour les recuperer.");
			PlayerData data = this.dataList.stream().filter(d -> u.isSame(d.getPlayer())).findFirst().get();
			return data;
		} else {
			// if(u.getSame(this.playerList)==null)
			// throw new Exception("Le joueur n'appartient pas � cette partie. Le joueur doit appartient � cette partie pour obtenir ses donn�es.");
			// TODO verifier que le TIE existe
			// else if(u.getSame(this.dataTieList.stream().map(d->d.getPlayer()).collect(Collectors.toList()))==null)
			// throw new Exception("La donn�e du joueur n'existe pas. Le joueur doit avoir des donn�es pour les recuperer.");
			PlayerData data = this.dataTieList.stream().filter(d -> u.isSame(d.getPlayer())).findFirst().get();
			return data;
		}

	}

	// remets � null et � 0 les valeurs de lancer de d�s, winner,loser , etc ...
	// mets le player actuel et first player � la bonne valeur
	public void nextTurn(User firstPlayer) {
		List<PlayerData> newList = new ArrayList<PlayerData>();
		for (PlayerData pd : this.dataList)
			newList.add(new PlayerData(pd, true));
		this.setDataList(newList);
		this.setFirstPlayer(firstPlayer);
		this.setActualPlayer(firstPlayer);
		this.winners = null;
		this.losers = null;
		this.dataTieList = null;
		this.setTurnState(TurnState.INIT);
	}

	/*
	 * TO REVIEW : vérification selon le state actuel, add user fonctionne seulement en phase de prestart ? + vérifier l'unicité
	 */
	public void add(User user) {
		playerList.add(user);
		dataList.add(new PlayerData(user));
	}

	/*
	 *
	 */
	public void remove(User user) {
		User playerToRemove = user.getSame(this.playerList);
		if(playerToRemove != null)
		{
			playerList.remove(playerToRemove);
			List<PlayerData> dataToRemove = this.dataList.stream().filter(p->p.getPlayer().isSame(playerToRemove)).collect(Collectors.toList());
			for(PlayerData p : dataToRemove)
			{
				this.dataList.remove(p);
			}
		}
	}

	/*
	 * Permet de récupérer le joueur suivant dans la suite. Renvoit le premier joueur dans la liste si on est arrivé à la fin. S'adapte au type de tour et � la
	 * pahse du tour.
	 */
	public User getNextPlayer() {
		int nextIndex;
		if (this.turnState.get() == TurnState.WINNER_TIE_ROUND) {
			nextIndex = this.winners.indexOf(this.actualPlayer); // -1 si pas trouv�
			nextIndex = (nextIndex + 1) % this.winners.size();
			return this.winners.get(nextIndex);
		} else if (this.turnState.get() == TurnState.LOSER_TIE_ROUND) {
			nextIndex = this.losers.indexOf(this.actualPlayer); // -1 si pas trouv�
			nextIndex = (nextIndex + 1) % this.losers.size();
			return this.losers.get(nextIndex);
		} else {
			switch (this.state.get()) {
			case PRESTART:
			case SELECTION:
			case CHARGING: // tous les 3 les m�mes
				nextIndex = (this.playerList.indexOf(this.actualPlayer) + 1) % this.playerList.size();
				return playerList.get(nextIndex);
			case DISCHARGING:
				nextIndex = (this.playerList.indexOf(this.actualPlayer) + 1) % this.playerList.size();
				while (this.getData(this.playerList.get(nextIndex), false).getChip() != 0)
					nextIndex = (nextIndex + 1) % this.playerList.size();
				return this.playerList.get(nextIndex);
			case END:
				// TOREVIEW envoyer exception ? ca devrait pas �tre ici
			default:
				// TOREVIEW envoyer exception ? ca devrait pas �tre ici
				nextIndex = (this.playerList.indexOf(this.actualPlayer) + 1) % this.playerList.size();
				return this.playerList.get(nextIndex);
			}

		}
	}

	public ObservableList<User> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<User> playerList){
		this.playerList.clear();
		this.playerList.addAll(playerList);
	}

	public ObservableList<PlayerData> getDataList() {
		return dataList;
	}

	public void setDataList(List<PlayerData> dataList){
		this.dataList.clear();
		this.dataList.addAll(dataList);
	}

	public ObservableList<User> getWinners() {
		return winners;
	}

	public void setWinners(List<User> winnersList){
		this.winners.clear();
		this.winners.addAll(winnersList);
	}

	public ObservableList<User> getLosers() {
		return losers;
	}

	public void setLosers(List<User> losersList){
		this.losers.clear();
		this.losers.addAll(losersList);
	}

	public ObservableList<PlayerData> getDataTieList() {
		return dataTieList;
	}

	public void setDataTieList(List<PlayerData> dataTieList){
		this.dataTieList.clear();
		this.dataTieList.addAll(dataTieList);
	}


	public final IntegerProperty chipStackProperty() {
		return this.chipStack;
	}

	public final int getChipStack() {
		return this.chipStackProperty().get();
	}

	public final void setChipStack(final int chipStack) {
		this.chipStackProperty().set(chipStack);
	}

	public final ObjectProperty<User> firstPlayerProperty() {
		return this.firstPlayer;
	}

	public final User getFirstPlayer() {
		return this.firstPlayerProperty().get();
	}

	public final void setFirstPlayer(final User firstPlayer) {
		this.firstPlayerProperty().set(firstPlayer);
	}

	public final ObjectProperty<User> actualPlayerProperty() {
		return this.actualPlayer;
	}

	public final User getActualPlayer() {
		return this.actualPlayerProperty().get();
	}

	public final void setActualPlayer(final User actualPlayer) {
		this.actualPlayerProperty().set(actualPlayer);
	}

	public final ObjectProperty<State> stateProperty() {
		return this.state;
	}

	public final State getState() {
		return this.stateProperty().get();
	}

	public final void setState(final State state) {
		this.stateProperty().set(state);
	}

	public final ObjectProperty<Rules> rulesProperty() {
		return this.rules;
	}

	public final Rules getRules() {
		return this.rulesProperty().get();
	}

	public final void setRules(final Rules rules) {
		this.rulesProperty().set(rules);
	}

	public final ObjectProperty<TurnState> turnStateProperty() {
		return this.turnState;
	}

	public final TurnState getTurnState() {
		return this.turnStateProperty().get();
	}

	public final void setTurnState(final TurnState turnState) {
		this.turnStateProperty().set(turnState);
	}

	public final ObjectProperty<User> winnerGameProperty() {
		return this.winnerGame;
	}

	public final User getWinnerGame() {
		return this.winnerGameProperty().get();
	}

	public final void setWinnerGame(final User winnerGame) {
		this.winnerGameProperty().set(winnerGame);
	}

	public final ObjectProperty<User> loserGameProperty() {
		return this.loserGame;
	}

	public final User getLoserGame() {
		return this.loserGameProperty().get();
	}

	public final void setLoserGame(final User loserGame) {
		this.loserGameProperty().set(loserGame);
	}



}
