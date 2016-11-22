package data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerDataEngine implements InterfaceDataNetwork {
	private List<User> usersList;
	private List<GameTable> tableList;
	
	
	/*
	 * 
	 * Constructor
	 * 
	 */
	
	public ServerDataEngine() {
		this.usersList = new ArrayList<>();
		this.tableList = new ArrayList<>();
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
	
	public boolean closeTable (GameTable table){
		//TO-DO : Fermeture de la table
		return false;
	}
	
	public void connect (User user){
		//TO-DO : Connexion d'un user
	}
	
	public void disconnect (User user){
		//TO-DO : Deconnexion d'un user
	}
	
	/*
	 * 
	 * Implemented Methods
	 * 
	 */
	
	@Override
	public Profile getProfile(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateUserProfile(UUID uuid, Profile profile) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void launchGame(User user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void createNewTable(User user, String name, Parameters params) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
	
	
}
