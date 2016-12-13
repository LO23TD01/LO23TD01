package data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameTable {
	private final ObjectProperty<UUID> Uid;
	private final StringProperty name;
	private final ObjectProperty<User> creator;
	private final ObjectProperty<Parameters> parameters;
	private final ObservableList<User> playerList = FXCollections.observableArrayList();
	private final ObservableList<User> spectatorList = FXCollections.observableArrayList();
	private final ObjectProperty<GameState> gameState;
	private final ObjectProperty<Chat> localChat;
	private final ObjectProperty<Record> record;
	private final BooleanProperty vote;
	private final ObservableList<Vote> voteCasted = FXCollections.observableArrayList();

	public GameTable(String name, User creator, Parameters parameters, List<User> playerList, List<User> spectatorList) {
		this.Uid = new SimpleObjectProperty<UUID>(UUID.randomUUID());
		this.name = new SimpleStringProperty(name);
		this.creator = new SimpleObjectProperty<User>(creator);
		this.parameters = new SimpleObjectProperty<Parameters>(parameters);
		this.playerList.addAll(playerList);
		this.spectatorList.addAll(spectatorList);
		this.gameState = new SimpleObjectProperty<GameState>(new GameState(parameters, playerList));

		List<User> mergedList = playerList;
		mergedList.addAll(spectatorList);
		if (parameters.isAuthorizeSpecToChat())
			this.localChat = new SimpleObjectProperty<Chat>(new Chat(mergedList, mergedList));
		else
			this.localChat = new SimpleObjectProperty<Chat>(new Chat(playerList, mergedList));
		this.record = new SimpleObjectProperty<Record>();
		this.vote = new SimpleBooleanProperty();
	}

	public GameTable(UUID uid) {
		this.Uid = new SimpleObjectProperty<UUID>(uid);
		this.name = new SimpleStringProperty();
		this.creator = new SimpleObjectProperty<User>();
		this.parameters = new SimpleObjectProperty<Parameters>();
		this.gameState = new SimpleObjectProperty<GameState>();
		this.localChat = new SimpleObjectProperty<Chat>();
		this.record = new SimpleObjectProperty<Record>();
		this.vote = new SimpleBooleanProperty();
	}

	public GameTable(UUID uid, String name, User creator, Parameters parameters, List<User> playerList, List<User> spectatorList, GameState gameState) {
		this.Uid = new SimpleObjectProperty<UUID>(UUID.randomUUID());
		this.name = new SimpleStringProperty(name);
		this.creator = new SimpleObjectProperty<User>(creator);
		this.parameters = new SimpleObjectProperty<Parameters>(parameters);
		this.playerList.addAll(playerList);
		this.spectatorList.addAll(spectatorList);
		this.gameState = new SimpleObjectProperty<GameState>(gameState);
		this.localChat = new SimpleObjectProperty<Chat>();
		this.record = new SimpleObjectProperty<Record>();
		this.vote = new SimpleBooleanProperty();
	}

	public GameTable(UUID uid, String name, User creator, Parameters parameters, List<User> playerList, List<User> spectatorList, GameState gameState,
			Chat localChat, Record record, List<Vote> voteCasted) {
		this.Uid = new SimpleObjectProperty<UUID>(uid);
		this.name = new SimpleStringProperty(name);
		this.creator = new SimpleObjectProperty<User>(creator);
		this.parameters = new SimpleObjectProperty<Parameters>(parameters);
		this.playerList.addAll(playerList);
		this.spectatorList.addAll(spectatorList);
		this.gameState = new SimpleObjectProperty<GameState>(gameState);
		this.localChat = new SimpleObjectProperty<Chat>(localChat);
		this.record = new SimpleObjectProperty<Record>(record);
		this.voteCasted.addAll(voteCasted);
		this.vote = new SimpleBooleanProperty();
	}

	public void initializeGame() {
		// TOREVIEW ajouter des conditions pour eviter de reinit pendant une partie commencï¿½e ?
		this.setGameState(new GameState(this.parameters.get(), this.playerList));
	}

	public boolean connect(User u, boolean isSpec) {
		if (isSpec && !this.parameters.get().isAuthorizeSpecToChat())
			return false;
		if (isSpec) {
			this.spectatorList.add(u);
			this.localChat.get().add(u, this.parameters.get().isAuthorizeSpecToChat());
			return true;
		}
		if (!isSpec && this.parameters.get().getNbPlayerMax() <= this.playerList.size()
				&& !(this.gameState.get().getState() == State.PRESTART || this.gameState.get().getState() == State.END))
			return false;
		if (!isSpec) {
			this.playerList.add(u);
			this.localChat.get().add(u, true);
			this.gameState.get().add(u);
			return true;
		}
		return false;
	}

	public void disconnect(User u) {

		if (this.spectatorList.remove(u)) // TOREWROK
		{
			this.localChat.get().remove(u);
		} else {
			this.playerList.remove(u);
			this.localChat.get().remove(u);
			this.gameState.get().remove(u);
			// if(this.creator.isSame(u))
			// throw new Exception("User disconnecting from table is the creator.");
		}
	}

	public void startVote() {
		if(!this.vote.get())
		{
			this.vote.set(true);
			this.voteCasted.clear();

		}
//		else
//			throw new Exception("Vote d�j� en cours");
	}

	public void castVote(Vote vote) {
//		if(!this.vote.get())
//			throw new Exception("Pas de vote en cours.");
//		if(this.voteCasted.stream().filter(v->v.getUser().isSame(vote.getUser())).count()==0)
//			throw new Exception("D�j� vot�");
		this.voteCasted.add(vote);
	}

	public boolean voteResult() {
		int total = 0;
		for (Vote v : this.voteCasted) {
			if (v.isValue())
				total++;
		}
		return (this.playerList.size() / 2) < total;
	}

	public GameTable getEmptyVersion() {
		if (isEmptyVersion())
			return this;
		return new GameTable(this.getUid());
	}

	public boolean isEmptyVersion() {
		if (this.name == null) // TOReview add more condition
			return true;
		return false;
	}

	public GameTable getLightWeightVersion() {
		if (isLightWeightVersion())
			return this;
//		if (this.isEmptyVersion())
//			throw new Exception("GameTable is Empty, can't get LightWeight");

		return new GameTable(this.getUid(), this.name.get(), this.creator.get(), this.parameters.get(), this.playerList, this.spectatorList,
				this.gameState.get());
	}

	public boolean isLightWeightVersion() {

		if (!this.isEmptyVersion() && this.record == null) // TOREVIEW : p-etre faire une condition complete ?
			return true;
		return false;
	}

	public boolean isFullVersion() {
		if (!this.isEmptyVersion() && !this.isLightWeightVersion()) // TOREVIEW : p-etre faire une condition complete ?
			return true;
		return false;
	}

	public boolean isSame(GameTable t) {
		if (t.getUid() == this.getUid())
			return true;
		return false;
	}

	public GameTable getSame(List<GameTable> l) {
		for (GameTable t : l) {
			if (isSame(t))
				return t;
		}
		return null;
	}

	public List<User> getAllList() {
		List<User> newList = Stream.concat(this.playerList.stream(), this.spectatorList.stream()).collect(Collectors.toList());
		// List<User> newList = new ArrayList<User>();
		// newList.addAll(this.playerList);
		// newList.addAll(this.spectatorList);
		return newList;
	}

	public ObservableList<User> getPlayerList() {
		return playerList;
	}

	public ObservableList<User> getSpectatorList() {
		return spectatorList;
	}

	public ObservableList<Vote> getVoteCasted() {
		return voteCasted;
	}

	public final ObjectProperty<UUID> UidProperty() {
		return this.Uid;
	}

	public final UUID getUid() {
		return this.UidProperty().get();
	}

	public final void setUid(final UUID Uid) {
		this.UidProperty().set(Uid);
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final ObjectProperty<User> creatorProperty() {
		return this.creator;
	}

	public final User getCreator() {
		return this.creatorProperty().get();
	}

	public final void setCreator(final User creator) {
		this.creatorProperty().set(creator);
	}

	public final ObjectProperty<Parameters> parametersProperty() {
		return this.parameters;
	}

	public final Parameters getParameters() {
		return this.parametersProperty().get();
	}

	public final void setParameters(final Parameters parameters) {
		this.parametersProperty().set(parameters);
	}

	public final ObjectProperty<GameState> gameStateProperty() {
		return this.gameState;
	}

	public final GameState getGameState() {
		return this.gameStateProperty().get();
	}

	public final void setGameState(final GameState gameState) {
		this.gameStateProperty().set(gameState);
	}

	public final ObjectProperty<Chat> localChatProperty() {
		return this.localChat;
	}

	public final Chat getLocalChat() {
		return this.localChatProperty().get();
	}

	public final void setLocalChat(final Chat localChat) {
		this.localChatProperty().set(localChat);
	}

	public final ObjectProperty<Record> recordProperty() {
		return this.record;
	}

	public final Record getRecord() {
		return this.recordProperty().get();
	}

	public final void setRecord(final Record record) {
		this.recordProperty().set(record);
	}

	public final BooleanProperty voteProperty() {
		return this.vote;
	}

	public final boolean getVote() {
		return this.voteProperty().get();
	}

	public final void setVote(final boolean b) {
		this.voteProperty().set(b);
	}

}
