package data;

public class Parameters {
	private int nbPlayerMin;
	private int nbPlayerMax;
	private int nbChip;
	private boolean authorizeSpec;
	private boolean authorizeSpecToChat;
	private Rules rules;

	/**
	 * @param nbPlayerMin
	 * @param nbPlayerMax
	 * @param nbChip
	 * @param authorizeSpec
	 * @param authorizeSpecToChat
	 * @param rules
	 */
	public Parameters(int nbPlayerMin, int nbPlayerMax, int nbChip, boolean authorizeSpec, boolean authorizeSpecToChat,
			Rules rules) {
		super();
		this.nbPlayerMin = nbPlayerMin;
		this.nbPlayerMax = nbPlayerMax;
		this.nbChip = nbChip;
		this.authorizeSpec = authorizeSpec;
		this.authorizeSpecToChat = authorizeSpecToChat;
		this.rules = rules;
	}

	/**
	 * @return the nbPlayerMin
	 */
	public int getNbPlayerMin() {
		return nbPlayerMin;
	}

	/**
	 * @param nbPlayerMin
	 *            the nbPlayerMin to set
	 */
	public void setNbPlayerMin(int nbPlayerMin) {
		this.nbPlayerMin = nbPlayerMin;
	}

	/**
	 * @return the nbPlayerMax
	 */
	public int getNbPlayerMax() {
		return nbPlayerMax;
	}

	/**
	 * @param nbPlayerMax
	 *            the nbPlayerMax to set
	 */
	public void setNbPlayerMax(int nbPlayerMax) {
		this.nbPlayerMax = nbPlayerMax;
	}

	/**
	 * @return the nbChip
	 */
	public int getNbChip() {
		return nbChip;
	}

	/**
	 * @param nbChip
	 *            the nbChip to set
	 */
	public void setNbChip(int nbChip) {
		this.nbChip = nbChip;
	}

	/**
	 * @return the authorizeSpec
	 */
	public boolean isAuthorizeSpec() {
		return authorizeSpec;
	}

	/**
	 * @param authorizeSpec
	 *            the authorizeSpec to set
	 */
	public void setAuthorizeSpec(boolean authorizeSpec) {
		this.authorizeSpec = authorizeSpec;
	}

	/**
	 * @return the authorizeSpecToChat
	 */
	public boolean isAuthorizeSpecToChat() {
		return authorizeSpecToChat;
	}

	/**
	 * @param authorizeSpecToChat
	 *            the authorizeSpecToChat to set
	 */
	public void setAuthorizeSpecToChat(boolean authorizeSpecToChat) {
		this.authorizeSpecToChat = authorizeSpecToChat;
	}

	/**
	 * @return the rules
	 */
	public Rules getRules() {
		return rules;
	}

	/**
	 * @param rules
	 *            the rules to set
	 */
	public void setRules(Rules rules) {
		this.rules = rules;
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
		result = prime * result + (authorizeSpec ? 1231 : 1237);
		result = prime * result + (authorizeSpecToChat ? 1231 : 1237);
		result = prime * result + nbChip;
		result = prime * result + nbPlayerMax;
		result = prime * result + nbPlayerMin;
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
		return "Parameters [nbPlayerMin=" + nbPlayerMin + ", nbPlayerMax=" + nbPlayerMax + ", nbChip=" + nbChip
				+ ", authorizeSpec=" + authorizeSpec + ", authorizeSpecToChat=" + authorizeSpecToChat + ", rules="
				+ rules + "]";
	}

}
