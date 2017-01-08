package ihmTable.controller;

import java.util.Arrays;

import data.GameState;
import data.State;
import data.User;
import data.client.InterImplDataTable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller which manages the game stats view
 */
public class GameStatsController {

	/**
	 * The label containing the phase
	 */
	@FXML
	private Label phase;

	/**
	 * The label containing the nick name of the player who should play
	 */
	@FXML
	private Label turnOf;

	/**
	 * The label containing the best score
	 */
	@FXML
	private Label bestScore;

	/**
	 * The label containing the nick name of the player with the best score
	 */
	@FXML
	private Label bestScorePlayer;

	/**
	 * The label containing the score to beat
	 */
	@FXML
	private Label scoreToBeat;

	/**
	 * The label containing the nick name of the player with the score to beat
	 */
	@FXML
	private Label scoreToBeatPlayer;

	/**
	 * The label containing the value of the chips of the turn
	 */
	@FXML
	private Label turnChips;

	/**
	 * The label containing the value of the remaining chips
	 */
	@FXML
	private Label stackChips;

	/**
	 * The game state
	 *
	 * @see GameState
	 */
	private GameState gameState;
	/**
	 * The interface with Data
	 *
	 * @see InterImplDataTable
	 */
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
		this.stackChips.setText("0");
	}

	/**
	 * Set data to the controller
	 * @param interImplDataTable the Data's interface
	 */
	public void setData(InterImplDataTable interImplDataTable) {
		this.interImplDataTable = interImplDataTable;
		this.gameState = this.interImplDataTable.getActualTable().getGameState();
		addListeners();
		onChipStackChange(this.gameState.getChipStack());
	}

	/**
	 * Add listeners to properties in order to update the view
	 */
	private void addListeners() {
		this.gameState.stateProperty().addListener((observable, oldValue, newValue) -> onStateChange(newValue));
		this.gameState.actualPlayerProperty().addListener((observable, oldValue, newValue) -> onActualPlayerChange(newValue));
		this.gameState.chipStackProperty().addListener((observable, oldValue, newValue) -> onChipStackChange(newValue));
	}

	/**
	 * Update the view when the actual player changes
	 * @param actualPlayer the new actual player
	 */
	private void onActualPlayerChange(User actualPlayer) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				turnOf.setText(actualPlayer.getPublicData().getNickName());
				bestScore.setText(Arrays.toString(interImplDataTable.getBest().getDices()));
				bestScorePlayer.setText(interImplDataTable.getBest().getPlayer().getPublicData().getNickName());
				scoreToBeat.setText(Arrays.toString(interImplDataTable.getWorst().getDices()));
				scoreToBeatPlayer.setText(interImplDataTable.getWorst().getPlayer().getPublicData().getNickName());
				turnChips.setText(String.valueOf(interImplDataTable.getValueCurrentTurn()));
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
				stackChips.setText(String.valueOf(newValue));
			}
		});
	}
}
