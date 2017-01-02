package ihmTable.controller;

import java.io.IOException;

import data.GameTable;
import data.State;
import data.User;
import data.client.InterImplDataTable;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameStatsController {

    @FXML
    private Label phaseLabel;

    @FXML
    private Label bestScoreLabel;

    @FXML
    private Label bestScorePlayer;

    @FXML
    private Label scoreToBeatLabel;

    @FXML
    private Label scoreToBeatPlayer;

    @FXML
    private Label stakeLabel;

    public GameTable gameTableInstance;

    private InterImplDataTable interImplDataTable;


    public void initialize() throws IOException {
    	handleAsserts();
    }

	private void setLabel() {
		if(gameTableInstance != null)
			phaseLabel.setText(String.valueOf(gameTableInstance.getGameState().getState()));
		stakeLabel.setText("0");
		bestScoreLabel.setText("0 0 0");
		bestScorePlayer.setText("0");
		scoreToBeatLabel.setText("0 0 0");
		scoreToBeatPlayer.setText("0");
	}

	public void setData(InterImplDataTable interImplDataTable, User user) {
		this.interImplDataTable = interImplDataTable;
		gameTableInstance = this.interImplDataTable.getActualTable();
		setLabel();
		Bindings();
	}

	private void Bindings() {
		this.gameTableInstance.getGameState().stateProperty().addListener((observable, oldValue, newValue) -> stateListener(observable, oldValue, newValue));

		this.gameTableInstance.getGameState().getActualPlayer().getPublicData().uuidProperty().addListener(event -> actualPlayerChange());

		this.gameTableInstance.getGameState().chipStackProperty().addListener((observable, oldValue, newValue) -> chipStackListener(observable, oldValue, newValue));
	}

	private void actualPlayerChange(){
		bestScoreLabel.setText(String.valueOf(this.interImplDataTable.getValueCurrentTurn()));
		bestScorePlayer.setText(String.valueOf(this.interImplDataTable.getBest().getPlayer().getPublicData().loginProperty()));
		scoreToBeatLabel.setText(String.valueOf(this.interImplDataTable.getValueCurrentTurn()));
		scoreToBeatPlayer.setText(String.valueOf(this.interImplDataTable.getBest().getPlayer().getPublicData().loginProperty()));
	}


	private Object stateListener(ObservableValue<? extends State> observable, State oldValue, State newValue){
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	phaseLabel.setText(String.valueOf(newValue));
		    }
		});
		return null;
	}

	private Object chipStackListener(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		stakeLabel.setText(String.valueOf(newValue));
		return null;
	}

    private void handleAsserts(){
    	assert phaseLabel != null : "fx:id=\"GameStats_PhaseLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert bestScoreLabel != null : "fx:id=\"GameStats_BestScoreLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert scoreToBeatLabel != null : "fx:id=\"GameStats_ScoreToBeatLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert stakeLabel != null : "fx:id=\"GameStats_StakeLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert bestScorePlayer != null : "fx:id=\"GameStats_BestScorePlayer\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert scoreToBeatPlayer != null : "fx:id=\"GameStats_ScoreToBeatPlayer\" was not injected: check your FXML file 'GameStats.fxml'.";
    }
}
