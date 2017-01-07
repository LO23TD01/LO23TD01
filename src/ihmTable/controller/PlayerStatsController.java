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

public class PlayerStatsController extends PlayerController {

    private static final String LEFT_GAMES_TOOLTIP = "Nombre de parties abandonnées";
	private static final String LOST_GAMES_TOOLTIP = "Nombre de parties perdues";
	private static final String WON_GAMES_TOOLTIP = "Nombre de parties gagnées";

	@FXML
    private BorderPane playerStatsView;

	@FXML
    private VBox leftContainer;

	@FXML
    private VBox centerContainer;

	@FXML
    private VBox rightContainer;

    @FXML
    private Label wonGames;

    @FXML
    private Label lostGames;

    @FXML
    private Label leftGames;

    @FXML
    private Label title;

    @FXML
    private Label gameStatsTitle;

    @FXML
    private Label informationText;

    private User localUser;

    public void initialize() throws IOException {
    	super.initialize();
		this.wonGames.setTooltip(new Tooltip(WON_GAMES_TOOLTIP));
		this.lostGames.setTooltip(new Tooltip(LOST_GAMES_TOOLTIP));
		this.leftGames.setTooltip(new Tooltip(LEFT_GAMES_TOOLTIP));
	}

    @Override
    public void setData(InterImplDataTable interImplDataTable, User user) {
    	super.setData(interImplDataTable, user);
    	this.localUser = user;
    	this.interImplDataTable.getActualTable().getPlayerList().addListener((ListChangeListener.Change<? extends User> change) -> onPlayerListChanges(change));
    }

    @Override
    protected void updateView() {
    	if(this.gameState.getData(this.user, false) != null) {
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

    private void updateStatsView() {
    	this.wonGames.setText(String.valueOf(user.getPublicData().getNbGameWon()));
    	this.lostGames.setText(String.valueOf(user.getPublicData().getNbGameLost()));
    	this.leftGames.setText(String.valueOf(user.getPublicData().getNbGameAbandonned()));
    }

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
