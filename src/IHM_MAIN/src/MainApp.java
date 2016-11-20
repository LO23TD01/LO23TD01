package IHM_MAIN.src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./view/ihmmain.fxml"));
	BorderPane root = (BorderPane) fxmlLoader.load();
	Scene scene = new Scene(root, 780, 500);
	stage.setTitle("Main");
	stage.setScene(scene);
	stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
