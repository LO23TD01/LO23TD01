import java.util.List;

public class Chat {
	private List<User> voicedUserList ;
	private List<User> listeningUserList;
	private List<ChatMessage> messageList;
	
	//TODO
	//adduser
	//removeUser
	//chat a message
	
	
	public Chat(List<User> voicedUserList, List<User> listeningUserList) {
		super();
		this.messageList = new ArrayList<ChatMessage>;
		this.voicedUserList = voicedUserList;
		this.listeningUserList = listeningUserList;
	}
	
	
	
	public Chat(List<User> voicedUserList, List<User> listeningUserList, List<ChatMessage> messageList,
			GameTable parent) {
		super();
		this.voicedUserList = voicedUserList;
		this.listeningUserList = listeningUserList;
		this.messageList = messageList;
	}



	public List<User> getVoicedUserList() {
		return voicedUserList;
	}
	public void setVoicedUserList(List<User> voicedUserList) {
		this.voicedUserList = voicedUserList;
	}
	public List<User> getListeningUserList() {
		return listeningUserList;
	}
	public void setListeningUserList(List<User> listeningUserList) {
		this.listeningUserList = listeningUserList;
	}
	public List<ChatMessage> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<ChatMessage> messageList) {
		this.messageList = messageList;
	}
}
