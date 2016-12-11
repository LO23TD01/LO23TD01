package IHM_MAIN.src;

import data.IPData;
import data.Profile;
import data.User;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import data.client.*;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import IHM_MAIN.src.controller.ControllerApplication;
import IHM_MAIN.src.controller.PersonController;
import IHM_MAIN.src.controller.RegisterWindow;

public class MainApp extends Application {
	Scene scene;
	private Stage primaryStage;
	
	InterfaceDataIHMLobby interfaceData;
	ClientDataEngine clientData;
	InterImplDataMain interImplDataMain;
	
	TextField userTextField;
	PasswordField userPassField;
	TextField serverTextField;
	
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
		userTextField = new TextField();

		Label password = new Label("Mot de passe:");
		userPassField = new PasswordField();

		Label serverName = new Label("Adresse du serveur:");
		serverTextField = new TextField();
		
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
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./view/mainWindow.fxml"));
			BorderPane root;
			root = (BorderPane) fxmlLoader.load();
			
			ControllerApplication controller = (ControllerApplication) fxmlLoader.getController();
			controller.setClientData(this.clientData);
			controller.setInterfaceDataIHM(this.interfaceData);

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
			WaitingWindow t = new WaitingWindow(((Node) e.getSource()).getScene().getWindow());
			DataConnection dataConnect = new DataConnection(t);
			dataConnect.setInfo(userTextField.getText(), userPassField.getText(), new IPData(serverTextField.getText()));
			dataConnect.start();
			t.showAndWait();
			dataConnect.join();
				if(dataConnect.connectionLoginFlag){
					openMain();
				}else{
					Alert alert = new Alert(AlertType.ERROR, dataConnect.exceptionMessage);
					alert.showAndWait();
					openMain();
				}
	}
	
	private void openRegister(){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./view/registerWindow.fxml"));
			AnchorPane root;
			root = (AnchorPane) fxmlLoader.load();
			
			RegisterWindow controller = (RegisterWindow) fxmlLoader.getController();
			controller.setClientData(this.clientData);
			controller.setInterfaceDataIHM(this.interfaceData);
			controller.setInterImplDataMain(this.interImplDataMain);
			
			Scene new_scene = new Scene(root, 400, 500);
			Stage stage = new Stage();
			stage.setTitle("Register");
			stage.setScene(new_scene);
			
			controller.setCurrentStage(stage);
	        
			stage.show();
			//we don't close the current window because the user will need to come back after registering

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
	
	public class DataConnection extends Thread{
		WaitingWindow waitingWindow;
		String login;
		String password;
		data.IPData ip;
		Boolean connectionLoginFlag;
		String exceptionMessage;

		public void setInfo(String login2, String password2, data.IPData ip2){
			login = login2;
			password = password2;
			ip = ip2;
			connectionLoginFlag = false;
			
		}
		
		public void run(){
			try {	
					Thread.sleep(500);
					interfaceData.login(login, password, ip);
					connectionLoginFlag = true;
				} catch (Exception e) {
					exceptionMessage = e.toString();
					e.printStackTrace();
				}
			waitingWindow.close();	
		}
		public DataConnection(WaitingWindow t){
			waitingWindow = t;
		}
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
	
	public void init(){
		this.clientData = new ClientDataEngine();
		this.interImplDataMain = new InterImplDataMain(this.clientData);
	}

	public static void main(String[] args) {
		launch(args);
	}


}
