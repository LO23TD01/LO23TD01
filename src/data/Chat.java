package data;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Chat {
	private final ObservableList<User> voicedUserList = FXCollections.observableArrayList();
	private final ObservableList<User> listeningUserList = FXCollections.observableArrayList();
	private final ObservableList<ChatMessage> messageList = FXCollections.observableArrayList();

	public void add(User user, boolean isVoiced) {
		this.listeningUserList.add(user);
		if(isVoiced)
			this.voicedUserList.add(user);
	}
	
	public boolean isVoiced(ChatMessage message)
	{
		User sender = message.getSender().getSame(voicedUserList);
		if(sender != null)
			return true;
		return false;
	}
	
	public void add(ChatMessage message) {
		this.messageList.add(message);
	}

	public void remove(User user) {

		User u = user.getSame(this.listeningUserList);
		if(u !=null)
			this.listeningUserList.remove(u);
		u = user.getSame(this.voicedUserList);
		if(u !=null)
			this.voicedUserList.remove(u);
	}


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
