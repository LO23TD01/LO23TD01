import java.util.Date;

public class ChatMessage {
	private User sender;
	private String content;
	private Date date;

	public ChatMessage(User sender, String content, Date date) {
		super();
		this.sender = sender;
		this.content = content;
		this.date = date;
	}
	
	public ChatMessage(User sender, String content) {
		super();
		this.sender = sender;
		this.content = content;
		this.date = new Date();
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
