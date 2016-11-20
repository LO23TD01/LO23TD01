package IHM_MAIN.src.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import IHM_MAIN.src.model.Game;
import IHM_MAIN.src.model.ModelApplication;

public class ControllerApplication {
	//private ModelApplication model;
	@FXML
	TableView<Game> currentGames;
	@FXML
	TableColumn<Game, String> gameName;
	@FXML
	TableColumn<Game, String> players;
	@FXML
	TableColumn<Game, String> spectators;
	@FXML
	TableColumn<Game, String> owner;
	@FXML
	Button name;
	@FXML
	TextField userSearch;
	@FXML
	Button search;
	@FXML
	Button refresh;
	@FXML
	Button createGame;
	@FXML
	ComboBox<String> nbpl;
	@FXML
	TextField gameSearch;
	@FXML
	CheckBox full;
	@FXML
	CheckBox privacy;
	
	public ObservableList<Game> data = FXCollections.observableArrayList(
		    new Game("YoloGame", "3/12", "44", "F�lix"),
		    new Game("SwaggyOne", "6/6", "2", "Cl�ment"),
		    new Game("Java", "4/8", "Disabled", "Jo")
	);
	
	public ObservableList<Game> getGameData() {
        return data;
    }
	
	public ControllerApplication (){
		//model = new ModelApplication();
	}
	
	@FXML
    private void initialize() {
		fillTable();
		fillChoiceBox();
	}

	@FXML
	private void handleNameButton() {
		//ouverture fenetre gestion profil
	}
	
	@FXML
	private void handleCreateButton() {
		//ouverture fenetre creation nouvelle table
	}
	
	@FXML
	private void handleSearchButton() {
		//ouverture pop-up utilisateur cherch�
	}
	
	@FXML
	private void handleRefreshButton() {
		//rafraichissement des tables disponibles
	}
	
	private void fillTable() {
		gameName.setCellValueFactory(cellData -> cellData.getValue().getName());
		players.setCellValueFactory(cellData -> cellData.getValue().getPlayers());
		spectators.setCellValueFactory(cellData -> cellData.getValue().getSpectators());
		owner.setCellValueFactory(cellData -> cellData.getValue().getOwner());
		currentGames.setItems(getGameData());
	}
	
	private void fillChoiceBox() {
		nbpl.getItems().removeAll(nbpl.getItems());
	    nbpl.getItems().addAll("0-6", "6-12", "12-18");
	}
}
