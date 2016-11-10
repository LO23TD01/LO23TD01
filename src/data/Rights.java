package data;

public class Rights {
	private boolean profVisibility;
	private boolean canJoinCreatedGame;
	private boolean canSpectateCreatedGame;
	
	public Rights(boolean profVisibility, boolean canJoinCreatedGame, boolean canSpectateCreatedGame) {
		this.profVisibility = profVisibility;
		this.canJoinCreatedGame = canJoinCreatedGame;
		this.canSpectateCreatedGame = canSpectateCreatedGame;
	}
	
}
