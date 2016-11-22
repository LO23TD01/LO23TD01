package ihmTable;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class IHMTable extends Application {

	public static void launch(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Table.fxml"));
		Pane root = (Pane) fxmlLoader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Table");
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}
}