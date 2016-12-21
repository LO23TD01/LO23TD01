package IHM_MAIN.src.controller;

import IHM_MAIN.src.IHMLobbyAPI;
import IHM_MAIN.src.WaitingWindow;
import data.Parameters;
import data.Rules;
import data.User;
import data.Variant;
import data.GameTable;
import data.client.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ihmTable.api.*;

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

    InterImplDataMain interImplDataMain;
    
	public void setInterfaceData(InterImplDataMain interf){
		this.interImplDataMain = interf;
	}
	

	public void cancel(ActionEvent e){
		Stage stage = (Stage)((Node)(e.getSource())).getScene().getWindow();
		stage.close();
	}
	
	public void ok(){
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
			if(minPlayers.getValue() > maxPlayers.getValue())
				throw new Exception ("Veuillez renseigner un nombre de joueurs minimum inferieur ou egale ou nombre de joueurs maximum");
			if(this.interImplDataMain.getLocalProfile() == null)
				throw new Exception("Erreur Système: Impossible de charger le profil local");
			
		Stage stage = (Stage) tableName.getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Loading.fxml"));
		AnchorPane root;
		root = (AnchorPane) fxmlLoader.load();
		Scene new_scene = new Scene(root);
		Stage waiting = new Stage();
		waiting.initStyle(StageStyle.UNDECORATED);
		waiting.initModality(Modality.WINDOW_MODAL);
		waiting.initOwner(stage);
		waiting.setScene(new_scene);
		new IHMLobbyAPI().setWindowToDestroy(stage);
		waiting.show();
		Thread.sleep(500);
		Timeout timeout = new Timeout(waiting, 3000);
		timeout.start();
		this.interImplDataMain.createNewTable(
				this.interImplDataMain.getLocalProfile().getUUID(),
				tableName.getText(), 
				tablePassword.getText(), 
				minPlayers.getValue().intValue(),
				maxPlayers.getValue().intValue(),
				maxTokens.getValue().intValue(),
				spectatorsAllowed.isSelected(),
				chatEnabled.isSelected(),
				new Rules(Variant.valueOf(rules.getValue()), 3)
			);
		}	
		catch(Exception e){
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}		
	}
	public class Timeout extends Thread{
		Stage waitingWindow;
		int time;
		
		public void run(){
					try {
						Thread.sleep(this.time);
						if(waitingWindow.isShowing()){
							Platform.runLater(new Runnable() {
					            @Override public void run() {
									waitingWindow.close();
									Alert alert = new Alert(AlertType.ERROR, "Temps de réponse du serveur dépassé.");
									alert.showAndWait();
					            }
							});
						}

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();						
					}
		}
		public Timeout(Stage w, int time){
			this.waitingWindow = w;
			this.time = time;
		}
	}

	public void init(){
		this.minPlayers.setValue(3);
		this.maxPlayers.setValue(7);
		this.maxTokens.setValue(21);
		this.tableName.setText("La table du turfu");
	}
}