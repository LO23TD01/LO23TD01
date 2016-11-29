package IHM_MAIN.src.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;

import IHM_MAIN.src.MainApp;
import IHM_MAIN.src.controller.PersonController;
import IHM_MAIN.src.model.Game;
import IHM_MAIN.src.model.ModelApplication;
import data.Profile;
import data.User;

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

	private MainApp mainApp;

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
	private boolean handleNameButton() {
		 try {
		        // Load the fxml file and create a new stage for the popup dialog.
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(MainApp.class.getResource("view/GestionProfil.fxml"));
		        AnchorPane page = (AnchorPane) loader.load();

		        // Create the dialog Stage.
		        Stage dialogStage = new Stage();
		        dialogStage.setTitle("Edit Person");
		        dialogStage.initModality(Modality.WINDOW_MODAL);
		        Scene scene = new Scene(page);
		        dialogStage.setScene(scene);

		        // Set the person into the controller.
		        PersonController controller = loader.getController();
		        controller.setDialogStage(dialogStage);
	        	File fXmlFile = new File("file:./../monProfile.xml");

		       Profile profil= controller.loadPersonDataFromFile(fXmlFile);
		        //Profile profil = new Profile(null,"test","test","test",25);
		        User user= new User(profil);
		        controller.setPerson(user);

		        // Show the dialog and wait until the user closes it
		        dialogStage.showAndWait();

		        return controller.isOkClicked();
		    } catch (IOException e) {
		        e.printStackTrace();
		        return false;
		    }
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
