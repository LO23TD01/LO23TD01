package data;

import network.server.*; 

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import network.server.ComServer;
import network.server.ComServerInterface;

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
	
	public GameTable createTable (User user, String name, Parameters params) throws Exception{
		
		//Initialisation des joueurs de la table avec le joueur créant la table
		List<User> playerList = new ArrayList<User>();
		playerList.add(user);
		
		//Initialisation spectateurs, vide au début
		List<User> spectatorList = new ArrayList<User>();
		
		return new GameTable(name, user, params, playerList, spectatorList);
	}

	private void startLaunchTimer(GameTable table) //Edit : ajout de la GameTable. Il faut savoir quelle table lancer ...
	{
		GameTable tableFull = table.getSame(this.tableList);
		//		if(tableFull==null)
		//			throw new Exception("La table n'existe pas. Il faut que la table existe pour s'y connecter.");
		tableFull.initializeGame();

		this.selectFirstPlayer(tableFull);
	}

	public boolean closeTable (GameTable table){
		//TO-DO : Fermeture de la table
		return false;
	}

	private void selectFirstPlayer(GameTable table) //Edit : ajout de la GameTable. Il faut savoir quelle table lancer ...
	{
		this.comServer.showTimer(getUuidList(table.getAllList()));
	}

	public void connect (User user){
		//TO-DO : Connexion d'un user
	}
	
	public void disconnect (User user){
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
		//		if(user.getSame(this.usersList)==null)
		//			throw new Exception("Utilisateur non connecté. Il faut qu'il soit connecté pour retrouver son profil.");
		return user.getSame(this.usersList).getPublicData();
	}
	@Override
	public void updateUserProfile(UUID uuid, Profile profile) {
		//uuid useless (il est déjà dans le profile bande de bananes.)

		User compUser = new User(profile);
		//		if(!compUser.isFullVersion())
		//			throw new Exception("Profil non complet lors de la mise à jour. Il faut un profil complet");
		//		else if(compUser.getSame(this.usersList)==null)
		//			throw new Exception("Profil non connecté. Il faut que le profil soit connecté pour le mettre à jour.");
		compUser.getSame(this.usersList).setPublicData(profile);
		//La suite du diag de sequence a été overrided par l'avis du prof.
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
		//		if(tableFull==null)
		//			throw new Exception("La table n'existe pas. Il faut que la table existe pour s'y connecter.");
		//		else if(userFull==null)
		//			throw new Exception("L'utilisateur n'est pas connecté. Il faut être connecté pour rejoindre une table.");

		boolean isLaunched = tableFull.getGameState().getState()!=State.PRESTART;
		boolean isFull = tableFull.getParameters().getNbPlayerMax()>=tableFull.getPlayerList().size();
		boolean isSpecAuthorized = tableFull.getParameters().isAuthorizeSpec();

		if(tableFull.connect(user,isPlayer)) 
		{
			//Success
			user.setActualTable(tableFull.getEmptyVersion());
			user.setSpectating(!isPlayer);
			if(isPlayer)
				this.comServer.newPlayerOnTable(getUuidList(tableFull.getAllList()), user.getPublicData(), tableFull.getUid());
			else
				this.comServer.newPlayerOnTable(getUuidList(tableFull.getAllList()), user.getPublicData(), tableFull.getUid());
		}
		else if(isPlayer && isLaunched)
			this.comServer.raiseException(user.getPublicData().getUuid(), "Impossible de rejoindre une partie déjà commencée.");
		else if(isPlayer && isFull)
			this.comServer.raiseException(user.getPublicData().getUuid(), "Impossible de rejoindre une partie pleine.");
		else if(!isPlayer && !isSpecAuthorized)
			this.comServer.raiseException(user.getPublicData().getUuid(), "Impossible de regarder cette partie. Non Autorisé par le Créateur.");
		else
		{
			//la table n'a pas reussi à connecter le nouveau user malgré nos test en ammonts
			//TOREVIEW est ce que c'est une bonne idée d'afficher l'erreur de cette manière au client ? Le fiat est que si on le fait pas, il attendra de manière infinie d'apres nos diag de sequence.
			this.comServer.raiseException(user.getPublicData().getUuid(), "Erreur inconnue lors de la connexion à la table");
			//			throw new Exception("Erreur inconnue lors de la connexion à la table");
		}

//		//seconde implementation possible :
//
//		if(isPlayer)
//		{
//			if(tableFull.connect(user,isPlayer)) 
//			{
//				user.setActualTable(tableFull.getEmptyVersion());
//				user.setSpectating(!isPlayer);
//				this.comServer.newPlayerOnTable(getUuidList(tableFull.getAllList()), user.getPublicData(), tableFull.getUid());
//			}
//			else if(isLaunched)
//				this.comServer.raiseException(user.getPublicData().getUuid(), "Impossible de rejoindre une partie déjà commencée.");
//			else if(isFull)
//				this.comServer.raiseException(user.getPublicData().getUuid(), "Impossible de rejoindre une partie pleine.");
//			else
//			{
//				this.comServer.raiseException(user.getPublicData().getUuid(), "Erreur inconnue lors de la connexion à la table");
//				//				throw new Exception("Erreur inconnue lors de la connexion à la table");
//			}
//		}
//		else
//		{
//			if(tableFull.connect(user,isPlayer)) 
//			{
//				//Success
//				user.setActualTable(tableFull.getEmptyVersion());
//				user.setSpectating(!isPlayer);
//				this.comServer.newPlayerOnTable(getUuidList(tableFull.getAllList()), user.getPublicData(), tableFull.getUid());
//			}
//			else if(!isSpecAuthorized)
//				this.comServer.raiseException(user.getPublicData().getUuid(), "Impossible de regarder cette partie. Non Autorisé par le Créateur.");
//			else
//			{
//				this.comServer.raiseException(user.getPublicData().getUuid(), "Erreur inconnue lors de la connexion à la table");
//				//				throw new Exception("Erreur inconnue lors de la connexion à la table");
//			}
//		}


	}
	@Override
	public void launchGame(User user) {
		User userFull = user.getSame(this.usersList);
		//		if(userFull==null)
		//			throw new Exception("L'utilisateur n'est pas connecté. Il faut être connecté pour lancer une partie.");
		//		else if(userFull.getActualTable()==null)
		//			throw new Exception("L'utilisateur n'a rejoint aucune table. Il faut être assit à une table pour lancer une partie.")
		//		else if(!userFull.getActualTable().getCreator().isSame(user))
		//			throw new Exception("L'utilisateur n'est pas le createur de sa partie. Il faut être le createur pour lancer une partie.");

	}
	@Override
	public void createNewTable(User user, String name, Parameters params) {
		GameTable newTable = createTable(user,name,params);
		this.tableList.add(newTable);
		user.setActualTable(newTable.getEmptyVersion());
		user.setSpectating(false);
		this.comServer.addNewTable(user.getPublicData().getUuid(),getUuidList(this.usersList), newTable);

	}
	@Override
	public void disconnectUser(User user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int[] hasThrown(UUID uuid, boolean d1, boolean d2, boolean d3) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void hasSelected(UUID uuid, boolean d1, boolean d2, boolean d3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void connectUser(Profile profile) {
		User newUser = new User(profile);
		
//		if(!newUser.isFullVersion())
//			throw new Exception("Profil non complet lors de la connexion. Profil complet requis.");
//		else if (newUser.getSame(this.usersList)!=null)
//			throw new Exception("Profil déjà connecté. Veuillez réessayer dans X minutes");
		
		this.comServer.newUser(getUUIDList(this.usersList), newUser.getEmptyVersion().getPublicData());
		this.usersList.add(newUser);
		this.comServer.sendTablesUsers(this.usersList,this.tableList,newUser.getEmptyVersion().getPublicData());
		
		
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
		// TODO Auto-generated method stub
		
	}
	
	
	public ComServer getComServer() {
		return comServer;
	}

	public void setComServer(ComServer comServer) {
		this.comServer = comServer;
	}	
	
	public static List<User> getEmptyList(List<User> userList)
	{
		List<User> newList = userList.stream().map(u->u.getEmptyVersion()).collect(Collectors.toList());
//		List<User> newList = new ArrayList<User>();
//		for(User i : userList)
//			newList.add(i.getEmptyVersion());
		return newList;
	}
	
	public static List<UUID> getUUIDList(List<User> userList)
	{

		List<UUID> newList = userList.stream().map(u->u.getPublicData().getUuid()).collect(Collectors.toList());
//		List<UUID> newList = new ArrayList<UUID>();
//		for(User i : userList)
//			newList.add(i.getPublicData().getUuid());
		return newList;
	}
}
