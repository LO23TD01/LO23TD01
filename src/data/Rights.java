package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

@XmlRootElement
@XmlType(propOrder = { "profVisibility", "canJoinCreatedGame", "canSpectateCreatedGame" })
public class Rights {
	private final BooleanProperty profVisibility;
	private final BooleanProperty canJoinCreatedGame;
	private final BooleanProperty canSpectateCreatedGame;

	public Rights() {
		this.profVisibility = new SimpleBooleanProperty();
		this.canJoinCreatedGame = new SimpleBooleanProperty();
		this.canSpectateCreatedGame = new SimpleBooleanProperty();
	}

	public Rights(boolean profVisibility, boolean canJoinCreatedGame, boolean canSpectateCreatedGame) {
		this.profVisibility = new SimpleBooleanProperty(profVisibility);
		this.canJoinCreatedGame = new SimpleBooleanProperty(canJoinCreatedGame);
		this.canSpectateCreatedGame = new SimpleBooleanProperty(canSpectateCreatedGame);
	}

	public final BooleanProperty profVisibilityProperty() {
		return this.profVisibility;
	}

	public final boolean isProfVisibility() {
		return this.profVisibilityProperty().get();
	}

	@XmlElement
	public final void setProfVisibility(final boolean profVisibility) {
		this.profVisibilityProperty().set(profVisibility);
	}

	public final BooleanProperty canJoinCreatedGameProperty() {
		return this.canJoinCreatedGame;
	}

	public final boolean isCanJoinCreatedGame() {
		return this.canJoinCreatedGameProperty().get();
	}

	@XmlElement
	public final void setCanJoinCreatedGame(final boolean canJoinCreatedGame) {
		this.canJoinCreatedGameProperty().set(canJoinCreatedGame);
	}

	public final BooleanProperty canSpectateCreatedGameProperty() {
		return this.canSpectateCreatedGame;
	}

	public final boolean isCanSpectateCreatedGame() {
		return this.canSpectateCreatedGameProperty().get();
	}

	@XmlElement
	public final void setCanSpectateCreatedGame(final boolean canSpectateCreatedGame) {
		this.canSpectateCreatedGameProperty().set(canSpectateCreatedGame);
	}

}
