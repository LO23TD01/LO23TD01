package data;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class User {
	private final ObjectProperty<Profile> publicData;
	private final ObjectProperty<GameTable> actualTable;
	private final BooleanProperty isSpectating;

	public User(Profile publicData, GameTable actualTable, boolean isSpectating) {
		this.publicData = new SimpleObjectProperty<Profile>(publicData);
		this.actualTable = new SimpleObjectProperty<GameTable>(actualTable);
		this.isSpectating = new SimpleBooleanProperty(isSpectating);
	}

	public User(Profile publicData) {
		this.publicData = new SimpleObjectProperty<Profile>(publicData);
		this.actualTable = new SimpleObjectProperty<GameTable>();
		this.isSpectating = new SimpleBooleanProperty(false);
	}

	public User getEmptyVersion() {

		if (this.isEmptyVersion())
			return this;
		return new User(new Profile(this.publicData.get().getUUID()));
	}

	public boolean isEmptyVersion() {
		if (this.publicData.get().getNickName() == null) // TOREVIEW : p-etre faire une condition complete ?
			return true;
		return false;
	}

	public User getLightWeightVersion() {

		if (this.isLightWeightVersion())
			return this;
		// if(this.isEmptyVersion())
		// throw new Exception("User is Empty, can't get LightWeight");

		return new User(new Profile(this.publicData.get().getUUID(), this.publicData.get().getNickName(), this.publicData.get().getFirstName(),
				this.publicData.get().getSurName(), this.publicData.get().getAge()), this.actualTable.get(), this.isSpectating.get());
	}

	public boolean isLightWeightVersion() {

		if (!this.isEmptyVersion() && this.publicData.get().getAvatar() == null) // TOREVIEW : p-etre faire une condition complete ?
			return true;
		return false;
	}

	public boolean isFullVersion() {
		if (!this.isEmptyVersion() && !this.isLightWeightVersion()) // TOREVIEW : p-etre faire une condition complete ?
			return true;
		return false;
	}

	public boolean isSame(User u) {
		if (u.getPublicData().getUUID() == this.publicData.get().getUUID())
			return true;
		return false;
	}

	public User getSame(List<User> l) {
		for (User u : l) {
			if (isSame(u))
				return u;
		}
		return null;
	}

	public final ObjectProperty<Profile> publicDataProperty() {
		return this.publicData;
	}

	public final Profile getPublicData() {
		return this.publicDataProperty().get();
	}

	public final void setPublicData(final Profile publicData) {
		this.publicDataProperty().set(publicData);
	}

	public final ObjectProperty<GameTable> actualTableProperty() {
		return this.actualTable;
	}

	public final GameTable getActualTable() {
		return this.actualTableProperty().get();
	}

	public final void setActualTable(final GameTable actualTable) {
		this.actualTableProperty().set(actualTable);
	}

	public final BooleanProperty isSpectatingProperty() {
		return this.isSpectating;
	}

	public final boolean isIsSpectating() {
		return this.isSpectatingProperty().get();
	}

	public final void setSpectating(final boolean isSpectating) {
		this.isSpectatingProperty().set(isSpectating);
	}
}
