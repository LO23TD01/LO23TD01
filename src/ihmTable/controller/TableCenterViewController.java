package ihmTable.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class TableCenterViewController {

	@FXML
	private AnchorPane tableCenterView;

	@FXML
	private AnchorPane player5;

	@FXML
	private AnchorPane player1;

	@FXML
	private AnchorPane player8;

	@FXML
	private AnchorPane player3;

	@FXML
	private AnchorPane centerAnchor;

	@FXML
	private AnchorPane player4;

	@FXML
	private AnchorPane player7;

	@FXML
	private AnchorPane player2;

	@FXML
	private AnchorPane player6;

	private Ellipse tableEllipse;
	private Pane diceLauncher;

	public void initialize() throws IOException {
		tableEllipse = new Ellipse();
		tableEllipse.setStrokeWidth(3);
		tableEllipse.setStroke(Color.BLACK);
		tableEllipse.setFill(Color.CADETBLUE);
		FXMLLoader diceLaucherLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/DiceLauncher.fxml"));
		diceLauncher = diceLaucherLoader.load();
		AnchorPane.setBottomAnchor(diceLauncher, 0.0);
		centerAnchor.getChildren().addAll(tableEllipse, diceLauncher);
		centerAnchor.heightProperty().addListener(event -> setEllipse());
		centerAnchor.widthProperty().addListener(event -> setEllipse());
	}

	private void setEllipse() {
		tableEllipse.setCenterX(centerAnchor.getWidth() / 2);
		tableEllipse.setCenterY(centerAnchor.getHeight() / 2);
		tableEllipse.setRadiusX(tableCenterView.getWidth() / 6);
		tableEllipse.setRadiusY(tableCenterView.getHeight() / 6);
		AnchorPane.setLeftAnchor(diceLauncher, (centerAnchor.getWidth() / 2) - (diceLauncher.getWidth() / 2));
	}

}
