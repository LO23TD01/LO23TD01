package ihmTable.controller;

import java.io.IOException;

import data.User;
import data.client.InterImplDataTable;
import ihmTable.util.Utility;
import javafx.beans.binding.Bindings;
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

    public void initialize() throws IOException {
    	super.initialize();
		this.wonGames.setTooltip(new Tooltip(WON_GAMES_TOOLTIP));
		this.lostGames.setTooltip(new Tooltip(LOST_GAMES_TOOLTIP));
		this.leftGames.setTooltip(new Tooltip(LEFT_GAMES_TOOLTIP));
	}

    public void setData(InterImplDataTable interImplDataTable, User user) {
    	super.setData(interImplDataTable, user);
    	setUser(user);
    }

    protected void setUser(User user) {
    	super.setUser(user);
    	updateView();
    }

    private void updateView() {
    	this.wonGames.setText(String.valueOf(user.getPublicData().getNbGameWon()));
    	this.lostGames.setText(String.valueOf(user.getPublicData().getNbGameLost()));
    	this.leftGames.setText(String.valueOf(user.getPublicData().getNbGameAbandonned()));
    }

    @Override
    protected void setPrefProperties() {
    	Utility.bindPrefProperties(title, playerStatsView.widthProperty(), playerStatsView.heightProperty().multiply(0.1));
    	Utility.bindPrefProperties(informationText, playerStatsView.widthProperty(), playerStatsView.heightProperty().multiply(0.1));
		Utility.bindPrefProperties(leftContainer, playerStatsView.widthProperty().multiply(0.2), playerStatsView.heightProperty().multiply(0.7));
		Utility.bindPrefProperties(centerContainer, playerStatsView.widthProperty().multiply(0.4), playerStatsView.heightProperty().multiply(0.7));
		Utility.bindPrefProperties(rightContainer, playerStatsView.widthProperty().multiply(0.4), playerStatsView.heightProperty().multiply(0.7));
		Utility.bindPrefProperties(gameStatsTitle, centerContainer.widthProperty(), centerContainer.widthProperty().multiply(0.1));
		Utility.bindPrefProperties(diceContainer, centerContainer.widthProperty(), centerContainer.heightProperty().multiply(0.4));
		Utility.bindPrefProperties(tokens, Bindings.min(centerContainer.widthProperty(), centerContainer.heightProperty().multiply(0.4)), Bindings.min(centerContainer.widthProperty(), centerContainer.heightProperty().multiply(0.4)));
    }
}
