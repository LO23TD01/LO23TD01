package ihmTable.controller;

import java.util.Arrays;

import data.GameState;
import data.State;
import data.client.InterImplDataTable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameStatsController {

	@FXML
	private Label phase;

	@FXML
	private Label turnOf;

	@FXML
	private Label bestScore;

	@FXML
	private Label bestScorePlayer;

	@FXML
	private Label scoreToBeat;

	@FXML
	private Label scoreToBeatPlayer;

	@FXML
	private Label chips;

	private GameState gameState;
	private InterImplDataTable interImplDataTable;

	/**
	 * Initialize the controller
	 */
	public void initialize() {
		this.phase.setText(String.valueOf(State.PRESTART));
		this.turnOf.setText("Unknown");
		this.bestScore.setText("0 0 0");
		this.bestScorePlayer.setText("Unknown");
		this.scoreToBeat.setText("0 0 0");
		this.scoreToBeatPlayer.setText("Unknown");
		this.chips.setText("0");
	}

	/**
	 * Set data to the controller
	 * @param interImplDataTable the Data's interface
	 */
	public void setData(InterImplDataTable interImplDataTable) {
		this.interImplDataTable = interImplDataTable;
		this.gameState = this.interImplDataTable.getActualTable().getGameState();
		addListeners();
	}

	/**
	 * Add listeners to properties in order to update the view
	 */
	private void addListeners() {
		this.gameState.stateProperty().addListener((observable, oldValue, newValue) -> onStateChange(newValue));
		this.gameState.actualPlayerProperty().addListener(event -> onActualPlayerChange());
		this.gameState.chipStackProperty().addListener((observable, oldValue, newValue) -> onChipStackChange(newValue));
	}

	/**
	 * Update the view when the actual player changes
	 */
	private void onActualPlayerChange() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				turnOf.setText(gameState.getData(gameState.getActualPlayer(), false).getPlayer().getSame(gameState.getPlayerList()).getPublicData().getNickName());
				if(!gameState.getDataTieList().isEmpty()) {
					bestScore.setText(Arrays.toString(interImplDataTable.getBest().getDices()));
					bestScorePlayer.setText(interImplDataTable.getBest().getPlayer().getPublicData().getNickName());
					scoreToBeat.setText(Arrays.toString(interImplDataTable.getWorst().getDices()));
					scoreToBeatPlayer.setText(interImplDataTable.getWorst().getPlayer().getPublicData().getNickName());
				}
			}
		});
	}

	/**
	 * Update the phase's label when the phase changes
	 * @param newValue the new phase's name
	 */
	private void onStateChange(State newValue) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				phase.setText(String.valueOf(newValue));
			}
		});
	}

	/**
	 * Update chips' label when the chip stack changes
	 * @param newValue the new chips count
	 */
	private void onChipStackChange(Number newValue) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chips.setText(String.valueOf(newValue));
			}
		});
	}
}
