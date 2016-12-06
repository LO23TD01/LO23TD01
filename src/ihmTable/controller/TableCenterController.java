package ihmTable.controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class TableCenterController {

    @FXML
    private AnchorPane tableCenterView;

    @FXML
    private GridPane tableGrid;

    @FXML
    private AnchorPane centerAnchor;

	private Ellipse tableEllipse;
	private Pane diceLauncher;
	private ArrayList<PlayerController> playerControllers;

	public void initialize() throws IOException {
		tableEllipse = new Ellipse();
		tableEllipse.setStrokeWidth(3);
		tableEllipse.setStroke(Color.BLACK);
		tableEllipse.setFill(Color.CADETBLUE);

		playerControllers = new ArrayList<PlayerController>();
		FXMLLoader diceLaucherLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/DiceLauncher.fxml"));
		diceLauncher = diceLaucherLoader.load();
		AnchorPane.setBottomAnchor(diceLauncher, 0.0);
		centerAnchor.getChildren().addAll(tableEllipse, diceLauncher);
		centerAnchor.heightProperty().addListener(event -> setEllipse());
		centerAnchor.widthProperty().addListener(event -> setEllipse());

		addPlayer();
		addPlayer();
		addPlayer();
		addPlayer();
		addPlayer();
		addPlayer();
		addPlayer();
		addPlayer();
	}

	private void setEllipse() {
		tableEllipse.setCenterX(centerAnchor.getWidth() / 2);
		tableEllipse.setCenterY(centerAnchor.getHeight() / 2);
		tableEllipse.setRadiusX(tableCenterView.getWidth() / 6);
		tableEllipse.setRadiusY(tableCenterView.getHeight() / 6);
		AnchorPane.setLeftAnchor(diceLauncher, (centerAnchor.getWidth() / 2) - (diceLauncher.getWidth() / 2));
	}

	private void addPlayer() throws IOException {
		int playersCount = playerControllers.size() + 1;
		if(playersCount < 9) {
			FXMLLoader playerLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Player.fxml"));
			AnchorPane player = playerLoader.load();
			playerControllers.add(playerLoader.getController());
			switch(playersCount) {
			case 1:
				GridPane.setValignment(player, VPos.TOP);
				GridPane.setHalignment(player, HPos.CENTER);
				tableGrid.add(player, 1, 0);
				break;
			case 2:
				GridPane.setHalignment(player, HPos.CENTER);
				GridPane.setValignment(player, VPos.BOTTOM);
				tableGrid.add(player, 1, 2);
				break;
			case 3:
				tableGrid.add(player, 0, 1);
				break;
			case 4:
				tableGrid.add(player, 2, 1);
				break;
			case 5:
				GridPane.setHalignment(player, HPos.RIGHT);
				GridPane.setValignment(player, VPos.BOTTOM);
				tableGrid.add(player, 0, 0);
				break;
			case 6:
				GridPane.setHalignment(player, HPos.LEFT);
				GridPane.setValignment(player, VPos.TOP);
				tableGrid.add(player, 2, 2);
				break;
			case 7:
				GridPane.setHalignment(player, HPos.LEFT);
				GridPane.setValignment(player, VPos.BOTTOM);
				tableGrid.add(player, 2, 0);
				break;
			case 8:
				GridPane.setHalignment(player, HPos.RIGHT);
				GridPane.setValignment(player, VPos.TOP);
				tableGrid.add(player, 0, 2);
				break;
			}
		}
	}

}
