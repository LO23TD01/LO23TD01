package IHM_MAIN.src;

import data.IPData;
import data.Profile;
import data.User;
import data.client.ClientDataEngine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.Callable;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import IHM_MAIN.src.controller.PersonController;

public class MainApp extends Application {
	Scene scene;
	private Stage primaryStage;
	private static ClientDataEngine client;


	@Override
	public void start(Stage primaryStage) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setMinWidth(380);
		grid.setMinHeight(400);
		grid.setHgap(10);
		grid.setVgap(5);
		grid.setPadding(new Insets(25, 25, 25, 25));
		//grid.setGridLinesVisible(true);
		ImageView logo = new ImageView(new Image("http://img1.wikia.nocookie.net/__cb20121230054000/logopedia/images/6/66/NASA_logo.svg.png", 100, 100, true, true));

		Text scenetitle = new Text("Connexion");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Label userName = new Label("Identifiant:");
		TextField userTextField = new TextField();

		Label password = new Label("Mot de passe:");
		PasswordField userPassField = new PasswordField();

		Label serverName = new Label("Adresse du serveur:");
		TextField serverTextField = new TextField();

		Button connectionBtn = new Button("Connexion");
		Button registerBtn = new Button("Inscription");
		Button editBtn = new Button("Editer Profil");
		Button tableCreationBtn = new Button("Créer une Table");

		HBox hbBtn = new HBox();
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(connectionBtn);

		HBox hbBtn2 = new HBox();
		hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn2.getChildren().add(registerBtn);

		HBox hbLogo = new HBox();
		hbLogo.setAlignment(Pos.CENTER);
		hbLogo.getChildren().add(logo);

		HBox hbBtn3 = new HBox();
		hbBtn3.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn3.getChildren().add(editBtn);

		HBox hbBtn4 = new HBox();
		hbBtn4.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn4.getChildren().add(tableCreationBtn);

		grid.add(hbLogo, 1, 0);
		grid.add(scenetitle, 0, 0);
		grid.add(userName, 0, 1);
		grid.add(userTextField, 1, 1);
		grid.add(password, 0, 2);
		grid.add(userPassField, 1, 2);
		grid.add(serverName, 0, 3);
		grid.add(serverTextField, 1, 3);
		grid.add(hbBtn, 1, 4);
		grid.add(hbBtn2, 1, 5);
		grid.add(hbBtn3, 1, 6);
		grid.add(hbBtn4, 1, 8);

		scene = new Scene(grid, 380, 400);
		primaryStage.setScene(scene);
	    primaryStage.setResizable(false);

	    connectionBtn.setOnAction(e -> {
			try {
				connectionHandler(e);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	    registerBtn.setOnAction(e -> registerHandler(e));
	    editBtn.setOnAction(e -> editHandler(e));

	    primaryStage.show();

	}

	private void openMain(){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./view/ihmmain.fxml"));
			BorderPane root;
			root = (BorderPane) fxmlLoader.load();
			Scene new_scene = new Scene(root, 780, 500);
			Stage stage = new Stage();
			stage.setTitle("Main");
			stage.setScene(new_scene);
			stage.show();
			Stage this_window = (Stage)scene.getWindow();
			this_window.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void connectionHandler(ActionEvent e) throws InterruptedException{
			Waiting t = new Waiting(((Node) e.getSource()).getScene().getWindow());
			DataConnection data = new DataConnection(t);
			data.start();
			t.showAndWait();
			openMain();
	}
	
	private void openRegister(){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./view/registerWindow.fxml"));
			AnchorPane root;
			root = (AnchorPane) fxmlLoader.load();
			Scene new_scene = new Scene(root, 400, 500);
			Stage stage = new Stage();
			stage.setTitle("Register");
			stage.setScene(new_scene);
			stage.show();
			Stage this_window = (Stage)scene.getWindow();
			this_window.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void registerHandler(ActionEvent e){
		//Alert alert = new Alert(AlertType.INFORMATION, "Signal sent to data, preparing next window");
		//alert.showAndWait();
		openRegister();
	}

	private boolean editHandler(ActionEvent e){
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
		    } catch (IOException er) {
		        er.printStackTrace();
		        return false;
		    }
	}


	private class DataConnection extends Thread{
		Waiting waitingWindow;

		public void run(){
			try {
				for(int i=0;i<2;i++){
					Thread.sleep(1000);
					System.out.println("-");
				}
				waitingWindow.close();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public DataConnection(Waiting t){
			waitingWindow = t;
		}
	}

	public static void main(String[] args) {
		//launch(args);
		client = new ClientDataEngine();
		
		client.createProfile("mathieu1", "motdepasse1");
		
		client.createProfile("mathieu2", "motdepasse2");
		
		try {
			client.login("mathieu1", "motdepasse1", new IPData("localhost"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.logout();
	}

	public boolean EditProfile(User user){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/GestionProfil.fxml"));
			AnchorPane page= (AnchorPane) loader.load();

			// Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Person");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	     // Set the person into the controller.
	        PersonController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setPerson(user);

	     // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}

}
