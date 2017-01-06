package ihmTable.controller;

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
    private MenuItem informationItem;

    @FXML
    private MenuItem quitItem;

    @FXML
    private Label title;


    public void initialize() {
    	setPrefProperties();
    }

    private void setPrefProperties() {
    	Utility.bindPrefProperties(title, menuView.widthProperty().subtract(gameButton.prefWidthProperty()), menuView.heightProperty());
    }
}
