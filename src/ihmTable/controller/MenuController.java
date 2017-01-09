package ihmTable.controller;

import data.client.InterImplDataTable;
import ihmTable.api.IHMTableLobbyImpl;
import ihmTable.util.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

/**
 * Class which manages the menu view
 */
public class MenuController {

	/**
	 * The main menu view container
	 */
	@FXML
    private HBox menuView;

	/**
	 * The game button
	 */
	@FXML
    private MenuButton gameButton;

    /**
     * The quit item
     */
    @FXML
    private MenuItem quitItem;

    /**
     * The label containing the name of the table
     */
    @FXML
    private Label title;


    /**
     * Initialize the controller
     */
    public void initialize() {
    	setPrefProperties();
    	this.quitItem.setOnAction(event -> IHMTableLobbyImpl.showExitModal());
    }

    /**
     * Set data to the controller
	 * @param interImplDataTable the Data's interface
     */
    public void setData(InterImplDataTable interImplDataTable) {
    	this.title.setText("Table " + interImplDataTable.getActualTable().getName());
    }

    /**
	 * Set pref properties (width and height) in order to resize the different elements when the view is resized
	 */
    private void setPrefProperties() {
    	Utility.bindPrefProperties(title, menuView.widthProperty().subtract(gameButton.prefWidthProperty()), menuView.heightProperty());
    }
}
