package data;

public class GameEngine {
	//C'est l'interface entre le gameState et ServerDataEngine
	//De la sorte on separe le moteur du jeu du reste dans le ServerDataEngine.
	//Conceptuellement on peut dire que c'est une partie du ServerDataEngine.
	
	//Concretement, cela va gerer tout seul la partie, et appellera ServerDataEngine seulement quand un message doit sortir.
	//Ce sera la seule classe qui modifiera le gameState concernant le déroulement normal d'une partie.
	//Si on arrete la partie (definitivement) il faut détruire cette objet.
	//Si la partie est finie normalement, on détruit cet objet.
	//Pour commencer la partie, il faut avoir initialisé correctement le GameState, créé le GameEngine, et lancer Game().
		
	private GameState gameState;
	private ServerDataEngine parent;
	
	public void Game()
	{
		StartGame();
		FirstPhase();
		SecondPhase();
		End();
		
		//Destruction automatique ici ?
	}
	
	private void StartGame()
	{
		//verification que l'on est pas déjà commencé.
//		if(this.gameState.getState()!=State.PRESTART)
//			throw new Exception("Partie déjà commencé. Il faut que la partie ne soit pas commencé pour la commencer.");
		
		//choisir le premier joueur
		this.gameState.setActualPlayer(this.gameState.getFirstPlayer());
		OneThrow();
		while()
		
	}
	
	private void FirstPhase()
	{
		//boucle 
	}
	private void SecondPhase()
	{
		//boucle
	}
	private void End()
	{
		//appel des focntions de fins.
		
	}
	
	private void OneThrow()
	{
		PlayerData newData = new PlayerData(this.gameState.getData(this.gameState.getActualPlayer()));
		newData.setDices({Dice});
		this.gameState.replaceData())
	}
	

	
}
