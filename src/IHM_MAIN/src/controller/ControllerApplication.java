package IHM_MAIN.src.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Alert.AlertType;
import IHM_MAIN.src.MainApp;
import IHM_MAIN.src.model.Game;
import IHM_MAIN.src.model.ModelApplication;
import data.Profile;
import data.User;
import data.client.*;
import data.GameTable;

public class ControllerApplication {
	//private ModelApplication model;
	@FXML
	TableView<GameTable> currentGames;
	@FXML
	TableColumn<GameTable, String> gameName;
	@FXML
	TableColumn<GameTable, String> players;
	@FXML
	TableColumn<GameTable, String> spectators;
	@FXML
	TableColumn<GameTable, String> owner;
	@FXML
	Button name;
	@FXML
	TextField userSearch;
	@FXML
	Button search;
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

	ClientDataEngine CDEngine = new ClientDataEngine();

	private MainApp mainApp;

	
	public ControllerApplication (){
		//model = new ModelApplication();
	}

	@FXML
    private void initialize() {
		filterTable();
		fillChoiceBox();
	}

	@FXML
	private void handleNameButton() {
	    Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Person Selected");
        alert.setContentText("Please select a person in the table.");
        alert.showAndWait();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../view/GestionProfil.fxml"));
			Window parent = createGame.getScene().getWindow();
			Stage stage = new Stage();
			stage.setScene(new Scene(root, 440, 408));
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(parent);
		    stage.setTitle("Gestion du profil");
		    stage.setResizable(false);
			stage.show();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	@FXML
	private void handleCreateButton() {
		//ouverture fenetre creation nouvelle table
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("../view/tableCreation.fxml"));
					Window parent = createGame.getScene().getWindow();
					Stage stage = new Stage();
					stage.setScene(new Scene(root, 440, 408));
					stage.initModality(Modality.WINDOW_MODAL);
					stage.initOwner(parent);
				    stage.setTitle("Création de Table");
				    stage.setResizable(false);
					stage.show();

				} catch (Exception e1) {
					e1.printStackTrace();
				}
	}

	@FXML
	private void handleSearchButton() {
		//ouverture pop-up utilisateur cherché
	}
		
	StringProperty constructPlayers(CellDataFeatures<GameTable, String> cd){
		String ret = cd.getValue().getPlayerList().size() + "/" + cd.getValue().parametersProperty().getValue().getNbPlayerMax();
		ObservableValue<String> obss = new ReadOnlyObjectWrapper<String>(ret);
		return (StringProperty) obss;
	}

	@FXML
	private void handleRefreshButton() {
		//rafraichissement des tables disponibles
	}
	
	StringProperty constructSpectators(CellDataFeatures<GameTable, String> cd){
		String ret;
		if (cd.getValue().parametersProperty().getValue().authorizeSpecProperty().get() == true)
			ret = "Disabled";
		else
			ret = String.valueOf(cd.getValue().getSpectatorList().size());
		ObservableValue<String> obss = new ReadOnlyObjectWrapper<String>(ret);
		return (StringProperty) obss;
	}
	
	
	
	private void filterTable(){
		gameName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		players.setCellValueFactory(cellData -> constructPlayers(cellData));
		spectators.setCellValueFactory(cellData -> constructSpectators(cellData));
		owner.setCellValueFactory(cellData -> cellData.getValue().creatorProperty().getValue().publicDataProperty().getValue().nickNameProperty());
		
		FilteredList<GameTable> filtered = new FilteredList<>(CDEngine.getTableList(), p -> true);
		
		gameSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filtered.setPredicate(gameTable -> {
                // If filter text is empty, display all games.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare name of every game with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (gameTable.getName().toLowerCase().contains(lowerCaseFilter)) {// Does match.
                	if(full.isSelected())
                		return true;
                	else{
                		if (gameTable.getParameters().getNbPlayerMax() != gameTable.getPlayerList().size())
							return true;
                		else
                			return false;
                    }
                }
                else
                	return false; // Does not match.
            });
        });
		currentGames.setItems(filtered);
	}

	private void fillChoiceBox() {
		nbpl.getItems().removeAll(nbpl.getItems());
	    nbpl.getItems().addAll("0-6", "6-12", "12-18");
	}
}
