package IHM_MAIN.src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
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

public class MainApp extends Application {
	Scene scene;
		
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
	    
	    connectionBtn.setOnAction(e -> connectionHandler(e));
	    registerBtn.setOnAction(e -> registerHandler(e));
	    editBtn.setOnAction(e -> editHandler(e));
	    tableCreationBtn.setOnAction(e -> tableCreationHandler(e));

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
	private void connectionHandler(ActionEvent e){
		Alert alert = new Alert(AlertType.INFORMATION, "Signal sent to data, preparing next window");
		alert.showAndWait();
		openMain();
	}
	
	private void registerHandler(ActionEvent e){
		Alert alert = new Alert(AlertType.INFORMATION, "Signal sent to data, preparing next window");
		alert.showAndWait();
	}
	
	private void editHandler(ActionEvent e){
		Alert alert = new Alert(AlertType.INFORMATION, "Open Edit Profile Window");
		alert.showAndWait();
	}
	
	private void tableCreationHandler(ActionEvent e){
		//Alert alert = new Alert(AlertType.INFORMATION, "Open Edit Profile Window");
		//alert.showAndWait();
		
		///////////// CREE NOUVELLE FENETRE ET BLOQUE L'ANCIENNE ///////////////////
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("./view/tableCreation.fxml"));
			Window parent = scene.getWindow();
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
	
	public static void main(String[] args) {
		launch(args);
	}
}