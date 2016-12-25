package data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableIntegerArray;

public class PlayerData {
	private final ObjectProperty<User> player;
	private final IntegerProperty chip;
	private final IntegerProperty d1;
	private final IntegerProperty d2;
	private final IntegerProperty d3;
	private final IntegerProperty rerollCount;

	/**
	 * @param player
	 */
	//constructeur pour nouvelle partie
	public PlayerData(User player) {
		this.player = new SimpleObjectProperty<User>(player);
		this.chip = new SimpleIntegerProperty(0);
		this.d1 = new SimpleIntegerProperty(0);
		this.d2 = new SimpleIntegerProperty(0);
		this.d3 = new SimpleIntegerProperty(0);
		this.rerollCount = new SimpleIntegerProperty(0);
	}

	/**
	 * @param player
	 * @param chip
	 * @param dices
	 * @param rerollCount
	 */
	//constructeur en copie
	public PlayerData(User player, int chip, int[] dices, int rerollCount) {
		this.player = new SimpleObjectProperty<User>(player);
		this.chip = new SimpleIntegerProperty(chip);
		this.rerollCount = new SimpleIntegerProperty(rerollCount);
		this.d1 = new SimpleIntegerProperty(dices[0]);
		this.d2 = new SimpleIntegerProperty(dices[1]);
		this.d3 = new SimpleIntegerProperty(dices[2]);
	}

	/**
	 * @param pdata
	 * @param chip
	 * @param dices
	 * @param rerollCount
	 */
	//aucune id�e ???
	public PlayerData(PlayerData pData) {
		this.player = new SimpleObjectProperty<User>(pData.getPlayer());
		this.chip = new SimpleIntegerProperty(pData.getChip());
		this.rerollCount = new SimpleIntegerProperty(pData.getRerollCount());
		this.d1 = pData.d1;
		this.d2 = pData.d2;
		this.d3 = pData.d3;
	}

	/**
	 * @param pdata
	 * @param chip
	 * @param dices
	 * @param rerollCount
	 */
	// constructeur pour les nouveaux tours
	public PlayerData(PlayerData pData, boolean isNewTurn) {
		this.player = new SimpleObjectProperty<User>(pData.getPlayer());
		this.chip = new SimpleIntegerProperty(pData.getChip());
		this.d1 = new SimpleIntegerProperty(0);
		this.d2 = new SimpleIntegerProperty(0);
		this.d3 = new SimpleIntegerProperty(0);
		if (isNewTurn)
			this.rerollCount = new SimpleIntegerProperty(0);
		else {
			this.rerollCount = new SimpleIntegerProperty(pData.getRerollCount());
		}
	}

	/*
	 * Permet de reset le PlayerData pour un tour, c'est-à-dire que les dés et le compteur de reroll sont remis à zéro
	 */
	public void newTurn() {
		this.d1.set(0);
		this.d2.set(0);
		this.d3.set(0);
		this.setRerollCount(0);
	}

	public final IntegerProperty d1Property() {
		return this.d1;
	}
	public final IntegerProperty d2Property() {
		return this.d2;
	}
	public final IntegerProperty d3Property() {
		return this.d3;
	}

	public final int[] getDices() {
		return new int[]{d1.get(), d2.get(), d3.get()};
	}

	public final void setDices(int[] dices) {
		this.d1.set(dices[0]);
		this.d2.set(dices[1]);
		this.d3.set(dices[2]);
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
