package data;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChatMessage {
	private final ObjectProperty<User> sender;
	private final StringProperty content;
	private final ObjectProperty<Date> date;

	public ChatMessage(User sender, String content, Date date) {
		this.sender = new SimpleObjectProperty<User>(sender);
		this.content = new SimpleStringProperty(content);
		this.date = new SimpleObjectProperty<Date>(date);
	}

	public ChatMessage(User sender, String content) {
		this.sender = new SimpleObjectProperty<User>(sender);
		this.content = new SimpleStringProperty(content);
		this.date = new SimpleObjectProperty<Date>(new Date());
	}

	public final ObjectProperty<User> senderProperty() {
		return this.sender;
	}

	public final User getSender() {
		return this.senderProperty().get();
	}

	public final void setSender(final User sender) {
		this.senderProperty().set(sender);
	}

	public final StringProperty contentProperty() {
		return this.content;
	}

	public final String getContent() {
		return this.contentProperty().get();
	}

	public final void setContent(final String content) {
		this.contentProperty().set(content);
	}

	public final ObjectProperty<Date> dateProperty() {
		return this.date;
	}

	public final Date getDate() {
		return this.dateProperty().get();
	}

	public final void setDate(final Date date) {
		this.dateProperty().set(date);
	}

}
