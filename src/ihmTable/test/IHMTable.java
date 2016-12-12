package ihmTable.test;

import java.io.IOException;
import java.util.ArrayList;

import data.Profile;
import data.Rules;
import data.User;
import data.Variant;
import ihmTable.model.GameTableInstance;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class IHMTable extends Application {

	private GameTableInstance gameTableInstance;
	private FXMLLoader fxmlLoader;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
//		fxmlLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Table.fxml"));
//		Pane root = (Pane) fxmlLoader.load();
		Button root = new Button("Launch table");
		root.setOnAction(event -> {
			try {
				onLaunchButtonClick();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Scene scene = new Scene(root);
		stage.setTitle("Table");
		stage.setScene(scene);
		stage.setMaximized(false);
		stage.show();

//		if(gameTableInstance != null) {
//			TableController controller;
//			controller = (TableController)fxmlLoader.getController();
//			controller.gameTableInstance = this.gameTableInstance.getGameTableInstance();
//		}
	}

	private void onLaunchButtonClick() throws IOException {
		String nom = "Test table";
		User creator = new User(new Profile());
		data.Parameters parameters = new data.Parameters(3, 3, 21, false, false, new Rules(Variant.CONSTRAINED_DISCHARGE, 3));
		User p2 = new User(new Profile());
		User p3 = new User(new Profile());
		ArrayList<User> ps = new ArrayList<>();
		ps.add(creator);
		ps.add(p2);
		ps.add(p3);
		gameTableInstance = new GameTableInstance(nom, creator, parameters, ps, ps);
	}

//	public void InitializeModel(GameTable gameTableInstance) {
//		this.gameTableInstance = new GameTableInstance(gameTableInstance);
//		TableController controller;
//		controller = (TableController)fxmlLoader.getController();
//		controller.gameTableInstance = this.gameTableInstance.getGameTableInstance();
//	}
	//todo fair avec les autres paramètres
}