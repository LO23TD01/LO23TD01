package data.server;

import java.util.ArrayList;
import java.util.List;
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
import network.server.ComServer;

public class ServerDataEngine implements InterfaceDataNetwork {
	private List<User> usersList;
	private List<GameTable> tableList;

	private ComServer comServer;

	/*
	 *
	 * Constructor
	 *
	 */

	public ServerDataEngine() {
		this.usersList = new ArrayList<>();
		this.tableList = new ArrayList<>();
		this.comServer = null;
	}

	/*
	 *
	 * Methods
	 *
	 */

	public GameTable createTable(User user, String name, Parameters params) {

		// Initialisation des joueurs de la table avec le joueur cr�ant la table
		List<User> playerList = new ArrayList<User>();
		playerList.add(user);

		// Initialisation spectateurs, vide au d�but
		List<User> spectatorList = new ArrayList<User>();

		return new GameTable(name, user, params, playerList, spectatorList);
	}

	private void startLaunchTimer(GameTable table) // Edit : ajout de la
													// GameTable. Il faut savoir
													// quelle table lancer ...
	{
		GameTable tableFull = table.getSame(this.tableList);
		// if(tableFull==null)
		// throw new Exception("La table n'existe pas. Il faut que la table
		// existe pour s'y connecter.");
		tableFull.initializeGame();

		this.selectFirstPlayer(tableFull);
		this.comServer.showTimer(getUUIDList(table.getAllList()));
	}

	public boolean closeTable(GameTable table) {
		// TO-DO : Fermeture de la table
		return false;
	}

	// Deprecated : Execut� dans le GameEngine, ne jamais apeller
	private void selectFirstPlayer(GameTable table) // Edit : ajout de la
													// GameTable. Il faut savoir
													// quelle table lancer ...
	{
		// throw new Exception("Deprecated function");
	}

	public void connect(User user) {
		// TO-DO : Connexion d'un user
	}

	public void disconnect(User user) {
		user.getActualTable().disconnect(user);
		this.usersList.remove(user.getSame(this.usersList));
	}

	/*
	 *
	 * Implemented Methods
	 *
	 */

	@Override
	public Profile getProfile(User user) {
		// if(user.getSame(this.usersList)==null)
		// throw new Exception("Utilisateur non connect�. Il faut qu'il soit
		// connect� pour retrouver son profil.");
		return user.getSame(this.usersList).getPublicData();
	}

	@Override
	public void updateUserProfile(UUID uuid, Profile profile) {
		// uuid useless (il est d�j� dans le profile bande de bananes.)

		User compUser = new User(profile);
		// if(!compUser.isFullVersion())
		// throw new Exception("Profil non complet lors de la mise � jour. Il
		// faut un profil complet");
		// else if(compUser.getSame(this.usersList)==null)
		// throw new Exception("Profil non connect�. Il faut que le profil soit
		// connect� pour le mettre � jour.");
		compUser.getSame(this.usersList).setPublicData(profile);
		// La suite du diag de sequence a �t� overrided par l'avis du prof.
	}

	@Override
	public void sendMessage(ChatMessage message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropTable(GameTable table) {
		// TODO Auto-generated method stub

	}

	@Override
	public void quit(User user, GameTable table) {
		// TODO Auto-generated method stub

	}

	@Override
	public void askJoinTable(User user, GameTable table, boolean isPlayer) {
		GameTable tableFull = table.getSame(this.tableList);
		User userFull = user.getSame(this.usersList);
		// if(tableFull==null)
		// throw new Exception("La table n'existe pas. Il faut que la table
		// existe pour s'y connecter.");
		// else if(userFull==null)
		// throw new Exception("L'utilisateur n'est pas connect�. Il faut �tre
		// connect� pour rejoindre une table.");

		boolean isLaunched = tableFull.getGameState().getState() != State.PRESTART;
		boolean isFull = tableFull.getParameters().getNbPlayerMax() >= tableFull.getPlayerList().size();
		boolean isSpecAuthorized = tableFull.getParameters().isAuthorizeSpec();

		if (tableFull.connect(user, isPlayer)) {
			// Success
			user.setActualTable(tableFull.getEmptyVersion());
			user.setSpectating(!isPlayer);
			if (isPlayer)
				this.comServer.newPlayerOnTable(getUUIDList(tableFull.getAllList()), user.getPublicData(),
						tableFull);
			else
				this.comServer.newPlayerOnTable(getUUIDList(tableFull.getAllList()), user.getPublicData(),
						tableFull);
		} else if (isPlayer && isLaunched)
			this.comServer.raiseException(user.getPublicData().getUUID(),
					"Impossible de rejoindre une partie d�j� commenc�e.");
		else if (isPlayer && isFull)
			this.comServer.raiseException(user.getPublicData().getUUID(), "Impossible de rejoindre une partie pleine.");
		else if (!isPlayer && !isSpecAuthorized)
			this.comServer.raiseException(user.getPublicData().getUUID(),
					"Impossible de regarder cette partie. Non Autoris� par le Cr�ateur.");
		else {
			// la table n'a pas reussi � connecter le nouveau user malgr� nos
			// test en ammonts
			// TOREVIEW est ce que c'est une bonne id�e d'afficher l'erreur de
			// cette mani�re au client ? Le fiat est que si on le fait pas, il
			// attendra de mani�re infinie d'apres nos diag de sequence.
			this.comServer.raiseException(user.getPublicData().getUUID(),
					"Erreur inconnue lors de la connexion � la table");
			// throw new Exception("Erreur inconnue lors de la connexion � la
			// table");
		}

		// //seconde implementation possible :
		//
		// if(isPlayer)
		// {
		// if(tableFull.connect(user,isPlayer))
		// {
		// user.setActualTable(tableFull.getEmptyVersion());
		// user.setSpectating(!isPlayer);
		// this.comServer.newPlayerOnTable(getUUIDList(tableFull.getAllList()),
		// user.getPublicData(), tableFull.getUid());
		// }
		// else if(isLaunched)
		// this.comServer.raiseException(user.getPublicData().getUUID(),
		// "Impossible de rejoindre une partie d�j� commenc�e.");
		// else if(isFull)
		// this.comServer.raiseException(user.getPublicData().getUUID(),
		// "Impossible de rejoindre une partie pleine.");
		// else
		// {
		// this.comServer.raiseException(user.getPublicData().getUUID(), "Erreur
		// inconnue lors de la connexion � la table");
		// // throw new Exception("Erreur inconnue lors de la connexion � la
		// table");
		// }
		// }
		// else
		// {
		// if(tableFull.connect(user,isPlayer))
		// {
		// //Success
		// user.setActualTable(tableFull.getEmptyVersion());
		// user.setSpectating(!isPlayer);
		// this.comServer.newPlayerOnTable(getUUIDList(tableFull.getAllList()),
		// user.getPublicData(), tableFull.getUid());
		// }
		// else if(!isSpecAuthorized)
		// this.comServer.raiseException(user.getPublicData().getUUID(),
		// "Impossible de regarder cette partie. Non Autoris� par le
		// Cr�ateur.");
		// else
		// {
		// this.comServer.raiseException(user.getPublicData().getUUID(), "Erreur
		// inconnue lors de la connexion � la table");
		// // throw new Exception("Erreur inconnue lors de la connexion � la
		// table");
		// }
		// }

	}

	@Override
	public void launchGame(User user) {
		User userFull = user.getSame(this.usersList);
		// if(userFull==null)
		// throw new Exception("L'utilisateur n'est pas connect�. Il faut �tre
		// connect� pour lancer une partie.");
		// else if(userFull.getActualTable()==null)
		// throw new Exception("L'utilisateur n'a rejoint aucune table. Il faut
		// �tre assit � une table pour lancer une partie.")
		// else if(!userFull.getActualTable().getCreator().isSame(user))
		// throw new Exception("L'utilisateur n'est pas le createur de sa
		// partie. Il faut �tre le createur pour lancer une partie.");
		GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
		// if(tableFull==null)
		// throw new Exception("La table n'existe pas. Il faut que la table
		// existe pour s'y connecter.");
		tableFull.initializeGame();
		this.startLaunchTimer(userFull.getActualTable());
		gameEngine(tableFull, false);

	}

	@Override
	public void createNewTable(User user, String name, Parameters params) {
		GameTable newTable;
		try {
			newTable = createTable(user, name, params);
			this.tableList.add(newTable);
			user.setActualTable(newTable.getEmptyVersion());
			user.setSpectating(false);
			this.comServer.addNewTable(user.getPublicData().getUUID(), getUUIDList(this.usersList), newTable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void disconnectUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	// boolean true signifie relancer le d�
	public void hasThrown(UUID uuid, boolean d1, boolean d2, boolean d3) {
		User emptyUser = new User(new Profile(uuid));
		User userFull = emptyUser.getSame(this.usersList);
		// if(userFull==null)
		// throw new Exception("L'utilisateur n'est pas connect�. Il faut �tre
		// connect� pour lancer les d�s.");
		GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
		// if(tableFull==null)
		// throw new Exception("La table n'existe pas. Il faut que la table
		// existe pour y lancer les d�s.");

		// if(!tableFull.getGameState().getActualPlayer().isSame(userFull))
		// throw new Exception("Le lanceur de d�s n'est pas le joueur actuel. Il
		// faut �tre le joueur actuel pour lancer les d�s.");
		boolean tie = (tableFull.getGameState().getTurnState() == TurnState.LOSER_TIE_ROUND
				|| tableFull.getGameState().getTurnState() == TurnState.WINNER_TIE_ROUND);
		PlayerData pData = new PlayerData(tableFull.getGameState().getData(userFull, tie));
		boolean isFirstRoll = (pData.getRerollCount() == 0);
		boolean isStop = !(d1 || d2 || d3);
		boolean canReroll = tableFull.getGameState().getRules().canReroll(tableFull.getGameState().getDataList(),
				userFull, tableFull.getGameState().getFirstPlayer());
		boolean hasToReroll = tableFull.getGameState().getRules().hasToReroll(tableFull.getGameState().getDataList(),
				userFull, tableFull.getGameState().getFirstPlayer());
		// if(!canReroll && !isFirstRoll)
		// {
		// throw new Exception("Le joueur ne peux pas rejouer. Il doit pouvoir
		// rejouer pour rejouer.");
		// }
		// if(hasToReroll && isStop)
		// {
		// throw new Exception("Le joueur ne peux pas s'arreter. Il est oblig�
		// de rejouer.");
		// }

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
	}

	@Override
	public void hasSelected(UUID uuid, boolean d1, boolean d2, boolean d3) {
		User emptyUser = new User(new Profile(uuid));
		User userFull = emptyUser.getSame(this.usersList);
		// if(userFull==null)
		// throw new Exception("L'utilisateur n'est pas connect�. Il faut �tre
		// connect� pour selectionner des d�s.");
		GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
		// if(tableFull==null)
		// throw new Exception("La table n'existe pas. Il faut que la table
		// existe pour y selectionner des d�s.");

		// if(!tableFull.getGameState().getActualPlayer().isSame(userFull))
		// throw new Exception("Le lanceur de d�s n'est pas le joueur actuel. Il
		// faut �tre le joueur actuel pour selectionner des d�s.");

		this.comServer.sendSelection(getUUIDList(tableFull.getAllList()), userFull.getPublicData().getUUID(), d1, d2,
				d3);
	}

	@Override
	public void connectUser(Profile profile) {
		User newUser = new User(profile);

		// if(!newUser.isFullVersion())
		// throw new Exception("Profil non complet lors de la connexion. Profil
		// complet requis.");
		// else if (newUser.getSame(this.usersList)!=null)
		// throw new Exception("Profil d�j� connect�. Veuillez r�essayer dans X
		// minutes");

		this.comServer.newUser(getUUIDList(this.usersList), newUser.getEmptyVersion().getPublicData());
		this.usersList.add(newUser);
		this.comServer.sendTablesUsers(this.usersList, this.tableList, newUser.getEmptyVersion().getPublicData());

	}

	@Override
	public void hasRefusedReplay(UUID uuid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasAcceptedReplay(UUID uuid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void askRefreshUsersList(User user) {

		// TOREVIEW : on pourrait tester d'abord que le user est connect�, mais
		// j'imagine plus que le network va le faire car c'ets critique pour
		// eux.
		this.comServer.refreshUserList(user.getPublicData().getUUID(), getLightweightList(this.usersList));

	}

	// Cette fonction sert � savoir � partir des donn�es dans quel �tat on est
	// et ce qu'il faut faire.
	// pour commencer la partie, il necessite un gameState initialis� (sinon il
	// ne peux pas savoir que c'ets le debut)
	// � n'appeler que si il s'est pass� quelquechose
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
			// Absence de BREAK INTENTIONNELLE (on passe � la suite cela veux
			// dire).

		case SELECTION:
			// il faut choisir le premier joueur en faisant un tour de d�.
			// on lance les d�s en commencant par le premier joueur. � la fin on
			// calcule les gagnants.
			// on cree et resoud le Tie.
			// on passe � la charge.

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
				tableFull.getGameState().nextTurn(tableFull.getGameState().getWinners().get(0));
				gameEngine(tableFull, false);
				break;
			default:
				// throw new Exception("Etat incoh�rent. Le syst�me ne devrait
				// �tre dans cet �tat");
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

				if (tableFull.getGameState().getChipStack() == 0) // on passe �
																	// la
																	// decharge
				{
					tableFull.getGameState().setState(State.DISCHARGING);
					// si qqun perd sec
					if (tableFull.getGameState().getDataList().stream().filter(d -> d.getChip() != 0).count() == 1)
						tableFull.getGameState().setState(State.END);

				}
				tableFull.getGameState().nextTurn(pDataL.getPlayer());
				gameEngine(tableFull, false);

				break;
			default:
				// throw new Exception("Etat incoh�rent. Le syst�me ne devrait
				// �tre dans cet �tat");
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
						tableFull.getGameState().setState(State.END);
				}
				tableFull.getGameState().nextTurn(pDataL.getPlayer());
				gameEngine(tableFull, false);
				break;
			default:
				// throw new Exception("Etat incoh�rent. Le syst�me ne devrait
				// �tre dans cet �tat");
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
			// TODO : coder hasLost
			// this.comServer.hasLost(getUUIDList(tableFull.getAllList()),pDataLoser.getPlayer().getPublicData().getUUID());
			break;

		default:
			// throw new Exception("Etat incoh�rent. Le syst�me ne devrait �tre
			// dans cet �tat");
			break;
		}
	}

	public ComServer getComServer() {
		return comServer;
	}

	public void setComServer(ComServer comServer) {
		this.comServer = comServer;
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
		while (number < 0 && number > 6) // m�thode du rejet
			number = (int) (Math.random() * 6 + 1);
		return number;
	}

	// Game engine factorisation :
	// pour aller plus vite, �tant donn� ces fonctions priv�es, on envoie une
	// tableFull necessairement

	private void initTurnRoutine(GameTable tableFull, boolean isSec, boolean isStop) {
		if (isSec) {
			tableFull.getGameState().setTurnState(TurnState.FIRST_ROUND);
			this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
					tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);

		} else {
			boolean canReroll = tableFull.getGameState().getRules().canReroll(tableFull.getGameState().getDataList(),
					tableFull.getGameState().getActualPlayer(), tableFull.getGameState().getFirstPlayer());
			boolean hasToReroll = tableFull.getGameState().getRules().hasToReroll(
					tableFull.getGameState().getDataList(), tableFull.getGameState().getActualPlayer(),
					tableFull.getGameState().getFirstPlayer());

			if (!(tableFull.getGameState().getData(tableFull.getGameState().getActualPlayer(), false)
					.getRerollCount() == 0) // si il a d�j� commenc�
					&& ((isStop && !hasToReroll) || !canReroll)) // et qu'il
																	// veut ou
																	// doit
																	// arreter
			{
				// on change le joueur et l'�tat
				tableFull.getGameState().setActualPlayer(tableFull.getGameState().getNextPlayer());
				tableFull.getGameState().setTurnState(TurnState.FIRST_ROUND);
			}
			this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
					tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);

		}
	}

	private void firstRoundRoutine(GameTable tableFull, boolean isSec, boolean isStop) {
		if (isSec) {
			// la fonction gameEngine est appel� de Onethrow , il faut cahnger
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
				gameEngine(tableFull, false); // pour passer � la phase suivante
												// sans trop de souci.
			}
		} else {
			boolean canReroll = tableFull.getGameState().getRules().canReroll(tableFull.getGameState().getDataList(),
					tableFull.getGameState().getActualPlayer(), tableFull.getGameState().getFirstPlayer());
			boolean hasToReroll = tableFull.getGameState().getRules().hasToReroll(
					tableFull.getGameState().getDataList(), tableFull.getGameState().getActualPlayer(),
					tableFull.getGameState().getFirstPlayer());

			if (!(tableFull.getGameState().getData(tableFull.getGameState().getActualPlayer(), false)
					.getRerollCount() == 0) // si il a d�j� commenc�
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
					// sinon on change d'�tat
					tableFull.getGameState().setTurnState(TurnState.WINNER_TIE_ROUND);
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
				gameEngine(tableFull, false); // pour passer � la phase suivante
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
			gameEngine(tableFull, false); // pour passer � la phase suivante
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
				gameEngine(tableFull, false); // pour passer � la phase suivante
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
			gameEngine(tableFull, false); // pour passer � la phase suivante
											// sans trop de souci.
		}

	}

}
