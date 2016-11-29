package data;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Chat {
	private final ObservableList<User> voicedUserList = FXCollections.observableArrayList();
	private final ObservableList<User> listeningUserList = FXCollections.observableArrayList();
	private final ObservableList<ChatMessage> messageList = FXCollections.observableArrayList();

	// TODO
	public void add(User user, boolean isVoiced) {
		// TODO;
	}

	public void remove(User user) {
		// TODO;
	}
	// adduser
	// removeUser
	// chat a message

	public Chat() {
	}

	public Chat(List<User> voicedUserList, List<User> listeningUserList) {
		this.voicedUserList.addAll(voicedUserList);
		this.listeningUserList.addAll(listeningUserList);
	}

	public Chat(List<User> voicedUserList, List<User> listeningUserList, List<ChatMessage> messageList) {
		super();
		this.voicedUserList.addAll(voicedUserList);
		this.listeningUserList.addAll(listeningUserList);
		this.messageList.addAll(messageList);
	}

	public ObservableList<User> getVoicedUserList() {
		return voicedUserList;
	}

	public ObservableList<User> getListeningUserList() {
		return listeningUserList;
	}

	public ObservableList<ChatMessage> getMessageList() {
		return messageList;
	}

}
