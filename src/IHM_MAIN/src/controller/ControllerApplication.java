package IHM_MAIN.src.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import IHM_MAIN.src.IHMLobbyAPI;
import IHM_MAIN.src.IHMLobbyAPI.IncompleteProfileException;
import IHM_MAIN.src.MainApp;
import data.User;
import data.client.*;
import data.GameTable;
import data.Profile;
import data.client.*;
import data.Profile;
import data.User;

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
	TableColumn<GameTable, String> status;
	@FXML
	Button name;
	@FXML
	TextField userSearch;
	@FXML
	Button search;
	@FXML
	Button createGame;
	@FXML
	TextField gameSearch;
	@FXML
	ListView<User> connectedUsers;
	GameTable tempGame;
	Date lastClickGame;
	User tempUser;
	Date lastClickUser;

	private MainApp mainApp;
	InterImplDataMain interImplDataMain;
	IHMLobbyAPI interfaceLobby;

	public void setInterfaceData(InterImplDataMain interf){
		this.interImplDataMain = interf;
	}

	public ControllerApplication (){
		//model = new ModelApplication();
	}

    public void init() {
		filterTable();
		fillListView();
		//ObservableList<GameTable> yolo = interImplDataMain.getTableList();
		System.out.println(interImplDataMain.getTableList());
		interfaceLobby = new IHMLobbyAPI();
	}

	@FXML
	private void handleNameButton() {
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
		        controller.setInterImplDataMain(this.interImplDataMain);
	        	//File fXmlFile = new File("file:./../monProfile.xml");
		        //InterImplDataMain interImplDataMain = mainApp.getInterImplDataMain();
		       Profile profil = this.interImplDataMain.getLocalProfile();


		        controller.setPerson(profil);

		        // Show the dialog and wait until the user closes it
		        dialogStage.showAndWait();

		       // return controller.isOkClicked();
		    } catch (IOException er) {
		        er.printStackTrace();
		       // return false;
		    }
	}


	@FXML
	private void handleCreateButton() {
		//ouverture fenetre creation nouvelle table
				Parent root;
				try {
					/*==>After merge*/
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/tableCreation.fxml"));

					root = fxmlLoader.load();
					//root = FXMLLoader.load(getClass().getResource("../view/tableCreation.fxml"));
					TableCreation controller = (TableCreation) fxmlLoader.getController();
					controller.setInterfaceData(this.interImplDataMain);

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


	StringProperty constructPlayers(CellDataFeatures<GameTable, String> cd){
		String ret = cd.getValue().getPlayerList().size() + "/" + cd.getValue().parametersProperty().getValue().getNbPlayerMax();
		//System.out.println(cd.getValue().getCreator().getPublicData().getNickName());
		//ObservableValue<String> obss = new ReadOnlyObjectWrapper<String>(ret);
		StringProperty st = new SimpleStringProperty();
		st.setValue(ret);
		return st;
	}


	StringProperty constructSpectators(CellDataFeatures<GameTable, String> cd){
		String ret;
		if (cd.getValue().parametersProperty().getValue().authorizeSpecProperty().get() == false)
			ret = "Disabled";
		else
			ret = String.valueOf(cd.getValue().getSpectatorList().size());
		StringProperty st = new SimpleStringProperty();
		st.setValue(ret);
		return st;
	}

	StringProperty constructState(CellDataFeatures<GameTable, String> cd){
		String ret;
		ret = cd.getValue().gameStateProperty().getValue().getState().toString();
		if (ret == "PRESTART")
			ret = "En attente";
		else
			ret = "En cours";
		StringProperty st = new SimpleStringProperty();
		st.setValue(ret);
		return st;
	}


	private void filterTable(){

		gameName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		players.setCellValueFactory(cellData -> constructPlayers(cellData));
		spectators.setCellValueFactory(cellData -> constructSpectators(cellData));
		owner.setCellValueFactory(cellData -> cellData.getValue().creatorProperty().getValue().getSame(interImplDataMain.getUserList()).publicDataProperty().getValue().nickNameProperty());
		status.setCellValueFactory(cellData -> constructState(cellData));

		FilteredList<GameTable> filtered = new FilteredList<>(interImplDataMain.getTableList(), p -> true);

		gameSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filtered.setPredicate(gameTable -> {
                // If filter text is empty, display all games.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare name of every game with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (gameTable.getName().toLowerCase().contains(lowerCaseFilter))// Does match.
					return true;
                else
                	return false; // Does not match.
            });
        });
		currentGames.setItems(filtered);
	}



	private void fillListView(){
		ObservableList<User> items = interImplDataMain.getUserList();
		connectedUsers.setItems(items);
		connectedUsers.setCellFactory(new Callback<ListView<User>, ListCell<User>>(){
            public ListCell<User> call(ListView<User> p) {
                ListCell<User> cell = new ListCell<User>(){
                    @Override
                    protected void updateItem(User u, boolean bln) {
                        super.updateItem(u, bln);
                        if (u != null) {
                            setText(u.getPublicData().getNickName());
                        } else {
                        	setText("");
                        }
                    }
                };
                 return cell;
            }
        });
	}

	@FXML
	private void handleRowSelectGame(){
	    GameTable row = currentGames.getSelectionModel().getSelectedItem();
	    if (row==null) return;
	    if(row!=tempGame){
	        tempGame=row;
	        lastClickGame=new Date();
	    } else if(row==tempGame) {
	        Date now = new Date();
	        long diff = now.getTime() - lastClickGame.getTime();
	        if (diff < 300){
	     		//Parent root;
	     		try {
	     			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/joinTable.fxml"));
	     			AnchorPane root;
	    			root = (AnchorPane) fxmlLoader.load();
	     			Window parent = createGame.getScene().getWindow();
	     			joinTableController controller = (joinTableController) fxmlLoader.getController();
	    			controller.setInterfaceData(this.interImplDataMain);
	    			controller.setJoiningGame(tempGame);

	     			Stage stage = new Stage();
	     			stage.setScene(new Scene(root, 440, 408));
	     			stage.initModality(Modality.WINDOW_MODAL);
	     			stage.initOwner(parent);
	     		    stage.setTitle("Rejoindre une partie");
	     		    stage.setResizable(false);
	     			stage.show();

	     		} catch (Exception e1) {
	     			e1.printStackTrace();
	     		}
	        } else {
	            lastClickGame = new Date();
	        }
	    }
	}

	@FXML
	private void handleRowSelectUser() throws IncompleteProfileException{

	    User row = connectedUsers.getSelectionModel().getSelectedItem();
		//System.out.println(row.getPublicData().getLogin());
	    if (row==null) return;
	    if(row!=tempUser){
	        tempUser=row;
	        lastClickUser=new Date();
	    } else if(row==tempUser) {
	        Date now = new Date();
	        long diff = now.getTime() - lastClickUser.getTime();
	        if (diff < 300){
	        	System.out.println("on affiche le profil!!");
	        	System.out.println(interfaceLobby);
				interfaceLobby.displayProfile(row.getPublicData(), createGame.getScene().getWindow());
	        } else {
	            lastClickUser = new Date();
	        }
	    }
	}
}
