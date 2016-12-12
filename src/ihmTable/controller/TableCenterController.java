package ihmTable.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import data.GameTable;
import data.User;
import data.client.InterImplDataTable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

    private GameTable gameTableInstance;
    private User user;
    private InterImplDataTable interImplDataTable;
    private DiceLauncherController diceLaunchController;

	private Ellipse tableEllipse;
	private Pane diceLauncher;
	private HashMap<UUID, AnchorPane> playerViews;
	
	private ObservableList<User> players;

	public void initialize() throws IOException {
		tableEllipse = new Ellipse();
		tableEllipse.setStrokeWidth(3);
		tableEllipse.setStroke(Color.BLACK);
		tableEllipse.setFill(Color.CADETBLUE);

		playerViews = new HashMap<UUID, AnchorPane>();

		FXMLLoader diceLauncherLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/DiceLauncher.fxml"));
		diceLauncher = diceLauncherLoader.load();
		this.diceLaunchController = (DiceLauncherController) diceLauncherLoader.getController();

		AnchorPane.setBottomAnchor(diceLauncher, 0.0);
		centerAnchor.getChildren().addAll(tableEllipse, diceLauncher);
		centerAnchor.heightProperty().addListener(event -> setEllipse());
		centerAnchor.widthProperty().addListener(event -> setEllipse());
	}

	public void setData(InterImplDataTable interImplDataTable, User user) throws IOException {
		this.interImplDataTable = interImplDataTable;
		this.gameTableInstance = interImplDataTable.getActualTable();
		this.user = user;
		diceLaunchController.setData(this.interImplDataTable, user);
		initPlayers();
	}
	
	private void initPlayers() throws IOException {
		this.players = gameTableInstance.getPlayerList();
		for(User player : players) {
			addPlayer(player);
		}
		ListChangeListener<User> playersChangeListener;
		playersChangeListener = change -> {
			while(change.next()) {
				if(change.wasRemoved()) {
					for(User removedUser : change.getRemoved()) {
						this.tableGrid.getChildren().remove(playerViews.get(removedUser.getPublicData().getUUID()));
					}
				}
			}
		};
		players.addListener(playersChangeListener);
	}

	private void setEllipse() {
		tableEllipse.setCenterX(centerAnchor.getWidth() / 2);
		tableEllipse.setCenterY(centerAnchor.getHeight() / 2);
		tableEllipse.setRadiusX(tableCenterView.getWidth() / 6);
		tableEllipse.setRadiusY(tableCenterView.getHeight() / 6);
		AnchorPane.setLeftAnchor(diceLauncher, (centerAnchor.getWidth() / 2) - (diceLauncher.getWidth() / 2));
	}

	private void addPlayer(User user) throws IOException {
		int playersCount = playerViews.size() + 1;
		if(playersCount < 9) {
			FXMLLoader playerLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/Player.fxml"));
			AnchorPane player = playerLoader.load();
			PlayerController playerController = playerLoader.getController();
			playerController.setData(gameTableInstance, user);
			playerViews.put(user.getPublicData().getUUID(), player);
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
