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
	private List<EndGameTimer> timerList;

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
		this.timerList = new ArrayList<EndGameTimer>();
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
		 if(tableFull==null)
			 System.out.println("Failure Launch Timer");//this.comServer.raiseException(getUUIDList(tableFull.getAllList()), "La table n'existe pas. Il faut que la table existe pour s'y connecter.");
		 else
			 this.comServer.showTimer(getUUIDList(table.getAllList()));
	}


	/*
	 *
	 * Implemented Methods
	 *
	 */

	public boolean exist(User user){
		return user.getSame(this.usersList) != null;
	}

	@Override
	public Profile getProfile(User user) {
		 return user.getSame(this.usersList).getPublicData();
	}

	//normalement pas appel�
	@Override
	public void updateUserProfile(UUID uuid, Profile profile) {
		// uuid useless (il est d�j� dans le profile bande de bananes.)

		User compUser = new User(profile);
		 if(!compUser.isFullVersion())
			 this.comServer.raiseException(uuid,"Profil non complet lors de la mise � jour. Il faut un profil complet");
		 else if(compUser.getSame(this.usersList)==null)
			 this.comServer.raiseException(uuid,"Profil non connect�. Il faut que le profil soit		 connect� pour le mettre � jour.");
		 else
			 {
			 	compUser.getSame(this.usersList).setPublicData(profile);
			 	// La suite du diag de sequence a �t� overrided par l'avis du prof.
				// ensuite il a �t� re-overrided pour etre reinclus
			 	this.comServer.sendProfileUpdate(getUUIDList(this.usersList), compUser.getPublicData().getUUID(), compUser.getPublicData());
			 }

	}

	@Override
	public void sendMessage(ChatMessage message) {
		User userFull = message.getSender().getSame(this.usersList);
		if(userFull==null)
			this.comServer.raiseException(message.getSender().getPublicData().getUUID(),"L'utilisateur n'exsite pas, Il doit exister pour parler.");
		else if(userFull.getActualTable()==null)
			this.comServer.raiseException(message.getSender().getPublicData().getUUID(),"L'utilisateur n'est � aucune table. il doit �tre � une table pour parler.");
		else {
			GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
			if(tableFull==null)
				this.comServer.raiseException(message.getSender().getPublicData().getUUID(),"L'utilisateur est � une table qui n'existe pas. La table doit exister pour parler.");
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
		 if(tableFull==null)
			 System.out.println("Fail Drop table");//throw new Exception("La table n'existe pas. Il faut que la table existe pour la d�truire.");
		 else
		 {
			 for(User u : tableFull.getAllList())
			 {
				 User userFull = u.getSame(this.usersList);
				 if(userFull==null)
					 this.comServer.raiseException(u.getPublicData().getUUID(),"L'utilisateur n'est pas connect�. Il faut �tre connect� pour se faire kick d'une table.");
				 else
					 userFull.setActualTable(null);
			 }
			 this.comServer.kick(getUUIDList(tableFull.getAllList()), "La partie n'existe plus.");
			 this.tableList.remove(tableFull);
			 this.comServer.refreshTableList(getUUIDList(this.usersList),this.tableList);
		 }
	}


	//arguement Table inutile
	@Override
	public void quit(User user, GameTable table) {
		User userFull = user.getSame(this.usersList);
		 if(userFull==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'est pas connect�. Il faut �tre connect� pour quitter une table.");

		 else if(userFull.getActualTable()==null)
			 this.comServer.raiseException(userFull.getPublicData().getUUID(),"L'utilisateur n'est pas � une table. Il dtoi �tre � une table pour la quitter.");
		 else{
			 GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
			 if(tableFull==null)
				 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur est � une table qui n'existe pas. La table doit exister pour quitter la table..");
			 else{
				 	boolean actuelQuit = userFull.isSame(tableFull.getGameState().getActualPlayer());
					tableFull.disconnect(userFull);
					userFull.setActualTable(null);
					this.comServer.playerQuitGame(getUUIDList(tableFull.getAllList()), userFull.getPublicData().getUUID());
					 this.comServer.refreshTableList(getUUIDList(this.usersList),this.tableList);
					 //engine en premier
					 if(actuelQuit && (tableFull.getGameState().getState() != State.PRESTART && tableFull.getGameState().getState() != State.END))
						 gameEngine(tableFull,false);
					 //debloquer vote si c'est le cas
						if(tableFull.getVote())
						{
							if(tableFull.getGameState().getState()== State.END)
								this.testVoteReplay(tableFull);
							else
								this.testVoteStop(tableFull);
						}
					 //drop table vide et test createur
					 if(tableFull.getGameState().getState() != State.PRESTART && tableFull.getPlayerList().size()<2)
					 {
							//kick les spec
							for(User u : tableFull.getAllList())
							{
								u.setActualTable(null);
							}
							this.comServer.kick(getUUIDList(tableFull.getAllList()), "Plus personne ne joue.");
							//et drop la tbale
							this.tableList.remove(tableFull);
							 this.comServer.refreshTableList(getUUIDList(this.usersList),this.tableList);
					 }
					 else if(tableFull.getPlayerList().size()==0)
					{
						//kick les spec
						for(User u : tableFull.getAllList())
						{
							u.setActualTable(null);
						}
						this.comServer.kick(getUUIDList(tableFull.getAllList()), "La partie n'existe plus.");
						//et drop la tbale
						this.tableList.remove(tableFull);
						 this.comServer.refreshTableList(getUUIDList(this.usersList),this.tableList);
					}
					else if(tableFull.getCreator().isSame(user))
						{
							//changer creator
							if(tableFull.getGameState().getState() == State.PRESTART || tableFull.getGameState().getState() == State.END)
								this.dropTable(tableFull);
							else
							{
								tableFull.setCreator(tableFull.getPlayerList().get(0));
								this.askQuitTable(tableFull);
							}
						}
				}
		 }
	}

	@Override
	public void askJoinTable(User user, GameTable table, boolean isPlayer) {
		GameTable tableFull = table.getSame(this.tableList);
		User userFull = user.getSame(this.usersList);
		if(tableFull==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"La table n'existe pas. Il faut que la table existe pour s'y connecter.");
		else if(userFull==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'est pas connect�. Il faut �tre connect� pour rejoindre une table.");
		else
		{
			boolean isLaunched = tableFull.getGameState().getState() != State.PRESTART;
			boolean isFull = tableFull.getParameters().getNbPlayerMax() <= tableFull.getPlayerList().size();
			boolean isSpecAuthorized = tableFull.getParameters().isAuthorizeSpec();

			if (tableFull.connect(userFull, isPlayer)) {
				// Success
				userFull.setActualTable(tableFull.getEmptyVersion());
				userFull.setSpectating(!isPlayer);

				List<UUID> currentReceivers = getUUIDList(tableFull.getAllList());
				currentReceivers.remove(user.getPublicData().getUUID());
				if (isPlayer)
					this.comServer.newPlayerOnTable(currentReceivers, userFull.getPublicData(), tableFull);
				else
					this.comServer.newSpectatorOnTable(currentReceivers, userFull.getPublicData(), tableFull);

				 this.comServer.refreshTableList(getUUIDList(this.usersList),this.tableList);

			} else if (isPlayer && isLaunched)
				this.comServer.raiseException(userFull.getPublicData().getUUID(),
						"Impossible de rejoindre une partie d�j� commenc�e.");
			else if (isPlayer && isFull)
				this.comServer.raiseException(userFull.getPublicData().getUUID(), "Impossible de rejoindre une partie pleine.");
			else if (!isPlayer && !isSpecAuthorized)
				this.comServer.raiseException(userFull.getPublicData().getUUID(),"Impossible de regarder cette partie. Non Autoris� par le Cr�ateur.");
			else {
				// la table n'a pas reussi � connecter le nouveau user malgr� nos
				// test en ammonts
				// TOREVIEW est ce que c'est une bonne id�e d'afficher l'erreur de
				// cette mani�re au client ? Le fiat est que si on le fait pas, il
				// attendra de mani�re infinie d'apres nos diag de sequence.
				this.comServer.raiseException(userFull.getPublicData().getUUID(),
						"Erreur inconnue lors de la connexion � la table");
				// throw new Exception("Erreur inconnue lors de la connexion � la
				// table");
			}
		}
	}

	@Override
	public void launchGame(User user) {
		User userFull = user.getSame(this.usersList);
		 if(userFull==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'est pas connect�. Il faut �tre connect� pour lancer une partie.");
		 else if(userFull.getActualTable()==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'a rejoint aucune table. Il faut �tre assit � une table pour lancer une partie.");
		 else {
			 GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
			 if(tableFull==null)
				 this.comServer.raiseException(user.getPublicData().getUUID(),"La table n'existe pas. Il faut que la table existe pour s'y connecter.");
			 else if(!tableFull.getCreator().isSame(user))
				 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'est pas le createur de sa partie. Il faut �tre le createur pour lancer une partie.");
			 else if(!(tableFull.getGameState().getState()==State.PRESTART))//fix car ihmtable lance la partie en permmanence  // || tableFull.getGameState().getState()==State.END))
				 this.comServer.raiseException(user.getPublicData().getUUID(),"Ce n'est pas le moment de lancer une partie.");
			 else
			 {
				 tableFull.initializeGame();
				 this.startLaunchTimer(userFull.getActualTable());
				 gameEngine(tableFull, false);
				 this.comServer.refreshTableList(getUUIDList(this.usersList),this.tableList);
			 }
		 }
	}

	@Override
	public void createNewTable(User user, String name, Parameters params) {
		User userFull = user.getSame(this.usersList);
		 if(userFull==null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur n'est pas connect�. Il faut �tre connect� pour lancer une partie.");
		 else if(userFull.getActualTable()!=null)
			 this.comServer.raiseException(user.getPublicData().getUUID(),"L'utilisateur est d�j� � une table. Il faut sortir de table avant d'en cr�er une nouvelle.");
		 else{
			 GameTable newTable = createTable(userFull.getLightWeightVersion(), name, params);
			this.tableList.add(newTable);
			userFull.setActualTable(newTable.getEmptyVersion());
			userFull.setSpectating(false);
			this.comServer.addNewTable(user.getPublicData().getUUID(), getUUIDList(this.usersList), newTable); //to review on pourrait mettre en lightwieght plus tard ...
		}
	}

	@Override
	public void disconnectUser(User user) {
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
	// boolean true signifie relancer le d�
	public void hasThrown(UUID uuid, boolean d1, boolean d2, boolean d3) {
		User emptyUser = new User(new Profile(uuid));
		User userFull = emptyUser.getSame(this.usersList);
		 if(userFull==null)
			 this.comServer.raiseException(uuid,"L'utilisateur n'est pas connect�. Il faut �tre connect� pour lancer les d�s.");
		 else{
			 GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
			 if(tableFull==null)
				 this.comServer.raiseException(uuid,"La table n'existe pas. Il faut que la table existe pour y lancer les d�s.");
			 else if(!tableFull.getGameState().getActualPlayer().isSame(userFull))
				 this.comServer.raiseException(uuid,"Le lanceur de d�s n'est pas le joueur actuel. Il faut �tre le joueur actuel pour lancer les d�s.");
			 else
			 {
				boolean tie = (tableFull.getGameState().getTurnState() == TurnState.LOSER_TIE_ROUND
						|| tableFull.getGameState().getTurnState() == TurnState.WINNER_TIE_ROUND);
				if(tie && tableFull.getGameState().getDataTieList().size() ==0)
				{
					//le mec clique trop ou au mauvais moment, il ne laisse pas le temps au donn�es de s'init correctement.
					 this.comServer.raiseException(uuid,"Vous avez cliqu� trop vite. Veuillez patientez quelque "+/*"milli"+*/ "secondes avant de recliquer.");
				}
				else
				{
				 	PlayerData pData = new PlayerData(tableFull.getGameState().getData(userFull, tie));
					boolean isFirstRoll = (pData.getRerollCount() == 0);
					boolean isStop = !(d1 || d2 || d3);
					boolean isFullRoll = (d1 && d2 && d3);
					boolean canReroll,hasToReroll;
					if(tie)
					{
						canReroll = tableFull.getGameState().getRules().canReroll(tableFull.getGameState().getDataTieList(),
								userFull, tableFull.getGameState().getFirstPlayer(),false);
						hasToReroll = tableFull.getGameState().getRules().hasToReroll(tableFull.getGameState().getDataTieList(),
								userFull, tableFull.getGameState().getFirstPlayer(),false);
					}
					else
					{
						canReroll = tableFull.getGameState().getRules().canReroll(tableFull.getGameState().getDataList(),
							userFull, tableFull.getGameState().getFirstPlayer(),tableFull.getGameState().getState()==State.DISCHARGING);
						hasToReroll = tableFull.getGameState().getRules().hasToReroll(tableFull.getGameState().getDataList(),
							userFull, tableFull.getGameState().getFirstPlayer(),tableFull.getGameState().getState()==State.DISCHARGING);
					}
					if(!canReroll)
					{
						//WTF erreur ?????
						//c'ets le joueur actuel et il peux pas lancer ...
						//on update le game alors
						this.gameEngine(tableFull, false);
					} else
					 if(hasToReroll && isStop)
					 {
						 this.comServer.raiseException(uuid,"Le joueur ne peux pas s'arreter. Il est oblig� de rejouer.");
					 }
					 else if(isFirstRoll && !isFullRoll)
					 {
						 this.comServer.raiseException(uuid,"Le joueur doit lancer tous les d�s lors de son premier lancer.");
					 }
					 else
					 {
					 		if (!isStop) { //le fait de tester le firstRoll ici n'etait pas necessaire et pouvait engranger des erreurs
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
//								//debug egalite
//								if(tableFull.getGameState().getState()==State.SELECTION)
//								{
//								r1=1;
//								r2=2;
//								r3=3;
//								}
								int[] tDice = { r1, r2, r3 };
								pData.setDices(tDice);
								pData.setRerollCount(pData.getRerollCount() + 1);
								tableFull.getGameState().replaceData(pData);
								this.comServer.sendResult(getUUIDList(tableFull.getAllList()),pData.getPlayer().getPublicData().getUUID(), r1, r2, r3);
								gameEngine(tableFull, false);
					 		} else {
								this.comServer.sendResult(getUUIDList(tableFull.getAllList()),pData.getPlayer().getPublicData().getUUID(), pData.getDices()[0], pData.getDices()[1],
										pData.getDices()[2]);
								gameEngine(tableFull, true);
							}
					 	}
					}
				}
			 }
		 }

	@Override
	public void hasSelected(UUID uuid, boolean d1, boolean d2, boolean d3) {
		User emptyUser = new User(new Profile(uuid));
		User userFull = emptyUser.getSame(this.usersList);
		 if(userFull==null)
			 this.comServer.raiseException(uuid,"L'utilisateur n'est pas connect�. Il faut �tre connect� pour selectionner des d�s.");
		 else{
			 GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
			 if(tableFull==null)
				 this.comServer.raiseException(uuid,"La table n'existe pas. Il faut que la table existe pour y selectionner des d�s.");
			 else
			 if(!tableFull.getGameState().getActualPlayer().isSame(userFull))
				 this.comServer.raiseException(uuid,"Le lanceur de d�s n'est pas le joueur actuel. Il faut �tre le joueur actuel pour selectionner des d�s.");
			 else
				 this.comServer.sendSelection(getUUIDList(tableFull.getAllList()), userFull.getPublicData().getUUID(), d1, d2,
					d3);
		 	}
		 }

	@Override
	public void connectUser(Profile profile) {
		User newUser = new User(profile);

		//TODO : Impossible de cr�er un full user sans �tre connect� mais impossible de se connecter sans un full user
		//Proposition : utiliser des lightweigth � la place est ce que ca existe ?
		//En attendant que ce probl�me soit resolue on accepte la connexion pour pouvoir continuer � tester les autres fonctionnalit�s
		 //if(!newUser.isFullVersion())
//		 if(false)
//			 this.comServer.raiseException(profile.getUUID(),"Profil non complet lors de la connexion. Profil complet requis.");
		 //else if(false)
		 if(newUser.getSame(this.usersList)!=null)
			 this.comServer.raiseException(profile.getUUID(),"Profil d�j� connect�. Veuillez r�essayer dans X minutes");
		 else{
			this.comServer.newUser(getUUIDList(this.usersList), newUser.getPublicData());
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
			this.comServer.raiseException(uuid,"L'utilisateur n'est � aucune table. il doit �tre � une table pour voter.");
		else{
			GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
			if(tableFull==null)
				this.comServer.raiseException(uuid,"L'utilisateur est � une table qui n'existe pas. La table doit exister pour voter.");
			else if(tableFull.getVote() || tableFull.getGameState().getState()!=State.END)
				this.comServer.raiseException(uuid,"Aucun vote pour rejouer en cours. Un vote doit �tre en cours pour voter");
			else if(tableFull.getVoteCasted().stream().filter(v->v.getUser().isSame(userFull)).count()!=0)
				this.comServer.raiseException(uuid,"L'utilisateur � d�j� vot�.");
			else{
				tableFull.castVote(new Vote(userFull.getLightWeightVersion(),answer,tableFull.getLightWeightVersion()));
				if(answer)
					this.comServer.hasAccepted(userFull.getPublicData().getUUID(), getUUIDList(tableFull.getAllList()));
				else
					this.comServer.hasRefused(userFull.getPublicData().getUUID(), getUUIDList(tableFull.getAllList()));
				this.testVoteReplay(tableFull);
			}
		}
	}

	private void testVoteReplay(GameTable tableFull){
		if(tableFull.getVoteCasted().size()==tableFull.getPlayerList().size())
		{
			if(tableFull.voteResult()==true)
			{
				tableFull.initializeGame();
				tableFull.setVote(false);
				for(EndGameTimer egt : this.timerList)
				{
					egt.deactivate(tableFull);
				}
				this.getComServer().replay(getUUIDList(tableFull.getAllList()));
				this.gameEngine(tableFull, false);
			}
			else
			{
				this.dropTable(tableFull);
			}
		}

	}

	@Override
	public void askRefreshUsersList(User user) {
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

		tableFull.getGameState().debugDisplay();


		// detecte la phase
		switch (tableFull.getGameState().getState()) {
		case PRESTART:
			// Premier appel debut du jeu
			tableFull.getGameState().setState(State.SELECTION);
			this.comServer.changeState(getUUIDList(tableFull.getAllList()), State.SELECTION);
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
				this.comServer.changeState(getUUIDList(tableFull.getAllList()), State.CHARGING);
				tableFull.getGameState().nextTurn(tableFull.getGameState().getWinners().get(0));
				gameEngine(tableFull, false);
				break;
			default:
				this.comServer.raiseException(getUUIDList(tableFull.getAllList()),"Etat incoh�rent. Le syst�me ne devrait �tre dans cet �tat");
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
//				PlayerData pDataW = tableFull.getGameState().getData(tableFull.getGameState().getWinners().get(0),
//						false);
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

					// si qqun perd sec
					if (tableFull.getGameState().getDataList().stream().filter(d -> d.getChip() != 0).count() == 1)
					{
						tableFull.getGameState().setState(State.END);

						this.comServer.changeState(getUUIDList(tableFull.getAllList()), State.END);
					}
					else
					{
						tableFull.getGameState().setDataList(tableFull.getGameState().getDataList().stream().filter(d -> d.getChip() != 0).collect(Collectors.toList()));
						tableFull.getGameState().setState(State.DISCHARGING);
						this.comServer.changeState(getUUIDList(tableFull.getAllList()), State.DISCHARGING);
					}

				}
				tableFull.getGameState().nextTurn(pDataL.getPlayer());
				gameEngine(tableFull, false);

				break;
			default:
				this.comServer.raiseException(getUUIDList(tableFull.getAllList()),"Etat incoh�rent. Le syst�me ne devrait �tre dans cet �tat");
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
				tableFull.getGameState().replaceData(pDataW);
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
					tableFull.getGameState().setDataList(tableFull.getGameState().getDataList().stream().filter(d -> d.getChip() != 0).collect(Collectors.toList()));

				}
				tableFull.getGameState().nextTurn(pDataL.getPlayer());
				gameEngine(tableFull, false);
				break;
			default:
				this.comServer.raiseException(getUUIDList(tableFull.getAllList()),"Etat incoh�rent. Le syst�me ne devrait �tre dans cet �tat");
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

			 EndGameTimer newTimer = new EndGameTimer(tableFull,this);
			 this.timerList.add(newTimer);
			this.time.schedule(newTimer,1000*2*60);
			break;

		default:
			this.comServer.raiseException(getUUIDList(tableFull.getAllList()),"Etat incoh�rent. Le syst�me ne devrait �tre dans cet �tat");
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
		while (number < 0 || number > 6) // m�thode du rejet
			number = (int) (Math.random() * 6 + 1);
		return number;
	}

	// Game engine factorisation :
	// pour aller plus vite, �tant donn� ces fonctions priv�es, on envoie une
	// tableFull necessairement

	private void initTurnRoutine(GameTable tableFull, boolean isSec, boolean isStop) {
		if (isSec) {
			tableFull.getGameState().setTurnState(TurnState.FIRST_ROUND);
			this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.FIRST_ROUND);
			this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
					tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);
			System.out.println("StartTurn Init Sec : " + tableFull.getGameState().getActualPlayer().getPublicData().getUUID());
		} else {
			boolean canReroll = tableFull.getGameState().getRules().canReroll(tableFull.getGameState().getDataList(),
					tableFull.getGameState().getActualPlayer(), tableFull.getGameState().getFirstPlayer(),tableFull.getGameState().getState()==State.DISCHARGING);
			boolean hasToReroll = tableFull.getGameState().getRules().hasToReroll(
					tableFull.getGameState().getDataList(), tableFull.getGameState().getActualPlayer(),
					tableFull.getGameState().getFirstPlayer(),
					tableFull.getGameState().getState()==State.DISCHARGING);

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
				this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.FIRST_ROUND);
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
			if (tableFull.getGameState().getDataList().stream().filter(d -> d.getRerollCount() == 0).count() != 0) // alors
																													// on
																													// n'a
																													// pas
																													// fini
																													// de
																													// distribuer
			{
				this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
						tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);

				System.out.println("StartTurn FirstRound Sec : " + tableFull.getGameState().getActualPlayer().getPublicData().getUUID());
			} else // On a fini de dsitribuer
			{
				tableFull.getGameState().setTurnState(TurnState.WINNER_TIE_ROUND);
				this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.WINNER_TIE_ROUND);
				gameEngine(tableFull, false); // pour passer � la phase suivante
												// sans trop de souci.
			}
		} else {
			boolean canReroll = tableFull.getGameState().getRules().canReroll(tableFull.getGameState().getDataList(),
					tableFull.getGameState().getActualPlayer(), tableFull.getGameState().getFirstPlayer(),tableFull.getGameState().getState()==State.DISCHARGING);
			boolean hasToReroll = tableFull.getGameState().getRules().hasToReroll(
					tableFull.getGameState().getDataList(), tableFull.getGameState().getActualPlayer(),
					tableFull.getGameState().getFirstPlayer()
					,tableFull.getGameState().getState()==State.DISCHARGING);

			if (!(tableFull.getGameState().getData(tableFull.getGameState().getActualPlayer(), false)
					.getRerollCount() == 0) // si il a d�j� commenc�
					&& ((isStop && !hasToReroll) || !canReroll)) // et qu'il
																	// veut ou
																	// doit
																	// arreter
			{
				if (tableFull.getGameState().getDataList().stream().filter(d -> (d.getRerollCount() == 0 && (tableFull.getGameState().getState()!=State.DISCHARGING || d.getChip()!=0))).count() != 0)
				{
					// on change le joueur
					tableFull.getGameState().setActualPlayer(tableFull.getGameState().getNextPlayer());
					this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
							tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);
				} else {
					// sinon on change d'�tat
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
			if (tableFull.getGameState().getWinners().size() == 0) {
				// on calcule les winners
				tableFull.getGameState().setWinners(
						tableFull.getGameState().getRules().getWinner(tableFull.getGameState().getDataList()));
				if (tableFull.getGameState().getWinners().size() != 1) {
					// on preinit le tie
					List<PlayerData> newList = new ArrayList<PlayerData>();
					for (User u : tableFull.getGameState().getWinners())
						newList.add(new PlayerData(u));
					tableFull.getGameState().setDataTieList(newList);
					tableFull.getGameState().setActualPlayer(tableFull.getGameState().getWinners().get(0));
					tableFull.getGameState().setFirstPlayer(tableFull.getGameState().getWinners().get(0));
					 this.comServer.exAequoCase(getUUIDList(tableFull.getAllList()),
					 tableFull.getGameState().getWinners(),true);
				}
			}

			if (tableFull.getGameState().getWinners().size() == 1) // un seul winner la solution facile
			{
				tableFull.getGameState().setTurnState(TurnState.LOSER_TIE_ROUND);
				this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.LOSER_TIE_ROUND);
				gameEngine(tableFull, false); // pour passer � la phase suivante
												// sans trop de souci.
				return;
			}
			else if (tableFull.getGameState().getDataTieList().stream().filter(d -> d.getRerollCount() == 0).count() != 0) // alors onn'a pas fini de distribuer
			{
				tableFull.getGameState().setActualPlayer(tableFull.getGameState().getNextPlayer());
				this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
						tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);
			} else {
				// on recalcule les winners
				tableFull.getGameState().setWinners(
						tableFull.getGameState().getRules().getWinner(tableFull.getGameState().getDataTieList()));
				List<PlayerData> newList = new ArrayList<PlayerData>();
				for (User u : tableFull.getGameState().getWinners())
					newList.add(new PlayerData(u));
				tableFull.getGameState().setDataTieList(newList);
				tableFull.getGameState().setActualPlayer(tableFull.getGameState().getWinners().get(0));
				tableFull.getGameState().setFirstPlayer(tableFull.getGameState().getWinners().get(0));
				if (tableFull.getGameState().getWinners().size() != 1) {
					 this.comServer.exAequoCase(getUUIDList(tableFull.getAllList()),
					 tableFull.getGameState().getWinners(),true);
				}
				gameEngine(tableFull, false);
				return;
			}
		} else {
			tableFull.getGameState().setTurnState(TurnState.LOSER_TIE_ROUND);
			this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.LOSER_TIE_ROUND);
			gameEngine(tableFull, false); // pour passer � la phase suivante
											// sans trop de souci.
		}

	}

	private void loserRoundRoutine(GameTable tableFull, boolean calculateLosers) {
		if (calculateLosers) {
			if (tableFull.getGameState().getLosers().size() == 0) {
				// on calcule les losers
				tableFull.getGameState().setLosers(
						tableFull.getGameState().getRules().getLoser(tableFull.getGameState().getDataList()));

				if (tableFull.getGameState().getLosers().size() > 1) {
					// on preinit le tie
					List<PlayerData> newList = new ArrayList<PlayerData>();
					for (User u : tableFull.getGameState().getLosers())
						newList.add(new PlayerData(u));
					tableFull.getGameState().setDataTieList(newList);
					tableFull.getGameState().setActualPlayer(tableFull.getGameState().getLosers().get(0));
					tableFull.getGameState().setFirstPlayer(tableFull.getGameState().getLosers().get(0));
					this.comServer.exAequoCase(getUUIDList(tableFull.getAllList()),
					tableFull.getGameState().getLosers(),false);
				}
			}
			if (tableFull.getGameState().getLosers().size() == 1) // un seul
																	// winner la
																	// solution
																	// facile
			{
				tableFull.getGameState().setTurnState(TurnState.END);
				this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.END);
				gameEngine(tableFull, false); // pour passer � la phase suivante
												// sans trop de souci.
				return;
			}

			if (tableFull.getGameState().getDataTieList().stream().filter(d -> d.getRerollCount() == 0).count() != 0) // alors
																														// on
																														// n'a
																														// pas
																														// fini
																														// de
																														// distribuer
			{
				tableFull.getGameState().setActualPlayer(tableFull.getGameState().getNextPlayer());
				this.comServer.startTurn(getUUIDList(tableFull.getAllList()),
						tableFull.getGameState().getActualPlayer().getPublicData().getUUID(), false);
			} else {
				// on recalcule les losers
				tableFull.getGameState().setLosers(
						tableFull.getGameState().getRules().getLoser(tableFull.getGameState().getDataTieList()));
				List<PlayerData> newList = new ArrayList<PlayerData>();
				for (User u : tableFull.getGameState().getLosers())
					newList.add(new PlayerData(u));
				tableFull.getGameState().setDataTieList(newList);
				tableFull.getGameState().setActualPlayer(tableFull.getGameState().getLosers().get(0));
				tableFull.getGameState().setFirstPlayer(tableFull.getGameState().getLosers().get(0));
				if (tableFull.getGameState().getLosers().size() != 1) {
					 this.comServer.exAequoCase(getUUIDList(tableFull.getAllList()),
					 tableFull.getGameState().getLosers(),true);
				}
				gameEngine(tableFull, false);
				return;
			}
		} else {
			tableFull.getGameState().setTurnState(TurnState.END);
			this.comServer.changeTurnState(getUUIDList(tableFull.getAllList()), TurnState.END);
			gameEngine(tableFull, false); // pour passer � la phase suivante
											// sans trop de souci.
		}
	}
	//normalement pas appele
	@Override
	public void askQuitTable(UUID tableID,UUID user) {
		System.out.println("Obselete AskQuitTable called. This should not have happened.");
//		User userFull = new User(new Profile(user)).getSame(this.usersList);
//		if(userFull==null)
//			this.comServer.raiseException(user,"L'utilisateur n'exsite pas, Il doit exister pour demander de quitter une table.");
//		else
//		if(userFull.getActualTable()==null)
//			this.comServer.raiseException(user,"L'utilisateur n'est � aucune table. il doit �tre � une table pour demander de la quitter.");
//		else{
//		GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
//		if(tableFull==null)
//			this.comServer.raiseException(user,"L'utilisateur est � une table qui n'existe pas. La table doit exister pour �tre quitt�e.");
//		else
//		if(tableFull.getVote())
//			this.comServer.raiseException(user,"Vote d�j� en cours");
//		else if(tableFull.getCreator().getPublicData().getUUID() != user)
//			this.comServer.raiseException(user,"Vous n'etes pas le cr�ateur de la table.");
//		else if(tableFull.getGameState().getState() == State.PRESTART)
//			this.comServer.raiseException(user,"Partie non commenc�e.");
//		else{
//		tableFull.startVote();
//		this.comServer.askStopGameEveryUser(getUUIDList(tableFull.getPlayerList()));
//		//toReviesw ajouter un timer pour evteul cas on des gens ne votent pas.
//	}}
}

	//remplace ancienne m�thode
	//tableFull d�j� donn�e car methode priv�e
	private void askQuitTable(GameTable tableFull) {
		if ((tableFull!=null) &&(!tableFull.getVote()) && (tableFull.getGameState().getState() != State.PRESTART))
			{
			tableFull.startVote();
			this.comServer.askStopGameEveryUser(getUUIDList(tableFull.getPlayerList()));
			//toReviesw ajouter un timer pour evteul cas on des gens ne votent pas.
			}
		}


//arguement table inutile
	@Override
	public void answerStopGame(UUID tableID, boolean answer, UUID user) {
		User userFull = new User(new Profile(user)).getSame(this.usersList);
		if(userFull==null)
			this.comServer.raiseException(user,"L'utilisateur n'exsite pas, Il doit exister pour voter.");
		else if(userFull.getActualTable()==null)
			this.comServer.raiseException(user,"L'utilisateur n'est � aucune table. il doit �tre � une table pour voter.");
		else {
			GameTable tableFull = userFull.getActualTable().getSame(this.tableList);
			if(tableFull==null)
				this.comServer.raiseException(user,"L'utilisateur est � une table qui n'existe pas. La table doit exister pour voter.");
			else if(tableFull.getVoteCasted() == null)
				this.comServer.raiseException(user,"Aucun vote en cours. Un vote doit �tre en cours pour voter");
			else {
				tableFull.castVote(new Vote(userFull.getLightWeightVersion(),answer,tableFull.getLightWeightVersion()));
				if(answer)
					this.comServer.hasAccepted(userFull.getPublicData().getUUID(), getUUIDList(tableFull.getAllList()));
				else
					this.comServer.hasRefused(userFull.getPublicData().getUUID(), getUUIDList(tableFull.getAllList()));
				testVoteStop(tableFull);
			}
		}
	}

	private void testVoteStop(GameTable tableFull)
	{
		if(tableFull.getVoteCasted().size()==tableFull.getPlayerList().size())
		{
			this.comServer.stopGame(getUUIDList(tableFull.getAllList()),tableFull.voteResult());
			if(tableFull.voteResult()==false)
			{
				 for(User u : tableFull.getAllList())
				 {
					 User userFull2 = u.getSame(this.usersList);
					 if(userFull2==null)
						 ;//this.comServer.raiseException(u,"L'utilisateur n'est pas connect�. Il faut �tre connect� pour se faire kick d'une table.");
					 else
						 userFull2.setActualTable(null);
				 }
				 this.tableList.remove(tableFull);
				 this.comServer.refreshTableList(getUUIDList(this.usersList),this.tableList);
			}
			else
			{
				tableFull.setCreator(tableFull.getPlayerList().get(0));
				this.comServer.setCreator(getUUIDList(tableFull.getAllList()),tableFull.getCreator().getPublicData().getUUID());
			}
		}
	}

}
