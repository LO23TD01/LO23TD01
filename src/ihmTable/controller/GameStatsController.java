package ihmTable.controller;

import java.io.IOException;

import data.GameTable;
import data.State;
import data.User;
import data.client.InterImplDataTable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
    	handleAsserts();
    }
	
	private void setLabel() {
		if(gameTableInstance != null)
			GameStats_PhaseLabel.setText(String.valueOf(gameTableInstance.getGameState().getState()));
		GameStats_StakeLabel.setText("0");
		GameStats_BestScoreLabel.setText("0 0 0");
		GameStats_BestScorePlayer.setText("0");
		GameStats_ScoreToBeatLabel.setText("0 0 0");
		GameStats_ScoreToBeatPlayer.setText("0");
	}
	
	public void setData(InterImplDataTable interImplDataTable, User user) {
		this.interImplDataTable = interImplDataTable;
		gameTableInstance = this.interImplDataTable.getActualTable();
		setLabel();
		Bindings();
	}

	private void Bindings() {
		this.gameTableInstance.getGameState().stateProperty()
		.addListener((observable, oldValue, newValue) -> stateListener(observable, oldValue, newValue));

		this.gameTableInstance.getGameState().getActualPlayer().getPublicData().uuidProperty()
		.addListener(event -> actualPlayerChange());

		this.gameTableInstance.getGameState().chipStackProperty()
		.addListener((observable, oldValue, newValue) -> chipStackListener(observable, oldValue, newValue));
	}

	private void actualPlayerChange(){
		GameStats_BestScoreLabel.setText(String.valueOf(this.interImplDataTable.getValueCurrentTurn()));
		GameStats_BestScorePlayer.setText(String.valueOf(
				this.interImplDataTable.getBest().getPlayer().getPublicData().loginProperty()));
		GameStats_ScoreToBeatLabel.setText(String.valueOf(this.interImplDataTable.getValueCurrentTurn()));
		GameStats_ScoreToBeatPlayer.setText(String.valueOf(
				this.interImplDataTable.getBest().getPlayer().getPublicData().loginProperty()));
	}

	
	private Object stateListener(ObservableValue<? extends State> observable, State oldValue,
			State newValue){
		GameStats_PhaseLabel.setText(String.valueOf(newValue));
		return null;
	}
	
	private Object chipStackListener(ObservableValue<? extends Number> observable, Number oldValue,
			Number newValue) {
		GameStats_StakeLabel.setText(String.valueOf(newValue));
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
