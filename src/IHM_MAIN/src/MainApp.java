package IHM_MAIN.src;

import data.IPData;
import data.Profile;
import data.User;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import data.client.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

	InterImplDataMain interImplDataMain;

	TextField userTextField;
	PasswordField userPassField;
	TextField serverTextField;

	/**
	*	Handles the main window display.
	*	<br>This overriden method will construct the login window, and display it.
	*/
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
		
		connectionBtn.defaultButtonProperty().bind(connectionBtn.focusedProperty());
		registerBtn.defaultButtonProperty().bind(registerBtn.focusedProperty());

		HBox hbBtn = new HBox();
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(connectionBtn);

		HBox hbBtn2 = new HBox();
		hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn2.getChildren().add(registerBtn);

		HBox hbLogo = new HBox();
		hbLogo.setAlignment(Pos.CENTER);
		hbLogo.getChildren().add(logo);

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

		userTextField.setText("tmp");
		userPassField.setText("tmp");
		serverTextField.setText("localhost");

		primaryStage.show();
	}

	/**
	*	This method opens the main window once the user connected.
	*	<br>It will construct the window and display it to the user.
	*/
	private void openMain(){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./view/mainWindow.fxml"));
			BorderPane root;
			root = (BorderPane) fxmlLoader.load();

			ControllerApplication controller = (ControllerApplication) fxmlLoader.getController();
			controller.setInterfaceData(this.interImplDataMain);
			controller.init();
			Scene new_scene = new Scene(root, 915, 500);
			Stage stage = new Stage();
			stage.setTitle("Main");
			stage.setScene(new_scene);
			stage.show();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			  public void handle(WindowEvent we) {
				 interImplDataMain.logout();
			  }
			});
			Stage this_window = (Stage)scene.getWindow();
			this_window.close();

		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR, "Une erreur inconnue est arrivé. Merci de réessayer.");
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	/**
	*	Handles the user connection to the server.
	*	<br>It will et the user info, pass it to a DataConnection thread and wait for the answer.
	*/	
	private void connectionHandler(ActionEvent e) throws InterruptedException{
		WaitingWindow t = new WaitingWindow(((Node) e.getSource()).getScene().getWindow());
		DataConnection dataConnect = new DataConnection(t);
		dataConnect.setInfo(userTextField.getText(), userPassField.getText(), new IPData(serverTextField.getText()));
		dataConnect.setInterfaceData(this.interImplDataMain);
		dataConnect.start();
		t.showAndWait();
		dataConnect.join();

		if(dataConnect.connectionLoginFlag){
			openMain();
		}else{
			Alert alert = new Alert(AlertType.ERROR, dataConnect.exceptionMessage);
			alert.showAndWait();
		}
	}

	/**
	*	Opens the register window so that the user can create a profile.
	*/			
	private void openRegister(){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./view/registerWindow.fxml"));
			AnchorPane root;
			root = (AnchorPane) fxmlLoader.load();

			RegisterWindow controller = (RegisterWindow) fxmlLoader.getController();
			controller.setInterImplDataMain(this.interImplDataMain);

			Scene new_scene = new Scene(root, 400, 500);
			Stage stage = new Stage();
			stage.setTitle("Register");
			stage.setScene(new_scene);
			stage.setResizable(false);

			controller.setCurrentStage(stage);
			controller.setButtonToBeTriggerByEnter();

			stage.show();
			//we don't close the current window because the user will need to come back after registering

		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR, "Une erreur inconnue est arrivé. Merci de réessayer.");
			alert.showAndWait();
			e.printStackTrace();
		}
	}
	
	private void registerHandler(ActionEvent e){
		openRegister();
	}


	/**
	*	Opens the Profile Management window so the user can modify his/her info.
	*/
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
			Profile profil = interImplDataMain.getLocalProfile();

			User user= new User(profil);
			//controller.setPerson(user);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException er) {
			Alert alert = new Alert(AlertType.ERROR, "Une erreur inconnue est arrivé. Merci de réessayer.");
			alert.showAndWait();
			er.printStackTrace();
			return false;
		}
	}

	/**
	*	This thread handles the user login. It creates a thread with a given timeout.
	*	<br>It possesses an exception message for handling.
	*/
	public class DataConnection extends Thread{
		WaitingWindow waitingWindow;
		String login;
		String password;
		data.IPData ip;
		Boolean connectionLoginFlag;
		String exceptionMessage;
		InterImplDataMain interImplDataMain;

		public void setInfo(String login2, String password2, data.IPData ip2){
			this.login = login2;
			this.password = password2;
			this.ip = ip2;
			this.connectionLoginFlag = false;

		}

		public void setInterfaceData(InterImplDataMain client){
			this.interImplDataMain = client;
		}

		public void run(){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				this.exceptionMessage= "Connexion Réseau Impossible";
				e.printStackTrace();
			}
			if(this.interImplDataMain.login(login, password, ip)){
				this.connectionLoginFlag = true;
			} else {
				this.exceptionMessage= "Connexion Réseau Impossible";
			}
			this.waitingWindow.close();
		}

		public DataConnection(WaitingWindow t){
			this.waitingWindow = t;
		}
	}

	/**
	*	Returns the Data API implementation given in this class.
	*	@return InterImplDataMain
	*/
	public InterImplDataMain getInterImplDataMain() {
		return this.interImplDataMain;
	}

	/**
	*	Returns the primary stage (JavaFX)
	*/
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}

	/**
	*	Initializes the app. 
	*	<br>This method will instanciate the InterImplDataMain object, containing the Data API implementation.
	*/
	public void init(){
		this.interImplDataMain = new InterImplDataMain(new InterfaceSingleThreadDataClient(new ClientDataEngine()));
		System.out.println(this.interImplDataMain);
	}

	/**
	*	Lanches the App.
	*/
	public static void main(String[] args) {
		launch(args);
	}

}
