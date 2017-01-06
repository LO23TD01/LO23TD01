package ihmTable.controller;

import data.client.InterImplDataTable;
import ihmTable.api.IHMTableLobbyImpl;
import ihmTable.util.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class MenuController {

	@FXML
    private HBox menuView;

	@FXML
    private MenuButton gameButton;

    @FXML
    private MenuItem quitItem;

    @FXML
    private Label title;


    public void initialize() {
    	setPrefProperties();
    	this.quitItem.setOnAction(event -> IHMTableLobbyImpl.showExitModal());
    }

    public void setData(InterImplDataTable interImplDataTable) {
    	this.title.setText(interImplDataTable.getActualTable().getName());
    }

    private void setPrefProperties() {
    	Utility.bindPrefProperties(title, menuView.widthProperty().subtract(gameButton.prefWidthProperty()), menuView.heightProperty());
    }
}
