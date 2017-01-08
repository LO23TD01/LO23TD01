package ihmTable.controller;

import java.io.IOException;

import data.User;
import data.client.InterImplDataTable;
import ihmTable.util.Utility;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Controller which manages the players stats view. Extends PlayerController
 *
 * @see PlayerController
 */
public class PlayerStatsController extends PlayerController {

    /**
     * Tooltip message for left games label
     *
     * @see PlayerStatsController#leftGames
     */
    private static final String LEFT_GAMES_TOOLTIP = "Nombre de parties abandonnées";
    /**
     * Tooltip message for lost games label
     *
     * @see PlayerStatsController#lostGames
     */
	private static final String LOST_GAMES_TOOLTIP = "Nombre de parties perdues";
	/**
     * Tooltip message for won games label
     *
     * @see PlayerStatsController#wonGames
     */
	private static final String WON_GAMES_TOOLTIP = "Nombre de parties gagnées";

	/**
	 * The main container of the player stats view
	 */
	@FXML
    private BorderPane playerStatsView;

	/**
	 * The left container of the player stats
	 *
	 * @see PlayerStatsController#playerStatsView
	 */
	@FXML
    private VBox leftContainer;

	/**
	 * The center container of the player stats
	 *
	 * @see PlayerStatsController#playerStatsView
	 */
	@FXML
    private VBox centerContainer;

	/**
	 * The right container of the player stats
	 *
	 * @see PlayerStatsController#playerStatsView
	 */
	@FXML
    private VBox rightContainer;

    /**
     * Label which contains the value of won games of the player
     */
    @FXML
    private Label wonGames;

    /**
     * Label which contains the value of lost games of the player
     */
    @FXML
    private Label lostGames;

    /**
     * Label which contains the value of left games of the player
     */
    @FXML
    private Label leftGames;

    /**
     * The label containing the title of the panel
     */
    @FXML
    private Label title;

    /**
     * The title of the game stats part of the view
     */
    @FXML
    private Label gameStatsTitle;

    /**
     * The information label
     */
    @FXML
    private Label informationText;

    /**
     * The local user
     *
     * @see User
     */
    private User localUser;

    /**
     * Initialize the controller
     * @see ihmTable.controller.PlayerController#initialize()
     */
    public void initialize() throws IOException {
    	super.initialize();
		this.wonGames.setTooltip(new Tooltip(WON_GAMES_TOOLTIP));
		this.lostGames.setTooltip(new Tooltip(LOST_GAMES_TOOLTIP));
		this.leftGames.setTooltip(new Tooltip(LEFT_GAMES_TOOLTIP));
	}

    /**
     * Set the data of the controller
     * @see ihmTable.controller.PlayerController#setData(data.client.InterImplDataTable, data.User)
     */
    @Override
    public void setData(InterImplDataTable interImplDataTable, User user) {
    	super.setData(interImplDataTable, user);
    	this.localUser = user;
    	this.interImplDataTable.getActualTable().getPlayerList().addListener((ListChangeListener.Change<? extends User> change) -> onPlayerListChanges(change));
    }

    /**
     * Update the different elements of the view
     * @see ihmTable.controller.PlayerController#updateView()
     */
    @Override
    protected void updateView() {
    	if(this.playerData != null) {
    		super.updateView();
    		this.rightContainer.setVisible(true);
    		this.rightContainer.setManaged(true);
    	} else {
    		this.rightContainer.setVisible(false);
    		this.rightContainer.setManaged(false);
    		updatePlayerView();
    	}
    	updateStatsView();
    }

    /**
     * Update the specific elements of player stats view
     */
    private void updateStatsView() {
    	this.wonGames.setText(String.valueOf(user.getPublicData().getNbGameWon()));
    	this.lostGames.setText(String.valueOf(user.getPublicData().getNbGameLost()));
    	this.leftGames.setText(String.valueOf(user.getPublicData().getNbGameAbandonned()));
    }

    /**
     * If the player displayed leave the table the player stats is updated with the local user
     * @param change the change on player list
     *
     * @see PlayerStatsController#localUser
     */
    private void onPlayerListChanges(ListChangeListener.Change<? extends User> change) {
    	change.next();
    	if(change.getRemoved().contains(user)) {
    		Platform.runLater(new Runnable() {
				@Override
			    public void run() {
					setUser(localUser);
				}
			});
    	}
    }

    /**
     * Set the pref properties (width and height) of the view
     * @see ihmTable.controller.PlayerController#setPrefProperties()
     */
    @Override
    protected void setPrefProperties() {
    	Utility.bindPrefProperties(title, playerStatsView.widthProperty(), playerStatsView.heightProperty().multiply(0.1));
    	Utility.bindPrefProperties(informationText, playerStatsView.widthProperty(), playerStatsView.heightProperty().multiply(0.1));
		Utility.bindPrefProperties(leftContainer, playerStatsView.widthProperty().multiply(0.2), playerStatsView.heightProperty().multiply(0.7));
		Utility.bindPrefProperties(centerContainer, playerStatsView.widthProperty().multiply(0.4), playerStatsView.heightProperty().multiply(0.7));
		Utility.bindPrefProperties(rightContainer, playerStatsView.widthProperty().multiply(0.4), playerStatsView.heightProperty().multiply(0.7));
		Utility.bindPrefProperties(gameStatsTitle, rightContainer.widthProperty(), rightContainer.widthProperty().multiply(0.1));
		Utility.bindPrefProperties(diceContainer, rightContainer.widthProperty(), rightContainer.heightProperty().multiply(0.4));
		Utility.bindPrefProperties(tokens, Bindings.min(rightContainer.widthProperty(), rightContainer.heightProperty().multiply(0.4)), Bindings.min(rightContainer.widthProperty(), rightContainer.heightProperty().multiply(0.4)));
    }
}
