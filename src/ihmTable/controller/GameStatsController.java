package ihmTable.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameStatsController {

    @FXML
    private AnchorPane GameStatsView;

    @FXML
    private AnchorPane GameStats_PhasePane;

    @FXML
    private Label GameStats_PhaseLabel;

    @FXML
    private AnchorPane GameStats_BestScorePane;

    @FXML
    private Label GameStats_BestScoreLabel;

    @FXML
    private AnchorPane GameStats_ScoreToBeatPane;

    @FXML
    private Label GameStats_ScoreToBeatLabel;

    @FXML
    private AnchorPane GameStats_StakePane;

    @FXML
    private Label GameStats_StakeLabel;

    @FXML
    private Button GameStats_LaunchButton;


    public void initialize() throws IOException {
    	handleAsserts();
    }

    private void handleAsserts(){
    	assert GameStatsView != null : "fx:id=\"GameStatsView\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_PhasePane != null : "fx:id=\"GameStats_PhasePane\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_PhaseLabel != null : "fx:id=\"GameStats_PhaseLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
        assert GameStats_BestScorePane != null : "fx:id=\"GameStats_BestScorePane\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_BestScoreLabel != null : "fx:id=\"GameStats_BestScoreLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_ScoreToBeatPane != null : "fx:id=\"GameStats_ScoreToBeatPane\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_ScoreToBeatLabel != null : "fx:id=\"GameStats_ScoreToBeatLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_StakePane != null : "fx:id=\"GameStats_StakePane\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_StakeLabel != null : "fx:id=\"GameStats_StakeLabel\" was not injected: check your FXML file 'GameStats.fxml'.";
    	assert GameStats_LaunchButton != null : "fx:id=\"GameStats_LaunchButton\" was not injected: check your FXML file 'GameStats.fxml'.";
    }


}