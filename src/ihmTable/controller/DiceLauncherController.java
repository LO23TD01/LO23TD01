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

public class DiceLauncherController extends PlayerDiceController {

	@FXML
	private VBox diceLauncherView;

	@FXML
    private TilePane diceContainer;

    @FXML
    private Button launchButton;

	private InterImplDataTable interImplDataTable;
	private GameState gameState;
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

			if(this.localUser.isSame(actualPlayer))
				setDiceSelectable(true);

//			//TODO voir avec Data comment gérer la sélection des dés (rerollCount toujours à 0)
//			if(this.playerData.getRerollCount() == 1) {
//				setDiceSelectionOptions(true, false);
//			} else if(this.playerData.getRerollCount() > 1) {
//				setDiceSelectable(true);
//			} else {
//				setDiceSelectionOptions(false, true);
//			}
		}
	}

	//TODO à remove car selection des dés non pris en compte par Data
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
	 * Set all the dice as selectable or not and as selected or not according to the parameters
	 * @param selectable whether the dice are selectable
	 * @param selected whether the dice are selected
	 */
	private void setDiceSelectionOptions(boolean selectable, boolean selected) {
		setDiceSelectable(selectable);
		setDiceSelected(selected);
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

	/**
	 * Set all the dice as selected or not according to the parameter
	 * @param selected whether the dice are selected
	 */
	private void setDiceSelected(boolean selected) {
		this.diceController1.setSelected(selected);
		this.diceController2.setSelected(selected);
		this.diceController3.setSelected(selected);
	}
}
