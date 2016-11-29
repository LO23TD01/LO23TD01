package data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableIntegerArray;

public class PlayerData {
	private final ObjectProperty<User> player;
	private final IntegerProperty chip;
	private final ObservableIntegerArray dices = FXCollections.observableIntegerArray(0, 0, 0);
	private final IntegerProperty rerollCount;

	/**
	 * @param player
	 */
	public PlayerData(User player) {
		this.player = new SimpleObjectProperty<User>(player);
		this.chip = new SimpleIntegerProperty(0);
		this.rerollCount = new SimpleIntegerProperty(0);
	}

	/**
	 * @param player
	 * @param chip
	 * @param dices
	 * @param rerollCount
	 */
	public PlayerData(User player, int chip, int[] dices, int rerollCount) {
		this.player = new SimpleObjectProperty<User>(player);
		this.chip = new SimpleIntegerProperty(chip);
		this.rerollCount = new SimpleIntegerProperty(rerollCount);
		this.dices.setAll(dices, 0, 3);
	}

	/**
	 * @param pdata
	 * @param chip
	 * @param dices
	 * @param rerollCount
	 */
	public PlayerData(PlayerData pData) {
		this.player = new SimpleObjectProperty<User>(pData.getPlayer());
		this.chip = new SimpleIntegerProperty(pData.getChip());
		this.rerollCount = new SimpleIntegerProperty(pData.getRerollCount());
		this.dices.setAll(pData.dices, 0, 3);
	}

	/**
	 * @param pdata
	 * @param chip
	 * @param dices
	 * @param rerollCount
	 */
	public PlayerData(PlayerData pData, boolean isNewTurn) {
		this.player = new SimpleObjectProperty<User>(pData.getPlayer());
		this.chip = new SimpleIntegerProperty(pData.getChip());
		int[] newDices = new int[3];
		for (int i = 0; i < newDices.length; i++)
			newDices[i] = 0;
		if (isNewTurn)
			this.rerollCount = new SimpleIntegerProperty(0);
		else {
			this.dices.setAll(pData.dices, 0, 3);
			this.rerollCount = new SimpleIntegerProperty(pData.getRerollCount());
		}
	}

	/*
	 * Permet de reset le PlayerData pour un tour, c'est-à-dire que les dés et le compteur de reroll sont remis à zéro
	 */
	public void newTurn() {
		dices.setAll(0, 0, 0);
		this.setRerollCount(0);
	}

	public final int[] getDices() {
		return this.dices.toArray(null);
	}

	public final void setDices(int[] dices) {
		this.dices.setAll(dices, 0, 3);
	}

	public final ObjectProperty<User> playerProperty() {
		return this.player;
	}

	public final User getPlayer() {
		return this.playerProperty().get();
	}

	public final void setPlayer(final User player) {
		this.playerProperty().set(player);
	}

	public final IntegerProperty chipProperty() {
		return this.chip;
	}

	public final int getChip() {
		return this.chipProperty().get();
	}

	public final void setChip(final int chip) {
		this.chipProperty().set(chip);
	}

	public final IntegerProperty rerollCountProperty() {
		return this.rerollCount;
	}

	public final int getRerollCount() {
		return this.rerollCountProperty().get();
	}

	public final void setRerollCount(final int rerollCount) {
		this.rerollCountProperty().set(rerollCount);
	}
}
