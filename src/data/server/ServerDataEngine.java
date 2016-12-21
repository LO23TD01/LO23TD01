package data.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.UUID;
import java.util.stream.Collectors;

import data.ChatMessage;
import data.GameTable;
import data.Parameters;
import data.PlayerData;
import data.Profile;
import data.State;
import data.TurnState;
import data.User;
import data.Vote;
import network.server.ComServer;

public class ServerDataEngine implements InterfaceDataNetwork {
	private List<User> usersList;
	private List<GameTable> tableList;

	private Timer time;
	private ComServer comServer;


	/*
	 *
	 * Constructor
	 *
	 */

	public ServerDataEngine() {
		this.usersList = new ArrayList<>();
		this.tableList = new ArrayList<>();
		this.time = new Timer();
		this.comServer = null;
	}

	/*
	 *
	 * Methods
	 *
	 */

	public GameTable createTable(User user, String name, Parameters params) {

		// Initialisation des joueurs de la table avec le joueur crï¿½ant la table
		List<User> playerList = new ArrayList<User>();
		playerList.add(user);

		// Initialisation spectateurs, vide au dï¿½but
		List<User> spectatorList = new ArrayList<User>();

		return new GameTable(name, user, params, playerList, spectatorList);
	}

	private void startLaunchTimer(GameTable table) // Edit : ajout de la
													// GameTable. Il faut savoir
													// quelle table lancer ...
	{
		GameTable tableFull = table.getSame(this.tableList);
		 if(tableFull==null)
			 this.comServer.raiseException(getUUIDList(tableFull.getAllList()), "La table n'existe pas. Il faut que la table existe pour s'y connecter.");
		 else
			 this.comServer.showTimer(getUUIDList(table.getAllList()));
	}


	/*
	 *
	 * Implemented Methods
	 *
	 */

	@Override
	public Profile getProfile(User user) {
		 return user.getSame(this.usersList).getPublicData();
	}

	@Override
	public void updateUserProfile(UUID uuid, Profile profile) {
		// uuid useless (il est dï¿½jï¿½ dans le profile bande de bananes.)

		User compUser = new User(profile);
		 if(!compUser.isFullVersion())
			 this.comServer.raiseException(uuid,"Profil non complet lors de la mise ï¿½ jour. Il faut un profil complet");
		 else if(compUser.getSame(this.usersList)==null)
			 this.comServer.raiseException(uuid,"Profil non connectï¿½. Il faut que le profil soit		 connectï¿½ pour le mettre ï¿½ jour.");
		 else
			 compUser.getSame(this.usersList).setPublicData(profile);
		// La suite du diag de sequence a ï¿½tï¿½ overrided par l'avis du prof.
	}

	@Override
	public void sendMessage(ChatMessage message) {
		User userFull = message.getSender().getSame(this.usersList);
		if(userFull==null)
			this.comServer.raiseException(message.getSender().getPublicData().getUUID(),"L'utilisateur n'exsite pas, Il doit exister pour parler.");
		else if(userFull.getActualTable()==null)
			this.comServer.raiseException(message.getSender().getPublicData().getUUID(),"L'utilisateur n'est à aucune table. il doit être à une table pour parler.");
		else {
		GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
		if(tableFull==null)
			this.comServer.raiseException(message.getSender().getPublicData().getUUID(),"L'utilisateur est à une table qui n'existe pas. La table doit exister pour parler.");
		else if(tableFull.getLocalChat().isVoiced(message))
		{
			tableFull.getLocalChat().add(message);

			this.comServer.sendMessage(getUUIDList(tableFull.getLocalChat().getListeningUserList()), message);
		}
		else
		{
			this.comServer.raiseException(message.getSender().getPublicData().getUUID(),"L'utilisateur n'a pas les droits pour parler. Il doit avoir le droit de parler pour parler.");
		}
		}
	}

	@Override
	public void dropTable(GameTable table) {
		GameTable tableFull = table.getSame(this.tableList);
//		 if(tableFull==null)
//			 throw new Exception("La table n'existe pas. Il faut que la table existe pour la détruire.");
		 for(User u : tableFull.getAllList())
		 {
			 User userFull = u.getSame(this.usersList);
			 if(userFull==null)
				 this.comServer.raiseException(u.getPublicData().getUUID(),"L'utilisateur n'est pas connecté. Il faut être connecté pour se faire kick d'une table.");
			 else
				 userFull.setActualTable(null);
		 }
		 this.comServer.kick(getUUIDList(tableFull.getAllList()), "La partie n'existe plus.");
		 this.tableList.remove(tableFull);
	}


	//Table inutile
	@Override
	public void quit(User user, GameTable table) {
		User userFull = user.getSame(this.usersList);
		 if(userFull==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'est pas connecté. Il faut être connecté pour quitter une table.");

		 else if(userFull.getActualTable()==null)
			 this.comServer.raiseException(userFull.getPublicData().getUUID(),"L'utilisateur n'est pas à une table. Il dtoi être à une table pour la quitter.");
		 else{
		 GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
		 if(userFull.getActualTable()==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur est à une table qui n'existe pas. La table doit exister pour quitter la table..");
		 else{
		tableFull.disconnect(userFull);
		userFull.setActualTable(null);
		this.comServer.playerQuitGame(getUUIDList(tableFull.getAllList()), userFull.getPublicData().getUUID());
		 }}
		 }

	@Override
	public void askJoinTable(User user, GameTable table, boolean isPlayer) {
		GameTable tableFull = table.getSame(this.tableList);
		User userFull = user.getSame(this.usersList);
		 if(tableFull==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"La table n'existe pas. Il faut que la table existe pour s'y connecter.");
		 else if(userFull==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'est pas connectï¿½. Il faut ï¿½tre connectï¿½ pour rejoindre une table.");
		 else
		 {
		boolean isLaunched = tableFull.getGameState().getState() != State.PRESTART;
		boolean isFull = tableFull.getParameters().getNbPlayerMax() >= tableFull.getPlayerList().size();
		boolean isSpecAuthorized = tableFull.getParameters().isAuthorizeSpec();

		if (tableFull.connect(userFull, isPlayer)) {
			// Success
			userFull.setActualTable(tableFull.getEmptyVersion());
			userFull.setSpectating(!isPlayer);
			if (isPlayer){
				List<UUID> currentPlayers = getUUIDList(tableFull.getAllList());
				currentPlayers.remove(user.getPublicData().getUUID());
				this.comServer.newPlayerOnTable(currentPlayers, userFull.getPublicData(), tableFull);
			}
			else
				this.comServer.newPlayerOnTable(getUUIDList(tableFull.getAllList()), userFull.getPublicData(),
						tableFull);
		} else if (isPlayer && isLaunched)
			this.comServer.raiseException(userFull.getPublicData().getUUID(),
					"Impossible de rejoindre une partie dï¿½jï¿½ commencï¿½e.");
		else if (isPlayer && isFull)
			this.comServer.raiseException(userFull.getPublicData().getUUID(), "Impossible de rejoindre une partie pleine.");
		else if (!isPlayer && !isSpecAuthorized)
			this.comServer.raiseException(userFull.getPublicData().getUUID(),
					"Impossible de regarder cette partie. Non Autorisï¿½ par le Crï¿½ateur.");
		else {
			// la table n'a pas reussi ï¿½ connecter le nouveau user malgrï¿½ nos
			// test en ammonts
			// TOREVIEW est ce que c'est une bonne idï¿½e d'afficher l'erreur de
			// cette maniï¿½re au client ? Le fiat est que si on le fait pas, il
			// attendra de maniï¿½re infinie d'apres nos diag de sequence.
			this.comServer.raiseException(userFull.getPublicData().getUUID(),
					"Erreur inconnue lors de la connexion ï¿½ la table");
			// throw new Exception("Erreur inconnue lors de la connexion ï¿½ la
			// table");
		}
		}
	}

	@Override
	public void launchGame(User user) {
		User userFull = user.getSame(this.usersList);
		 if(userFull==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'est pas connectï¿½. Il faut ï¿½tre connectï¿½ pour lancer une partie.");
		 else if(userFull.getActualTable()==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'a rejoint aucune table. Il faut ï¿½tre assit ï¿½ une table pour lancer une partie.");
		 else if(!userFull.getActualTable().getCreator().isSame(user))
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'est pas le createur de sa partie. Il faut ï¿½tre le createur pour lancer une partie.");
		GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
		 if(tableFull==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"La table n'existe pas. Il faut que la table existe pour s'y connecter.");
		tableFull.initializeGame();
		this.startLaunchTimer(userFull.getActualTable());
		gameEngine(tableFull, false);

	}

	@Override
	public void createNewTable(User user, String name, Parameters params) {
		User userFull = user.getSame(this.usersList);
		 if(userFull==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'est pas connecté. Il faut être connecté pour lancer une partie.");
		 else if(userFull.getActualTable()!=null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur est déjà à une table. Il faut sortir de table avant d'en créer une nouvelle.");
		 else{
		 GameTable newTable = createTable(userFull.getLightWeightVersion(), name, params);
		this.tableList.add(newTable);
		userFull.setActualTable(newTable.getEmptyVersion());
		userFull.setSpectating(false);
		this.comServer.addNewTable(user.getPublicData().getUUID(), getUUIDList(this.usersList), newTable);
		 }
		}

	//DOUBLON
	@Override
	public void disconnectUser(User user) {
		this.disconnect(user);
	}
	public void disconnect(User user) {
		if(user.getActualTable()!=null)
		{
			GameTable tableFull = user.getActualTable().getSame(this.tableList);
			if(tableFull !=null)
				tableFull.disconnect(user);
		}
		this.usersList.remove(user.getSame(this.usersList));
		List<User> toSend = getLightweightList(this.usersList);
		for(User u : this.usersList)
		{
			this.comServer.refreshUserList(u.getPublicData().getUUID(),toSend);
		}
	}


	@Override
	// boolean true signifie relancer le dï¿½
	public void hasThrown(UUID uuid, boolean d1, boolean d2, boolean d3) {
		User emptyUser = new User(new Profile(uuid));
		User userFull = emptyUser.getSame(this.usersList);
		 if(userFull==null)
			 this.comServer.raiseException(uuid,"L'utilisateur n'est pas connectï¿½. Il faut ï¿½tre connectï¿½ pour lancer les dï¿½s.");
		 else{
		 GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
		 if(tableFull==null)
			 this.comServer.raiseException(uuid,"La table n'existe pas. Il faut que la table existe pour y lancer les dï¿½s.");
		 else if(!tableFull.getGameState().getActualPlayer().isSame(userFull))
			 this.comServer.raiseException(uuid,"Le lanceur de dï¿½s n'est pas le joueur actuel. Il faut ï¿½tre le joueur actuel pour lancer les dï¿½s.");
		 else
		 {
		 boolean tie = (tableFull.getGameState().getTurnState() == TurnState.LOSER_TIE_ROUND
				|| tableFull.getGameState().getTurnState() == TurnState.WINNER_TIE_ROUND);
		PlayerData pData = new PlayerData(tableFull.getGameState().getData(userFull, tie));
		boolean isFirstRoll = (pData.getRerollCount() == 0);
		boolean isStop = !(d1 || d2 || d3);
		 boolean canReroll = tableFull.getGameState().getRules().canReroll(tableFull.getGameState().getDataList(),
				userFull, tableFull.getGameState().getFirstPlayer());
		 boolean hasToReroll = tableFull.getGameState().getRules().hasToReroll(tableFull.getGameState().getDataList(),
				userFull, tableFull.getGameState().getFirstPlayer());
		 if(!canReroll && !isFirstRoll)
		 {
			 this.comServer.raiseException(uuid,"Le joueur ne peux pas rejouer. Il doit pouvoir rejouer pour rejouer.");
		 }
		 else
		 if(hasToReroll && isStop)
		 {
			 this.comServer.raiseException(uuid,"Le joueur ne peux pas s'arreter. Il est obligï¿½ de rejouer.");
		 }
		 else
		if (isFirstRoll || !isStop) {
			int r1, r2, r3;
			if (d1 || isFirstRoll)
				r1 = Dice();
			else
				r1 = pData.getDices()[0];
			if (d2 || isFirstRoll)
				r2 = Dice();
			else
				r2 = pData.getDices()[1];
			if (d3 || isFirstRoll)
				r3 = Dice();
			else
				r3 = pData.getDices()[2];
			int[] tDice = { r1, r2, r3 };
			pData.setDices(tDice);
			pData.setRerollCount(pData.getRerollCount() + 1);
			tableFull.getGameState().replaceData(pData);
			this.comServer.sendResult(getUUIDList(tableFull.getAllList()), r1, r2, r3);
			gameEngine(tableFull, false);
		} else {
			this.comServer.sendResult(getUUIDList(tableFull.getAllList()), pData.getDices()[0], pData.getDices()[1],
					pData.getDices()[2]);
			gameEngine(tableFull, true);
		}
	}}}

	@Override
	public void hasSelected(UUID uuid, boolean d1, boolean d2, boolean d3) {
		User emptyUser = new User(new Profile(uuid));
		User userFull = emptyUser.getSame(this.usersList);
		 if(userFull==null)
			 this.comServer.raiseException(uuid,"L'utilisateur n'est pas connectï¿½. Il faut ï¿½tre connectï¿½ pour selectionner des dï¿½s.");
		 else{
		 GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
		 if(tableFull==null)
			 this.comServer.raiseException(uuid,"La table n'existe pas. Il faut que la table existe pour y selectionner des dï¿½s.");
		 else
		 if(!tableFull.getGameState().getActualPlayer().isSame(userFull))
			 this.comServer.raiseException(uuid,"Le lanceur de dï¿½s n'est pas le joueur actuel. Il faut ï¿½tre le joueur actuel pour selectionner des dï¿½s.");
		 else
		this.comServer.sendSelection(getUUIDList(tableFull.getAllList()), userFull.getPublicData().getUUID(), d1, d2,
				d3);
	}}

	@Override
	public void connectUser(Profile profile) {
		User newUser = new User(profile);

		//To-Fix : Impossible de créer un full user sans être connecté mais impossible de se connecter sans un full user
		//En attendant que ce problème soit resolue on accepte la connexion pour pouvoir continuer à tester les autres fonctionnalités
		 //if(!newUser.isFullVersion())
		 if(false)
			 this.comServer.raiseException(profile.getUUID(),"Profil non complet lors de la connexion. Profil complet requis.");
		 //else if (newUser.getSame(this.usersList)!=null)
		 else if(false)
			 this.comServer.raiseException(profile.getUUID(),"Profil dï¿½jï¿½ connectï¿½. Veuillez rï¿½essayer dans X minutes");
		 else{
		this.comServer.newUser(getUUIDList(this.usersList), newUser.getEmptyVersion().getPublicData());
		this.usersList.add(newUser);
		this.comServer.sendTablesUsers(this.usersList, this.tableList, newUser.getEmptyVersion().getPublicData());
		 }
	}

	@Override
	public void hasRefusedReplay(UUID uuid) {
		voteReplay(uuid, false);
	}

	@Override
	public void hasAcceptedReplay(UUID uuid) {
		voteReplay(uuid, true);
	}

	public void voteReplay(UUID uuid, boolean answer)
	{
		User userFull = new User(new Profile(uuid)).getSame(this.usersList);
		if(userFull==null)
			this.comServer.raiseException(uuid,"L'utilisateur n'exsite pas, Il doit exister pour voter.");
		else if(userFull.getActualTable()==null)
			this.comServer.raiseException(uuid,"L'utilisateur n'est à aucune table. il doit être à une table pour voter.");
		else{
		GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
		if(tableFull==null)
			this.comServer.raiseException(uuid,"L'utilisateur est à une table qui n'existe pas. La table doit exister pour voter.");
		else if(tableFull.getVote() || tableFull.getGameState().getState()!=State.END)
			this.comServer.raiseException(uuid,"Aucun vote pour rejouer en cours. Un vote doit être en cours pour voter");
		else if(tableFull.getVoteCasted().stream().filter(v->v.getUser().isSame(userFull)).count()!=0)
			this.comServer.raiseException(uuid,"L'utilisateur à déjà voté.");
		else{
		tableFull.castVote(new Vote(userFull.getLightWeightVersion(),answer,tableFull.getLightWeightVersion()));
		if(answer)
			this.comServer.hasAccepted(userFull.getPublicData().getUUID(), getUUIDList(tableFull.getAllList()));
		else
			this.comServer.hasRefused(userFull.getPublicData().getUUID(), getUUIDList(tableFull.getAllList()));
		if(tableFull.getVoteCasted().size()==tableFull.getPlayerList().size())
		{
			if(tableFull.voteResult()==true)
			{
				tableFull.initializeGame();
				this.gameEngine(tableFull, false);
				tableFull.setVote(false);
			}
			else
			{
				this.dropTable(tableFull);
			}
		}
	}}}

	@Override
	public void askRefreshUsersList(User user) {
		this.comServer.refreshUserList(user.getPublicData().getUUID(), getLightweightList(this.usersList));

	}

	// Cette fonction sert ï¿½ savoir ï¿½ partir des donnï¿½es dans quel ï¿½tat on est
	// et ce qu'il faut faire.
	// pour commencer la partie, il necessite un gameState initialisï¿½ (sinon il
	// ne peux pas savoir que c'ets le debut)
	// ï¿½ n'appeler que si il s'est passï¿½ quelquechose
	// fonctionne iterativement.
	private void gameEngine(GameTable table, boolean isStop) {
		GameTable tableFull = table.getSame(this.tableList);
		// if(tableFull == null)
		// throw new Exception("La table n'existe pas. La table doit exister
		// pour jouer dessus.");

		// detecte la phase
		switch (tableFull.getGameState().getState()) {
		case PRESTART:
			// Premier appel debut du jeu
			tableFull.getGameState().setState(State.SELECTION);
			this.comServer.changeState(getUUIDList(tableFull.getAllList()), State.SELECTION);
			// Absence de BREAK INTENTIONNELLE (on passe ï¿½ la suite cela veux
			// dire).

		case SELECTION:
			// il faut choisir le premier joueur en faisant un tour de dï¿½.
			// on lance les dï¿½s en commencant par le premier joueur. ï¿½ la fin on
			// calcule les gagnants.
			// on cree et resoud le Tie.
			// on passe ï¿½ la charge.

			switch (tableFull.getGameState().getTurnState()) {
			case INIT:
				initTurnRoutine(tableFull, true, isStop);
				break;
			case FIRST_ROUND:
				firstRoundRoutine(tableFull, true, isStop);
				break;
			case WINNER_TIE_ROUND:
				winnerRoundRoutine(tableFull, true);
				break;
			case LOSER_TIE_ROUND:
				loserRoundRoutine(tableFull, false);
				break;
			case END:
				tableFull.getGameState().setState(State.CHARGING);
				this.comServer.changeState(getUUIDList(tableFull.getAllList()), State.CHARGING);
				tableFull.getGameState().nextTurn(tableFull.getGameState().getWinners().get(0));
				gameEngine(tableFull, false);
				break;
			default:
				this.comServer.raiseException(getUUIDList(tableFull.getAllList()),"Etat incohï¿½rent. Le systï¿½me ne devrait ï¿½tre dans cet ï¿½tat");
				break;

			}
			break;

		case CHARGING:
			switch (tableFull.getGameState().getTurnState()) {
			case INIT:
				initTurnRoutine(tableFull, true, isStop);
				break;
			case FIRST_ROUND:
				firstRoundRoutine(tableFull, true, isStop);
				break;
			case WINNER_TIE_ROUND:
				winnerRoundRoutine(tableFull, false);
				break;
			case LOSER_TIE_ROUND:
				loserRoundRoutine(tableFull, true);
				break;
			case END:
				PlayerData pDataW = tableFull.getGameState().getData(tableFull.getGameState().getWinners().get(0),
						false);
				PlayerData pDataL = new PlayerData(
						tableFull.getGameState().getData(tableFull.getGameState().getLosers().get(0), false));
				int value = tableFull.getGameState().getRules().getChip(tableFull.getGameState().getDataList());
				if (tableFull.getGameState().getChipStack() < value)
					value = tableFull.getGameState().getChipStack();
				tableFull.getGameState().setChipStack(tableFull.getGameState().getChipStack() - value);
				pDataL.setChip(pDataL.getChip() + value);
				tableFull.getGameState().replaceData(pDataL);
				this.comServer.updateChips(getUUIDList(tableFull.getAllList()),
						pDataL.getPlayer().getPublicData().getUUID(), value);

				if (tableFull.getGameState().getChipStack() == 0) // on passe ï¿½
																	// la
																	// decharge
				{

					// si qqun perd sec
					if (tableFull.getGameState().getDataList().stream().filter(d -> d.getChip() != 0).count() == 1)
					{
						tableFull.getGameState().setState(State.END);

						this.comServer.changeState(getUUIDList(tableFull.getAllList()), State.END);
					}
					else
					{
						tableFull.getGameState().setState(State.DISCHARGING);
						this.comServer.changeState(getUUIDList(tableFull.getAllList()), State.DISCHARGING);
					}

				}
				tableFull.getGameState().nextTurn(pDataL.getPlayer());
				gameEngine(tableFull, false);

				break;
			default:
				this.comServer.raiseException(getUUIDList(tableFull.getAllList()),"Etat incohï¿½rent. Le systï¿½me ne devrait ï¿½tre dans cet ï¿½tat");
				break;

			}
			break;

		case DISCHARGING:
			switch (tableFull.getGameState().getTurnState()) {
			case INIT:
				initTurnRoutine(tableFull, false, isStop);
				break;
			case FIRST_ROUND:
				firstRoundRoutine(tableFull, false, isStop);
				break;
			case WINNER_TIE_ROUND:
				winnerRoundRoutine(tableFull, true);
				break;
			case LOSER_TIE_ROUND:
				loserRoundRoutine(tableFull, true);
				break;
			case END:
				PlayerData pDataW = new PlayerData(
						tableFull.getGameState().getData(tableFull.getGameState().getWinners().get(0), false));
				PlayerData pDataL = new PlayerData(
						tableFull.getGameState().getData(tableFull.getGameState().getLosers().get(0), false));
				int value = tableFull.getGameState().getRules().getChip(tableFull.getGameState().getDataList());
				if (pDataW.getChip() < value)
					value = pDataW.getChip();
				pDataW.setChip(pDataW.getChip() - value);
				pDataL.setChip(pDataL.getChip() + value);
				tableFull.getGameState().replaceData(pDataL);
				this.comServer.updateChips(getUUIDList(tableFull.getAllList()),
						pDataW.getPlayer().getPublicData().getUUID(), pDataL.getPlayer().getPublicData().getUUID(),
						value);

				if (pDataW.getChip() == 0) {
					if (tableFull.getGameState().getWinnerGame() == null)
						tableFull.getGameState().setWinnerGame(pDataW.getPlayer());

					if (tableFull.getGameState().getDataList().stream().filter(d -> d.getChip() != 0).count() == 1)
					{
						tableFull.getGameState().setState(State.END);
						this.comServer.changeState(getUUIDList(tableFull.getAllList()), State.END);
					}

				}
				tableFull.getGameState().nextTurn(pDataL.getPlayer());
				gameEngine(tableFull, false);
				break;
			default:
				this.comServer.raiseException(getUUIDList(tableFull.getAllList()),"Etat incohï¿½rent. Le systï¿½me ne devrait ï¿½tre dans cet ï¿½tat");
				break;

			}
			break;

		case END:
			// fin partie

			if (tableFull.getGameState().getWinnerGame() != null)
				this.comServer.hasWon(getUUIDList(tableFull.getAllList()),
						tableFull.getGameState().getWinnerGame().getPublicData().getUUID());
			else
				this.comServer.hasWon(getUUIDList(tableFull.getAllList()), null);

			PlayerData pDataLoser = tableFull.getGameState().getDataList().stream().filter(d -> d.getChip() != 0)
					.findFirst().get();

			 this.comServer.hasLost(getUUIDList(tableFull.getAllList()),pDataLoser.getPlayer().getPublicData().getUUID());

			this.time.schedule(new EndGameTimer(tableFull,this),1000*2*60);
			break;

		default:
			this.comServer.raiseException(getUUIDList(tableFull.getAllList()),"Etat incohï¿½rent. Le systï¿½me ne devrait ï¿½tre dans cet ï¿½tat");
			break;
		}
	}

	public ComServer getComServer() {
		return comServer;
	}

	public void setComServer(ComServer comServer) {
		this.comServer = comServer;
	}

	public List<GameTable> getTableList() {
		return this.tableList;
	}

	public static List<User> getEmptyList(List<User> userList) {
		List<User> newList = userList.stream().map(u -> u.getEmptyVersion()).collect(Collectors.toList());
		// List<User> newList = new ArrayList<User>();
		// for(User i : userList)
		// newList.add(i.getEmptyVersion());
		return newList;
	}

	public static List<User> getLightweightList(List<User> userList) {
		List<User> newList = userList.stream().map(u -> u.getLightWeightVersion()).collect(Collectors.toList());
		// List<User> newList = new ArrayList<User>();
		// for(User i: userList)
		// newList.add(i.getLightWeightVersion());
		return newList;
	}

	public static List<UUID> getUUIDList(List<User> userList) {
		List<UUID> newList = userList.stream().map(u -> u.getPublicData().getUUID()).collect(Collectors.toList());
		// List<UUID> newList = new ArrayList<UUID>();
		// for(User i : userList)
		// newList.add(i.getPublicData().getUUID());
		return newList;
	}

	private int Dice() {
		int number = -1;
		while (number < 0 && number > 6) // mï¿½thode du rejet
			number = (int) (Math.random() * 6 + 1);
		return number;
	}

	// Game engine factorisation :
	// pour aller plus vite, ï¿½tant donnï¿½ ces fonctions privï¿½es, on envoie une
	// tableFull necessairement

	private void initTurnRoutine(GameTable tableFull, boolean isSec, boolean isStop) {
		if (isSec) {
			tableFull.getGameState().setTurnState(TurnState.FIRST_ROUND);
			this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.FIRST_ROUND);
			this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
					tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);

		} else {
			boolean canReroll = tableFull.getGameState().getRules().canReroll(tableFull.getGameState().getDataList(),
					tableFull.getGameState().getActualPlayer(), tableFull.getGameState().getFirstPlayer());
			boolean hasToReroll = tableFull.getGameState().getRules().hasToReroll(
					tableFull.getGameState().getDataList(), tableFull.getGameState().getActualPlayer(),
					tableFull.getGameState().getFirstPlayer());

			if (!(tableFull.getGameState().getData(tableFull.getGameState().getActualPlayer(), false)
					.getRerollCount() == 0) // si il a dï¿½jï¿½ commencï¿½
					&& ((isStop && !hasToReroll) || !canReroll)) // et qu'il
																	// veut ou
																	// doit
																	// arreter
			{
				// on change le joueur et l'ï¿½tat
				tableFull.getGameState().setActualPlayer(tableFull.getGameState().getNextPlayer());
				tableFull.getGameState().setTurnState(TurnState.FIRST_ROUND);
				this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.FIRST_ROUND);
			}
			this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
					tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);

		}
	}

	private void firstRoundRoutine(GameTable tableFull, boolean isSec, boolean isStop) {
		if (isSec) {
			// la fonction gameEngine est appelï¿½ de Onethrow , il faut cahnger
			// de joueur
			tableFull.getGameState().setActualPlayer(tableFull.getGameState().getNextPlayer());
			if (tableFull.getGameState().getDataList().stream().filter(d -> d.getRerollCount() != 0).count() != 0) // alors
																													// on
																													// n'a
																													// pas
																													// fini
																													// de
																													// distribuer
			{
				this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
						tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);
			} else // On a fini de dsitribuer
			{
				tableFull.getGameState().setTurnState(TurnState.WINNER_TIE_ROUND);
				this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.WINNER_TIE_ROUND);
				gameEngine(tableFull, false); // pour passer ï¿½ la phase suivante
												// sans trop de souci.
			}
		} else {
			boolean canReroll = tableFull.getGameState().getRules().canReroll(tableFull.getGameState().getDataList(),
					tableFull.getGameState().getActualPlayer(), tableFull.getGameState().getFirstPlayer());
			boolean hasToReroll = tableFull.getGameState().getRules().hasToReroll(
					tableFull.getGameState().getDataList(), tableFull.getGameState().getActualPlayer(),
					tableFull.getGameState().getFirstPlayer());

			if (!(tableFull.getGameState().getData(tableFull.getGameState().getActualPlayer(), false)
					.getRerollCount() == 0) // si il a dï¿½jï¿½ commencï¿½
					&& ((isStop && !hasToReroll) || !canReroll)) // et qu'il
																	// veut ou
																	// doit
																	// arreter
			{
				if (tableFull.getGameState().getDataList().stream().filter(d -> d.getRerollCount() != 0).count() != 0) // et
																														// que
																														// l'
																														// on
																														// n'a
																														// pas
																														// fini
																														// de
																														// distribuer
				{
					// on change le joueur
					tableFull.getGameState().setActualPlayer(tableFull.getGameState().getNextPlayer());
					this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
							tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);
				} else {
					// sinon on change d'ï¿½tat
					tableFull.getGameState().setTurnState(TurnState.WINNER_TIE_ROUND);
					this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.WINNER_TIE_ROUND);
					gameEngine(tableFull, false);
				}

			} else // ou alors on continue avec le meme joueur
				this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
						tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);
		}

	}

	private void winnerRoundRoutine(GameTable tableFull, boolean calculateWinners) {
		if (calculateWinners) {
			if (tableFull.getGameState().getWinners() == null) {
				// on calcule les winners
				tableFull.getGameState().setWinners(
						tableFull.getGameState().getRules().getWinner(tableFull.getGameState().getDataList()));

				if (tableFull.getGameState().getWinners().size() != 1) {
					// on preinit le tie
					List<PlayerData> newList = new ArrayList<PlayerData>();
					for (User u : tableFull.getGameState().getWinners())
						newList.add(new PlayerData(u));
					tableFull.getGameState().setActualPlayer(tableFull.getGameState().getWinners().get(0));
					tableFull.getGameState().setFirstPlayer(tableFull.getGameState().getWinners().get(0));
					// this.comServer.exAequoCase(getUuidList(tableFull.getAllList()),
					// tableFull.getGameState().getWinners(),true);
				}
			}
			if (tableFull.getGameState().getWinners().size() == 1) // un seul
																	// winner la
																	// solution
																	// facile
			{
				tableFull.getGameState().setTurnState(TurnState.LOSER_TIE_ROUND);
				this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.LOSER_TIE_ROUND);
				gameEngine(tableFull, false); // pour passer ï¿½ la phase suivante
												// sans trop de souci.
				return;
			}
			if (tableFull.getGameState().getDataTieList().stream().filter(d -> d.getRerollCount() != 0).count() != 0) // alors
																														// on
																														// n'a
																														// pas
																														// fini
																														// de
																														// distribuer
			{
				this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
						tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);
				tableFull.getGameState().setActualPlayer(tableFull.getGameState().getNextPlayer());
			} else {
				// on recalcule les winners
				tableFull.getGameState().setWinners(
						tableFull.getGameState().getRules().getWinner(tableFull.getGameState().getDataList()));
				gameEngine(tableFull, false);
				return;
			}
		} else {
			tableFull.getGameState().setTurnState(TurnState.LOSER_TIE_ROUND);
			this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.LOSER_TIE_ROUND);
			gameEngine(tableFull, false); // pour passer ï¿½ la phase suivante
											// sans trop de souci.
		}

	}

	private void loserRoundRoutine(GameTable tableFull, boolean calculateLosers) {
		if (calculateLosers) {
			if (tableFull.getGameState().getLosers() == null) {
				// on calcule les losers
				tableFull.getGameState().setLosers(
						tableFull.getGameState().getRules().getLoser(tableFull.getGameState().getDataList()));

				if (tableFull.getGameState().getLosers().size() != 1) {
					// on preinit le tie
					List<PlayerData> newList = new ArrayList<PlayerData>();
					for (User u : tableFull.getGameState().getWinners())
						newList.add(new PlayerData(u));
					tableFull.getGameState().setActualPlayer(tableFull.getGameState().getLosers().get(0));
					tableFull.getGameState().setFirstPlayer(tableFull.getGameState().getLosers().get(0));
					// this.comServer.exAequoCase(getUuidList(tableFull.getAllList()),
					// tableFull.getGameState().getLosers(),false);
				}
			}
			if (tableFull.getGameState().getLosers().size() == 1) // un seul
																	// winner la
																	// solution
																	// facile
			{
				tableFull.getGameState().setTurnState(TurnState.END);
				this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.END);
				gameEngine(tableFull, false); // pour passer ï¿½ la phase suivante
												// sans trop de souci.
				return;
			}
			if (tableFull.getGameState().getDataTieList().stream().filter(d -> d.getRerollCount() != 0).count() != 0) // alors
																														// on
																														// n'a
																														// pas
																														// fini
																														// de
																														// distribuer
			{
				this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
						tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);
				tableFull.getGameState().setActualPlayer(tableFull.getGameState().getNextPlayer());
			} else {
				// on recalcule les losers
				tableFull.getGameState().setLosers(
						tableFull.getGameState().getRules().getLoser(tableFull.getGameState().getDataList()));
				gameEngine(tableFull, false);
				return;
			}
		} else {
			tableFull.getGameState().setTurnState(TurnState.END);
			this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.END);
			gameEngine(tableFull, false); // pour passer ï¿½ la phase suivante
											// sans trop de souci.
		}

	}

	@Override
	public void askQuitTable(UUID tableID,UUID user) {

		User userFull = new User(new Profile(user)).getSame(this.usersList);
		if(userFull==null)
			this.comServer.raiseException(user,"L'utilisateur n'exsite pas, Il doit exister pour demander de quitter une table.");
		else
		if(userFull.getActualTable()==null)
			this.comServer.raiseException(user,"L'utilisateur n'est à aucune table. il doit être à une table pour demander de la quitter.");
		else{
		GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
		if(tableFull==null)
			this.comServer.raiseException(user,"L'utilisateur est à une table qui n'existe pas. La table doit exister pour être quittée.");
		else
		if(tableFull.getVote())
			this.comServer.raiseException(user,"Vote déjà en cours");
		else if(tableFull.getCreator().getPublicData().getUUID() != user)
			this.comServer.raiseException(user,"Vous n'etes pas le créateur de la table.");
		else{
		tableFull.startVote();
		this.comServer.askStopGameEveryUser(getUUIDList(tableFull.getPlayerList()));
		//toReviesw ajouter un timer pour evteul cas on des gens ne votent pas.
	}}}


	@Override
	public void answerStopGame(UUID tableID, boolean answer, UUID user) {
		User userFull = new User(new Profile(user)).getSame(this.usersList);
		if(userFull==null)
			this.comServer.raiseException(user,"L'utilisateur n'exsite pas, Il doit exister pour voter.");
		else if(userFull.getActualTable()==null)
			this.comServer.raiseException(user,"L'utilisateur n'est à aucune table. il doit être à une table pour voter.");
		else {
		GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
		if(tableFull==null)
			this.comServer.raiseException(user,"L'utilisateur est à une table qui n'existe pas. La table doit exister pour voter.");
		else if(tableFull.getVoteCasted() == null)
			this.comServer.raiseException(user,"Aucun vote en cours. Un vote doit être en cours pour voter");
		else {
		tableFull.castVote(new Vote(userFull.getLightWeightVersion(),answer,tableFull.getLightWeightVersion()));
		if(tableFull.getVoteCasted().size()==tableFull.getPlayerList().size()-1)
		{
			this.comServer.stopGame(getUUIDList(tableFull.getAllList()),tableFull.voteResult());
			if(tableFull.voteResult()==true)
			{
				 for(User u : tableFull.getAllList())
				 {
					 User userFull2 = u.getSame(this.usersList);
					 if(userFull2==null)
						 this.comServer.raiseException(user,"L'utilisateur n'est pas connecté. Il faut être connecté pour se faire kick d'une table.");
					 else
						 userFull2.setActualTable(null);
				 }
				 this.tableList.remove(tableFull);
			}
			else
			{
				tableFull.setCreator(tableFull.getPlayerList().get(0));
				this.comServer.setCreator(getUUIDList(tableFull.getAllList()),tableFull.getCreator().getPublicData().getUUID());
			}
		}}

	}}


}
