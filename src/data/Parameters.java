package data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Parameters {
	private final IntegerProperty nbPlayerMin;
	private final IntegerProperty nbPlayerMax;
	private final IntegerProperty nbChip;
	private final BooleanProperty authorizeSpec;
	private final BooleanProperty authorizeSpecToChat;
	private final ObjectProperty<Rules> rules;

	/**
	 * @param nbPlayerMin
	 * @param nbPlayerMax
	 * @param nbChip
	 * @param authorizeSpec
	 * @param authorizeSpecToChat
	 * @param rules
	 */
	public Parameters(int nbPlayerMin, int nbPlayerMax, int nbChip, boolean authorizeSpec, boolean authorizeSpecToChat, Rules rules) {
		super();
		this.nbPlayerMin = new SimpleIntegerProperty(nbPlayerMin);
		this.nbPlayerMax = new SimpleIntegerProperty(nbPlayerMax);
		this.nbChip = new SimpleIntegerProperty(nbChip);
		this.authorizeSpec = new SimpleBooleanProperty(authorizeSpec);
		this.authorizeSpecToChat = new SimpleBooleanProperty(authorizeSpecToChat);
		this.rules = new SimpleObjectProperty<Rules>(rules);
	}

	public final BooleanProperty authorizeSpecProperty() {
		return this.authorizeSpec;
	}

	/**
	 * @return the authorizeSpec
	 */
	public final boolean isAuthorizeSpec() {
		return authorizeSpec.get();
	}

	/**
	 * @param authorizeSpec
	 *            the authorizeSpec to set
	 */
	public final void setAuthorizeSpec(boolean authorizeSpec) {
		this.authorizeSpec.set(authorizeSpec);
	}

	public final BooleanProperty authorizeSpecToChatProperty() {
		return this.authorizeSpecToChat;
	}

	/**
	 * @return the authorizeSpecToChat
	 */
	public final boolean isAuthorizeSpecToChat() {
		return this.authorizeSpecToChat.get();
	}

	/**
	 * @param authorizeSpecToChat
	 *            the authorizeSpecToChat to set
	 */
	public final void setAuthorizeSpecToChat(boolean authorizeSpecToChat) {
		this.authorizeSpecToChat.set(authorizeSpecToChat);
	}

	public final IntegerProperty nbPlayerMinProperty() {
		return this.nbPlayerMin;
	}

	public final int getNbPlayerMin() {
		return this.nbPlayerMinProperty().get();
	}

	public final void setNbPlayerMin(final int nbPlayerMin) {
		this.nbPlayerMinProperty().set(nbPlayerMin);
	}

	public final IntegerProperty nbPlayerMaxProperty() {
		return this.nbPlayerMax;
	}

	public final int getNbPlayerMax() {
		return this.nbPlayerMaxProperty().get();
	}

	public final void setNbPlayerMax(final int nbPlayerMax) {
		this.nbPlayerMaxProperty().set(nbPlayerMax);
	}

	public final IntegerProperty nbChipProperty() {
		return this.nbChip;
	}

	public final int getNbChip() {
		return this.nbChipProperty().get();
	}

	public final void setNbChip(final int nbChip) {
		this.nbChipProperty().set(nbChip);
	}

	public final ObjectProperty<Rules> rulesProperty() {
		return this.rules;
	}

	public final Rules getRules() {
		return this.rulesProperty().get();
	}

	public final void setRules(final Rules rules) {
		this.rulesProperty().set(rules);
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
		result = prime * result + (authorizeSpec.get() ? 1231 : 1237);
		result = prime * result + (authorizeSpecToChat.get() ? 1231 : 1237);
		result = prime * result + nbChip.get();
		result = prime * result + nbPlayerMax.get();
		result = prime * result + nbPlayerMin.get();
		result = prime * result + ((rules == null) ? 0 : rules.hashCode());
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
		Parameters other = (Parameters) obj;
		if (authorizeSpec != other.authorizeSpec)
			return false;
		if (authorizeSpecToChat != other.authorizeSpecToChat)
			return false;
		if (nbChip != other.nbChip)
			return false;
		if (nbPlayerMax != other.nbPlayerMax)
			return false;
		if (nbPlayerMin != other.nbPlayerMin)
			return false;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
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
		return "";
		//TODO again
		/*
		return "Parameters [nbPlayerMin=" + nbPlayerMin + ", nbPlayerMax=" + nbPlayerMax + ", nbChip=" + nbChip
				+ ", authorizeSpec=" + authorizeSpec + ", authorizeSpecToChat=" + authorizeSpecToChat + ", rules="
				+ rules + "]";*/
	}

}
