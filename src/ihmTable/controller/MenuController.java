package ihmTable.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MenuController {

    @FXML
    private AnchorPane menuView;

    @FXML
    private HBox Menu_Hbox;

    @FXML
    private MenuButton menu_partie;

    @FXML
    private MenuItem Menu_QuitterItem;

    @FXML
    private MenuItem Menu_OptionsItem;

    @FXML
    private MenuButton menu_affichage;

    @FXML
    private RadioMenuItem Menu_ReglesItem;

    @FXML
    private RadioMenuItem Menu_ChatItem;

    @FXML
    private RadioMenuItem Menu_ControleItem;

    @FXML
    private Button menu_apropos;
    
    public void initialize() throws IOException {
    	handleAsserts();
    }
    
    private void handleAsserts(){
    	assert menuView != null : "fx:id=\"menuView\" was not injected: check your FXML file 'Menu.fxml'.";
    	assert Menu_Hbox != null : "fx:id=\"Menu_Hbox\" was not injected: check your FXML file 'Menu.fxml'.";
    	assert menu_partie != null : "fx:id=\"menu_partie\" was not injected: check your FXML file 'Menu.fxml'.";
        assert Menu_QuitterItem != null : "fx:id=\"Menu_QuitterItem\" was not injected: check your FXML file 'Menu.fxml'.";
    	assert Menu_OptionsItem != null : "fx:id=\"Menu_OptionsItem\" was not injected: check your FXML file 'Menu.fxml'.";
    	assert menu_affichage != null : "fx:id=\"menu_affichage\" was not injected: check your FXML file 'Menu.fxml'.";
    	assert Menu_ReglesItem != null : "fx:id=\"Menu_ReglesItem\" was not injected: check your FXML file 'Menu.fxml'.";
    	assert Menu_ChatItem != null : "fx:id=\"Menu_ChatItem\" was not injected: check your FXML file 'Menu.fxml'.";
    	assert Menu_ControleItem != null : "fx:id=\"Menu_ControleItem\" was not injected: check your FXML file 'Menu.fxml'.";
    	assert menu_apropos != null : "fx:id=\"menu_apropos\" was not injected: check your FXML file 'Menu.fxml'.";
    }

}
