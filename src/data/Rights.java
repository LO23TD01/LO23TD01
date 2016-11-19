package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rights {
	private boolean profVisibility;
	private boolean canJoinCreatedGame;
	private boolean canSpectateCreatedGame;
	
	public Rights(){
		
	}
	
	public Rights(boolean profVisibility, boolean canJoinCreatedGame, boolean canSpectateCreatedGame) {
		this.profVisibility = profVisibility;
		this.canJoinCreatedGame = canJoinCreatedGame;
		this.canSpectateCreatedGame = canSpectateCreatedGame;
	}
	
	/*
	 * 
	 * Getters & Setters
	 * 
	 * 
	 */

	public boolean isProfVisibility() {
		return profVisibility;
	}

	@XmlElement
	public void setProfVisibility(boolean profVisibility) {
		this.profVisibility = profVisibility;
	}

	public boolean isCanJoinCreatedGame() {
		return canJoinCreatedGame;
	}

	@XmlElement
	public void setCanJoinCreatedGame(boolean canJoinCreatedGame) {
		this.canJoinCreatedGame = canJoinCreatedGame;
	}

	public boolean isCanSpectateCreatedGame() {
		return canSpectateCreatedGame;
	}

	@XmlElement
	public void setCanSpectateCreatedGame(boolean canSpectateCreatedGame) {
		this.canSpectateCreatedGame = canSpectateCreatedGame;
	}
	
	
}
