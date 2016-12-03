package ihmTable;

import java.io.IOException;

import data.GameTable;
import ihmTable.controller.GameTableInstance;
import ihmTable.controller.TableController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class IHMTable extends Application {

	private GameTableInstance gameTableInstance;
	private FXMLLoader fxmlLoader;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		fxmlLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Table.fxml"));
		Pane root = (Pane) fxmlLoader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Table");
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();

		if(gameTableInstance != null)
		{
			TableController controller;
			controller = (TableController)fxmlLoader.getController();
			controller.gameTableInstance = this.gameTableInstance.getGameTableInstance();
		}
	}

	public void InitializeModel(GameTable gameTableInstance)
	{
		this.gameTableInstance = new GameTableInstance(gameTableInstance);
		TableController controller;
		controller = (TableController)fxmlLoader.getController();
		controller.gameTableInstance = this.gameTableInstance.getGameTableInstance();
	}
	//todo fair avec les autres paramètres
}