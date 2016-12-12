package ihmTable.controller;

import java.io.IOException;

import data.GameTable;
import data.client.InterImplDataTable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class GameStatsController {

    @FXML
    private AnchorPane GameStatsView;

    @FXML
    private Label GameStats_PhaseLabel;

    @FXML
    private Label GameStats_BestScoreLabel;

    @FXML
    private Label GameStats_BestScorePlayer;

    @FXML
    private Label GameStats_ScoreToBeatLabel;

    @FXML
    private Label GameStats_ScoreToBeatPlayer;

    @FXML
    private Label GameStats_StakeLabel;

    @FXML
    private Button GameStats_LaunchButton;

    public GameTable gameTableInstance;

    private InterImplDataTable interImplDataTable;
    
    public void initialize() throws IOException {
    	setLabel();
    	setData();
    	handleAsserts();
    }
	
	private void setLabel() {
		GameStats_PhaseLabel.setText(String.valueOf(gameTableInstance.getGameState()));
		GameStats_StakeLabel.setText("0");
		GameStats_BestScoreLabel.setText("0 0 0");
		GameStats_BestScorePlayer.setText("0");
		GameStats_ScoreToBeatLabel.setText("0 0 0");
		GameStats_ScoreToBeatPlayer.setText("0");
	}
	
	public void setData(InterImplDataTable interImplDataTable, User user) {
		this.interImplDataTable = interImplDataTable;
		gameTableInstance = interImplDataTable.getActualTable();
		Bindings();
	}

	private void Bindings() {
		// TODO Quand Data a implémenté la fonction pour récupérer la meilleure valeur, le joueur, la pire valeur, le joueur
//		gameTableInstance.ElementBestScore().addListener((observable, oldValue, newValue) -> bestScoreListener(observable, oldValue, newValue));
//		gameTableInstance.ElementBestScorePlayer().addListener((observable, oldValue, newValue) -> bestScorePlayerListener(observable, oldValue, newValue));
//		gameTableInstance.ElementScoreToBeat().addListener((observable, oldValue, newValue) -> ScoreToBeatListener(observable, oldValue, newValue));
//		gameTableInstance.ElementScoreToBeatPlayer().addListener((observable, oldValue, newValue) -> ScoreToBeatPlayerListener(observable, oldValue, newValue));
	}
	
	private Object bestScoreListener(ObservableValue<? extends String> observable, String oldValue,
			String newValue) {
		GameStats_BestScoreLabel.setText(String.valueOf(newValue));
		return null;
	}
	
	private Object bestScorePlayerListener(ObservableValue<? extends Number> observable, Number oldValue,
			Number newValue) {
		GameStats_BestScorePlayer.setText(String.valueOf(newValue));
		return null;
	}
	
	private Object ScoreToBeatListener(ObservableValue<? extends String> observable, String oldValue,
			String newValue) {
		GameStats_BestScoreLabel.setText(String.valueOf(newValue));
		return null;
	}
	
	private Object ScoreToBeatPlayerListener(ObservableValue<? extends Number> observable, Number oldValue,
			Number newValue) {
		GameStats_BestScorePlayer.setText(String.valueOf(newValue));
		return null;
	}
	
    private void handleAsserts(){
    	assert GameStatsView != null : "fx:id=\"GameStatsView\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_PhaseLabel != null : "fx:id=\"GameStats_PhaseLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_BestScoreLabel != null : "fx:id=\"GameStats_BestScoreLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_ScoreToBeatLabel != null : "fx:id=\"GameStats_ScoreToBeatLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_StakeLabel != null : "fx:id=\"GameStats_StakeLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_LaunchButton != null : "fx:id=\"GameStats_LaunchButton\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_BestScorePlayer != null : "fx:id=\"GameStats_BestScorePlayer\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_ScoreToBeatPlayer != null : "fx:id=\"GameStats_ScoreToBeatPlayer\" was not injected: check your FXML file 'GameStats.fxml'.";  	
    }


}