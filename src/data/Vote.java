package data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Vote {
	private final ObjectProperty<User> user;
	private final BooleanProperty value;
	private final ObjectProperty<GameTable> parent;

	public Vote(User user, boolean value, GameTable parent) {
		super();
		this.user = new SimpleObjectProperty<User>(user);
		this.value = new SimpleBooleanProperty(value);
		this.parent = new SimpleObjectProperty<GameTable>(parent);
	}

	public final ObjectProperty<User> userProperty() {
		return this.user;
	}

	public final User getUser() {
		return this.userProperty().get();
	}

	public final void setUser(final User user) {
		this.userProperty().set(user);
	}

	public final BooleanProperty valueProperty() {
		return this.value;
	}

	public final boolean isValue() {
		return this.valueProperty().get();
	}

	public final void setValue(final boolean value) {
		this.valueProperty().set(value);
	}

	public final ObjectProperty<GameTable> parentProperty() {
		return this.parent;
	}

	public final GameTable getParent() {
		return this.parentProperty().get();
	}

	public final void setParent(final GameTable parent) {
		this.parentProperty().set(parent);
	}

}
