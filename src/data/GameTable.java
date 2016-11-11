package data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameTable {
	private UUID Uid;
	private String name;
	private User creator;
	private Parameters parameters;
	private List<User> playerList;
	private List<User> spectatorList;
	private GameState gameState;
	private Chat localChat;
	private Record record;
	private List<Vote> voteCasted;
	
	public GameTable(String name, User creator, Parameters parameters, List<User> playerList,
			List<User> spectatorList) {
		super();
		this.Uid = UUID.randomUUID();
		this.name = name;
		this.creator = creator;
		this.parameters = parameters;
		this.playerList = playerList;
		this.spectatorList = spectatorList;
		this.gameState = new GameState(this);
		this.localChat = new Chat(this);
		this.record = new Record();
		this.voteCasted = new ArrayList<Vote>();
	}
	
	public GameTable(UUID uid){
		this.Uid = uid;
		this.name = null;
		this.creator = null;
		this.parameters = null;
		this.playerList = null;
		this.spectatorList = null;
		this.gameState = null;
		this.localChat = null;
		this.record = null;
		this.voteCasted = null;
	}
	
	public GameTable(UUID uid, String name, User creator, Parameters parameters, List<User> playerList,
			List<User> spectatorList, GameState gameState) {
		super();
		this.Uid = uid;
		this.name = name;
		this.creator = creator;
		this.parameters = parameters;
		this.playerList = playerList;
		this.spectatorList = spectatorList;
		this.gameState = gameState;
		this.localChat = null;
		this.record = null;
		this.voteCasted = null;
	}
	
	public GameTable(UUID uid, String name, User creator, Parameters parameters, List<User> playerList,
			List<User> spectatorList, GameState gameState, Chat localChat, Record record, List<Vote> voteCasted) {
		super();
		this.Uid = uid;
		this.name = name;
		this.creator = creator;
		this.parameters = parameters;
		this.playerList = playerList;
		this.spectatorList = spectatorList;
		this.gameState = gameState;
		this.localChat = localChat;
		this.record = record;
		this.voteCasted = voteCasted;
	}

	public void initializeGame() throws Exception{	
		//TOREVIEW ajouter des conditions pour eviter de reinit pendant une partie commencée ?
		this.gameState = new GameState(this.parameters, this.playerList);
	}
	
	public boolean connect(User u, boolean isSpec){
		if(isSpec && !this.parameters.isAuthorizeSpecToChat())
			return false;
		if(isSpec)
		{
			this.spectatorList.add(u);
			this.localChat.add(u,this.parameters.isAuthorizeSpecToChat());
			return true;
		}
		if(!isSpec &&  this.parameters.getNbPlayerMax()<=this.playerList.size() && !(this.gameState.getState()==State.PRESTART || this.gameState.getState()==State.END))
			return false;
		if(!isSpec)
		{
			this.playerList.add(u);
			this.localChat.add(u,true);
			this.gameState.add(u);
			return true;
		}
	}
	
	public void disconnect(User u){
		
		if(this.spectatorList.remove(u)) //TOREWROK
		{
			this.localChat.remove(u);
		}
		else{
			this.playerList.remove(u);
			this.localChat.remove(u);
			this.gameState.remove(u);
			if(this.creator.isSame(u))
				throw(new Exception("User disconnecting from table is the creator."));
		}
	}
	
	public void startGame(){
		//TOReview inutile ?
	}
	
	public void stopGame(){
		//TOReview inutile ?
	}
	
	public void startVote(){
		this.voteCasted = new ArrayList<Vote>();
	}
	
	public void castVote(Vote v){
		//if() TO-DO : condiotn player et player pas deja casté
		this.voteCasted.add(v);
	}
	
	public boolean voteResult(){
		int total = 0;
		for(Vote v : this.voteCasted){
			if(v.isValue())
				total++;
		}
		return (this.playerList.size()/2)<total;
	}
	
	public GameTable getEmptyVersion(){
		if(isEmptyVersion())
			return this;
		return new GameTable(this.getUid());
	}
	
	public boolean isEmptyVersion(){
		if(this.name==null) //TOReview add more condition
			return true;
		return false;
	}
	
	public GameTable getLightWeightVersion() throws Exception{
		if(isLightWeightVersion())
			return this;
		if(this.isEmptyVersion())
			throw new Exception("GameTable is Empty, can't get LightWeight");
		
		return new GameTable(this.getUid(),this.name,this.creator,this.parameters,this.playerList,this.spectatorList,this.gameState);
	}
	
	public boolean isLightWeightVersion(){
		
		if(!this.isEmptyVersion() && this.record==null) //TOREVIEW : p-etre faire une condition complete ?
			return true;
		return false;
	}
	
	public boolean isFullVersion()
	{
		if(!this.isEmptyVersion() && !this.isLightWeightVersion()) //TOREVIEW : p-etre faire une condition complete ?
			return true;
		return false;
	}
	
	
	public boolean isSame(GameTable t){
		if(t.getUid()==this.getUid())
			return true;
		return false;
	}
	
	public GameTable getSame(List<GameTable> l){
		for(GameTable t : l)
		{
			if(isSame(t))
				return t;
		}
		return null;
	}

	public UUID getUid() {
		return Uid;
	}

	public void setUid(UUID uid) {
		Uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	public List<User> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<User> playerList) {
		this.playerList = playerList;
	}

	public List<User> getSpectatorList() {
		return spectatorList;
	}

	public void setSpectatorList(List<User> spectatorList) {
		this.spectatorList = spectatorList;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Chat getLocalChat() {
		return localChat;
	}

	public void setLocalChat(Chat localChat) {
		this.localChat = localChat;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public List<Vote> getVoteCasted() {
		return voteCasted;
	}

	public void setVoteCasted(List<Vote> voteCasted) {
		this.voteCasted = voteCasted;
	}

}
