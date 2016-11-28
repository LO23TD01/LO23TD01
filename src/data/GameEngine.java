package data;

public class GameEngine {
	//C'est l'interface entre le gameState et ServerDataEngine
	//De la sorte on separe le moteur du jeu du reste dans le ServerDataEngine.
	//Conceptuellement on peut dire que c'est une partie du ServerDataEngine.
	
	//Concretement, cela va gerer tout seul la partie, et appellera ServerDataEngine seulement quand un message doit sortir.
	//Ce sera la seule classe qui modifiera le gameState concernant le d�roulement normal d'une partie.
	//Si on arrete la partie (definitivement) il faut d�truire cette objet.
	//Si la partie est finie normalement, on d�truit cet objet.
	//Pour commencer la partie, il faut avoir initialis� correctement le GameState, cr�� le GameEngine, et lancer Game().
		
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
		//verification que l'on est pas d�j� commenc�.
//		if(this.gameState.getState()!=State.PRESTART)
//			throw new Exception("Partie d�j� commenc�. Il faut que la partie ne soit pas commenc� pour la commencer.");
		
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
