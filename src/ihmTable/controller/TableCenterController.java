package ihmTable.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import data.GameTable;
import data.User;
import data.client.InterImplDataTable;
import javafx.application.Platform;
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
    private InterImplDataTable interImplDataTable;
    private DiceLauncherController diceLaunchController;

	private Ellipse tableEllipse;
	private Pane diceLauncher;
	private HashMap<UUID, AnchorPane> playerViews;

	private ObservableList<User> players;

	public void initialize() throws IOException {
		this.tableEllipse = new Ellipse();
		this.tableEllipse.setStrokeWidth(3);
		this.tableEllipse.setStroke(Color.BLACK);
		this.tableEllipse.setFill(Color.CADETBLUE);

		this.playerViews = new HashMap<UUID, AnchorPane>();

		FXMLLoader diceLauncherLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/DiceLauncher.fxml"));
		this.diceLauncher = diceLauncherLoader.load();
		this.diceLaunchController = (DiceLauncherController) diceLauncherLoader.getController();

		AnchorPane.setBottomAnchor(this.diceLauncher, 0.0);
		this.centerAnchor.getChildren().addAll(this.tableEllipse, this.diceLauncher);
		this.centerAnchor.heightProperty().addListener(event -> setEllipse());
		this.centerAnchor.widthProperty().addListener(event -> setEllipse());
	}

	public void setData(InterImplDataTable interImplDataTable, User user) throws IOException {
		this.interImplDataTable = interImplDataTable;
		this.gameTableInstance = interImplDataTable.getActualTable();
		this.diceLaunchController.setData(this.interImplDataTable, user);
		initPlayers();
	}

	private void initPlayers() throws IOException {
		this.players = this.gameTableInstance.getPlayerList();
		for(User player : this.players) {
			addPlayer(player);
		}
		ListChangeListener<User> playersChangeListener;
		playersChangeListener = change -> {
			while(change.next()) {
				if (change.wasAdded()) {
					for (User addedUser : change.getAddedSubList()) {
						try {
							addPlayer(addedUser);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				if(change.wasRemoved()) {
					for(User removedUser : change.getRemoved()) {
						this.tableGrid.getChildren().remove(this.playerViews.get(removedUser.getPublicData().getUUID()));
					}
				}
			}
		};
		players.addListener(playersChangeListener);
	}

	private void setEllipse() {
		this.tableEllipse.setCenterX(this.centerAnchor.getWidth() / 2);
		this.tableEllipse.setCenterY(this.centerAnchor.getHeight() / 2);
		this.tableEllipse.setRadiusX(this.tableCenterView.getWidth() / 6);
		this.tableEllipse.setRadiusY(this.tableCenterView.getHeight() / 6);
		AnchorPane.setLeftAnchor(this.diceLauncher, (this.centerAnchor.getWidth() / 2) - (this.diceLauncher.getWidth() / 2));
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
				addToTableGrid(player, 1, 0, VPos.TOP, HPos.CENTER);
				break;
			case 2:
				addToTableGrid(player, 1, 2, VPos.BOTTOM, HPos.CENTER);
				break;
			case 3:
				addToTableGrid(player, 0, 1, VPos.CENTER, HPos.CENTER);
				break;
			case 4:
				addToTableGrid(player, 2, 1, VPos.CENTER, HPos.CENTER);
				break;
			case 5:
				addToTableGrid(player, 0, 0, VPos.BOTTOM, HPos.RIGHT);
				break;
			case 6:
				addToTableGrid(player, 2, 2, VPos.TOP, HPos.LEFT);
				break;
			case 7:
				addToTableGrid(player, 2, 0, VPos.BOTTOM, HPos.LEFT);
				break;
			case 8:
				addToTableGrid(player, 0, 2, VPos.TOP, HPos.RIGHT);
				break;
			}
		}
	}

	private void addToTableGrid(AnchorPane player, int columnIndex, int rowIndex, VPos vPos, HPos hPos) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	GridPane.setHalignment(player, hPos);
				GridPane.setValignment(player, vPos);
		    	tableGrid.add(player, columnIndex, rowIndex);
		    }
		});
	}

}
