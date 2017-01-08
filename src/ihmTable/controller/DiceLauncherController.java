package ihmTable.controller;

import java.io.IOException;

import data.GameState;
import data.User;
import data.client.InterImplDataTable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * Controller which manages the dice launcher view and inherit from PlayerDiceController
 *
 * @see PlayerDiceController
 */
public class DiceLauncherController extends PlayerDiceController {

	/**
	 * The main view of the dice launcher
	 */
	@FXML
	private VBox diceLauncherView;

	/**
	 * The container of the dice
	 */
	@FXML
    private TilePane diceContainer;

    /**
     * The launch button
     */
    @FXML
    private Button launchButton;

	/**
	 * The data interface
	 *
	 * @see InterImplDataTable
	 */
	private InterImplDataTable interImplDataTable;
	/**
	 * The game state
	 *
	 * @see GameState
	 */
	private GameState gameState;
	/**
	 * The local user
	 *
	 * @see User
	 */
	private User localUser;

	/**
	 * Initialize the controller
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		super.initialize();
	}

	/**
	 * Set data to the controller
	 * @param interImplDataTable the Data's interface
	 * @param user the local user
	 */
	public void setData(InterImplDataTable interImplDataTable, User user) {
		this.interImplDataTable = interImplDataTable;
		this.gameState = interImplDataTable.getActualTable().getGameState();
		this.localUser = user;
		addListeners();
	}

	/**
	 * Add listeners to properties in order to update the view
	 */
	private void addListeners() {
		this.launchButton.setOnAction(event -> onLaunchButtonAction());
		this.gameState.actualPlayerProperty().addListener(event -> onActualPlayerChange());
		onActualPlayerChange();
		this.diceController1.selectedProperty().addListener(event -> onSelectedChange());
		this.diceController2.selectedProperty().addListener(event -> onSelectedChange());
		this.diceController3.selectedProperty().addListener(event -> onSelectedChange());
	}

	/**
	 * Update the view and add listeners to dice properties when the actual player changes
	 */
	private void onActualPlayerChange(){
		if(this.gameState.getActualPlayer()!=null)
		{
			User actualPlayer = this.gameState.getActualPlayer();
			setDisableDiceLauncher(!this.localUser.isSame(actualPlayer));
			setPlayerData(this.gameState.getData(actualPlayer, false));

			if(this.localUser.isSame(actualPlayer)) {
				setDiceSelectable(true);
			}
		}
	}

	/**
	 * Inform Data when a dice has been selected or unselected
	 */
	private void onSelectedChange(){
		this.interImplDataTable.selectDice(diceController1.isSelected(), diceController2.isSelected(), diceController3.isSelected());
	}

	/**
	 * Throw the dice on launch button action
	 */
	private void onLaunchButtonAction() {
		this.interImplDataTable.throwDice(diceController1.isSelected(), diceController2.isSelected(), diceController3.isSelected());
	}

	/**
	 * Disable or enable according to parameter the dice launcher view including the launch button and the dice
	 * @param disable whether the dice launcher is disable
	 */
	private void setDisableDiceLauncher(boolean disable) {
		diceLauncherView.setDisable(disable);
		if(disable) {
			setDiceSelectable(false);
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(disable) {
					launchButton.setText("En attente...");
				} else {
					launchButton.setText("Lancer");
				}
			}
		});
	}

	/**
	 * Set all the dice as selectable or not according to the parameter
	 * @param selectable whether the dice are selectable
	 */
	private void setDiceSelectable(boolean selectable) {
		this.diceController1.setSelectable(selectable);
		this.diceController2.setSelectable(selectable);
		this.diceController3.setSelectable(selectable);
	}
}
