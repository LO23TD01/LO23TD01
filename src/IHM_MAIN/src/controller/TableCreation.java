package IHM_MAIN.src.controller;

import IHM_MAIN.src.MainApp.DataConnection;
import IHM_MAIN.src.WaitingWindow;
import data.Parameters;
import data.Rules;
import data.User;
import data.Variant;
import data.client.ClientDataEngine;
import data.client.InterfaceDataIHMLobby;
import data.Client;
import data.GameTable;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableCreation{
		
    @FXML
    CheckBox spectatorsAllowed;

    @FXML
    PasswordField tablePassword;

    @FXML
    ComboBox<Integer> maxPlayers;

    @FXML
    ComboBox<Integer> minPlayers;

    @FXML
    ComboBox<Integer> maxTokens;

    @FXML
    CheckBox chatEnabled;

    @FXML
    TextField tableName;

    @FXML
    ComboBox<String> rules;

    data.client.ClientDataEngine clientData;
    data.client.InterfaceDataIHMLobby interfaceData;

    public void setClientData(ClientDataEngine client){
		this.clientData = client;
	}
	public void setInterfaceDataIHM(InterfaceDataIHMLobby interf){
		this.interfaceData = interf;
	}
	

	public void cancel(ActionEvent e){
		Stage stage = (Stage)((Node)(e.getSource())).getScene().getWindow();
		stage.close();
	}
	
	public void ok(){
		// data.createNewTable(name, pwd, min, max, max_token, withSpec, withChat);
		/*Alert alert = new Alert(AlertType.INFORMATION, "Signal sent to data, preparing party:\n"
				+ "Table Name:"+tableName.getText()+"\n" 
				+ "Password:"+tablePassword.getText()+"\n"
				+ "Max Players:"+maxPlayers.getValue()+"\n"
				+ "Min Players:"+minPlayers.getValue()+"\n"
				+ "Max Tokens:"+maxTokens.getValue()+"\n"
				+ "With Chat:"+chatEnabled.isSelected()+"\n"
				+ "With Spec:"+spectatorsAllowed.isSelected()+"\n");
		alert.showAndWait();
		Stage stage = (Stage) tableName.getScene().getWindow();
		stage.close();*/
		//public Parameters(int nbPlayerMin, int nbPlayerMax, int nbChip, boolean authorizeSpec, boolean authorizeSpecToChat, Rules rules) {
		try{
			
			if(tableName.getText().isEmpty())
				throw new Exception("Veuillez renseigner le nom de la Table");
			if(minPlayers.getValue() == null)
				throw new Exception("Veuillez renseigner un nombre de joueurs minimum");
			if(maxPlayers.getValue() == null)
				throw new Exception("Veuillez renseigner un nombre de joueurs maximum");
			if(maxTokens.getValue() == null)
				throw new Exception("Veuillez renseigner un nombre de jetons");
			if(rules.getValue() == null)
				throw new Exception("Veuillez sélectionner une règle de jeu");
			if(this.clientData.getLocalProfile() == null)
				throw new Exception("Erreur Système: Impossible de charger le profil local");

		List<User> playersList = new ArrayList<User>();
		User user = new User(this.clientData.getLocalProfile());
		playersList.add(user);
		
		GameTable game = new GameTable(
				tableName.getText(), 
				user, 
				new Parameters(minPlayers.getValue(), 
				maxPlayers.getValue(), 
				maxTokens.getValue(),
				spectatorsAllowed.isSelected(),
				chatEnabled.isSelected(),
				new Rules(Variant.valueOf(rules.getValue()), 3)),
				playersList, 
				new ArrayList<User>()
				);
		
		}
		catch(Exception e){
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
		//open game table with game

		Stage stage = (Stage) tableName.getScene().getWindow();
		WaitingWindow t = new WaitingWindow(stage);
		DataConnection data = new DataConnection(t);
		data.start();
		t.showAndWait();
		this.open_table();
		stage.close();
		
	}
	public void open_table(){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../ihmTable/resources/view/Table.fxml"));
			Pane root;
			root = (Pane) fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Table");
			stage.setScene(scene);
			stage.setAlwaysOnTop(true);
	        stage.initModality(Modality.WINDOW_MODAL);
			stage.setMaximized(true);
			stage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class DataConnection extends Thread{
		WaitingWindow waitingWindow;
		
		public void run(){
			try {
					Thread.sleep(500);
					waitingWindow.close();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public DataConnection(WaitingWindow t){
			waitingWindow = t;
		}
	}

}