package data;

import java.util.Arrays;

public class PlayerData {
	private User player;
	private int chip;
	private int[] dices;
	private int rerollCount;

	/**
	 * @param parent
	 * @param player
	 */
	public PlayerData(User player) {
		super();
		this.player = player;
		this.chip = 0;
		this.dices = new int[3];
		for (int i = 0; i < dices.length; i++)
			dices[i] = 0;
		this.rerollCount = 0;
	}

	/**
	 * @param player
	 * @param chip
	 * @param dices
	 * @param rerollCount
	 */
	public PlayerData(User player, int chip, int[] dices, int rerollCount) {
		super();
		this.player = player;
		this.chip = chip;
		this.dices = dices;
		this.rerollCount = rerollCount;
	}

	/*
	 * Permet de reset le PlayerData pour un tour, c'est-à-dire que les dés et
	 * le compteur de reroll sont remis à zéro
	 */
	public void newTurn() {
		for (int i = 0; i < dices.length; i++)
			dices[i] = 0;
		this.rerollCount = 0;
	}

	/**
	 * @return the player
	 */
	public User getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(User player) {
		this.player = player;
	}

	/**
	 * @return the chip
	 */
	public int getChip() {
		return chip;
	}

	/**
	 * @param chip
	 *            the chip to set
	 */
	public void setChip(int chip) {
		this.chip = chip;
	}

	/**
	 * @return the dices
	 */
	public int[] getDices() {
		return dices;
	}

	/**
	 * @param dices
	 *            the dices to set
	 */
	public void setDices(int[] dices) {
		this.dices = dices;
	}

	/**
	 * @return the rerollCount
	 */
	public int getRerollCount() {
		return rerollCount;
	}

	/**
	 * @param rerollCount
	 *            the rerollCount to set
	 */
	public void setRerollCount(int rerollCount) {
		this.rerollCount = rerollCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + chip;
		result = prime * result + Arrays.hashCode(dices);
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + rerollCount;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerData other = (PlayerData) obj;
		if (chip != other.chip)
			return false;
		if (!Arrays.equals(dices, other.dices))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (rerollCount != other.rerollCount)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PlayerData [player=" + player + ", chip=" + chip + ", dices=" + Arrays.toString(dices)
				+ ", rerollCount=" + rerollCount + "]";
	}

}
