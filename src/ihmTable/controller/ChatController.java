package ihmTable.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import data.Chat;
import data.ChatMessage;
import data.GameTable;
import data.User;
import data.client.InterImplDataTable;
import ihmTable.util.Utility;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * Controller class which manages Chat view
 */
public class ChatController {

    /**
     * Tooltip message displayed when messageArea and sendButton are disabled
     *
     * @see ChatController#messageArea
     * @see ChatController#sendButton
     */
    private static final String MESSAGE_CONTROL_CONTAINER_TOOLTIP = "Le chat n'est pas autorisé aux spectateurs";

	/**
	 * Date format used to display dates
	 */
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    /**
     * General chat view
     */
    @FXML
    private BorderPane chatView;

    /**
     * List view displaying all messages
     *
     * @see ListView
     */
    @FXML
    private ListView<ChatMessage> listMessages;

    /**
     * Input field for messages
     */
    @FXML
    private TextArea messageArea;

    /**
     * Button to send messages
     */
    @FXML
    private Button sendButton;

    /**
     * List view displaying all users
     *
     * @see ListView
     */
    @FXML
    private ListView<User> listUsers;

    /**
     * Container of messageArea and sendButton
     *
     * @see ChatController#messageArea
     * @see ChatController#sendButton
     */
    @FXML
    private HBox messageControlContainer;

    /**
     * Left container of chatView including listMessages and messageControlContainer
     *
     * @see ChatController#chatView
     * @see ChatController#listMessages
     * @see ChatController#messageControlContainer
     */
    @FXML
    private BorderPane chatLeftContainer;

    /**
     * Right container of chatView including listUsers
     *
     * @see ChatController#chatView
     * @see ChatController#listUsers
     */
    @FXML
    private AnchorPane chatRightContainer;

    /**
     * Interface with Data
     */
    private InterImplDataTable interImplDataTable;
    /**
     * Chat object of Data used to populate listMessages
     *
     * @see Chat
     * @see ChatController#listMessages
     */
    private Chat localChat;
    /**
     * Local user
     *
     * @see User
     */
    private User user;
    /**
     * ObservableList containing all users connected to the table including players and spectators
     *
     * @see ObservableList
     * @see ChatController#players
     * @see ChatController#spectators
     */
    private ObservableList<User> users;
    /**
     * ObservableList containing all players of the table
     *
     * @see ObservableList
     */
    private ObservableList<User> players;
    /**
     * ObservableList containing all spectators of the table
     *
     * @see ObservableList
     */
    private ObservableList<User> spectators;
    /**
     * Controller of playerStats view
     *
     * @see PlayerStatsController
     */
    private PlayerStatsController playerStatsController;

    /**
     * Initialize the controller
     */
    public void initialize() {
    	initListMessagesCellFactory();
    	initListUsersCellFactory();
    	setPrefProperties();
    	sendButton.setOnAction(event -> onSendButtonClick());
    }

    /**
     * Set data to the controller
     * @param interImplDataTable the Data's interface
     * @param user the local user
     * @param playerStatsController the controller of playerStats' view
     */
    public void setData(InterImplDataTable interImplDataTable, User user, PlayerStatsController playerStatsController) {
		this.interImplDataTable = interImplDataTable;
		GameTable gameTable = interImplDataTable.getActualTable();
		this.localChat = gameTable.getLocalChat();
		this.user = user;
		this.playerStatsController = playerStatsController;
		this.users = FXCollections.observableArrayList();
		this.players = gameTable.getPlayerList();
		this.spectators = gameTable.getSpectatorList();
		this.users.addAll(players);
		this.users.addAll(spectators);
		this.listUsers.setItems(users);
		if(user.getSame(localChat.getVoicedUserList()) == null) {
			this.messageArea.setDisable(true);
			this.sendButton.setDisable(true);
			Tooltip.install(this.messageControlContainer, new Tooltip(MESSAGE_CONTROL_CONTAINER_TOOLTIP));
		}
		bindListMessages();
		bindListUsers();
	}

	/**
	 * Customize cells of listMessages
	 *
	 * @see ChatController#listMessages
	 */
	private void initListMessagesCellFactory() {
		listMessages.setCellFactory(new Callback<ListView<ChatMessage>, ListCell<ChatMessage>>(){
            @Override
            public ListCell<ChatMessage> call(ListView<ChatMessage> p) {
                ListCell<ChatMessage> cell = new ListCell<ChatMessage>(){
                    @Override
                    protected void updateItem(ChatMessage chatMessage, boolean bln) {
                    	super.updateItem(chatMessage, bln);
                    	Platform.runLater(new Runnable() {
                			@Override
                			public void run() {
                            	if (chatMessage != null) {
                                    VBox messageCell = getMessageCell(chatMessage);
                                    setGraphic(messageCell);
                                } else {
                                    setGraphic(null);
                                }
                			}
                		});
                    }
                };
                return cell;
            }
        });
	}

	/**
	 * Provide a customized view for a given chatMessage
	 * @param chatMessage the chat message for which the view is need
	 * @return the view of the given ChatMessage
	 *
	 * @see ChatMessage
	 */
	private VBox getMessageCell(ChatMessage chatMessage) {
		VBox vbox = new VBox();
		HBox hbox = new HBox();
		Text sender = new Text(chatMessage.getSender().getPublicData().getNickName());
		sender.setFont(Font.font(null, FontWeight.BOLD, sender.getFont().getSize()));
		Text date = new Text(DATE_FORMAT.format(chatMessage.getDate()));
		date.setFill(Color.GRAY);
		Text content = new Text(chatMessage.getContent());
		Pane space = new Pane();
		HBox.setHgrow(space, Priority.ALWAYS);
		hbox.getChildren().add(sender);
		hbox.getChildren().add(space);
		hbox.getChildren().add(date);
		vbox.getChildren().add(hbox);
		vbox.getChildren().add(content);
		return vbox;
	}

	/**
	 * Customize cells of listUsers
	 *
	 * @see ChatController#listUsers
	 */
	private void initListUsersCellFactory() {
		listUsers.setCellFactory((p) -> {
            return new ListCell<User>(){
                @Override
                protected void updateItem(User user, boolean bln) {
                    super.updateItem(user, bln);
                    Platform.runLater(new Runnable() {
                    	@Override
                    	public void run() {
                    		if (user != null) {
                    			if(user.getSame(spectators) != null) {
                    				setText(user.getPublicData().getNickName() + " (spec)");
                    			} else {
                    				setText(user.getPublicData().getNickName());
                    			}

                    		} else {
                    			setText(null);
                    		}
                    	}
                    });
                }
            };
        });
	}

	/**
	 * Bind listUsers to users in order to update it whenever there is any changes in players and spectators
	 *
	 *  @see ChatController#listUsers
	 *  @see ChatController#users
	 *  @see ChatController#players
	 *  @see ChatController#spectators
	 */
	private void bindListUsers() {
		//If there is any changes on players or spectators lists, update users list
		ListChangeListener<User> usersChangeListener;
		usersChangeListener = change -> {
			while(change.next()) {
				if (change.wasAdded()) {
					this.users.addAll(change.getAddedSubList());
				} else if(change.wasRemoved()) {
					this.users.removeAll(change.getRemoved());
				}
			}
		};
		players.addListener(usersChangeListener);
		spectators.addListener(usersChangeListener);
		listUsers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> playerStatsController.setUser(newValue));
	}

	/**
	 * Bind listMessages to localChat messages
	 *
	 * @see ChatController#listMessages
	 * @see ChatController#localChat
	 */
	private void bindListMessages() {
		listMessages.setItems(localChat.getMessageList());
	}

	/**
	 * Perform action when sendButton is clicked
	 *
	 * @see ChatController#sendButton
	 */
	private void onSendButtonClick() {
		interImplDataTable.sendMessage(new ChatMessage(user, messageArea.getText()));
		messageArea.clear();
	}

	/**
	 * Set pref properties (width and height) in order to resize the different elements when the view is resized
	 */
	private void setPrefProperties() {
		Utility.bindPrefProperties(chatLeftContainer, chatView.widthProperty().multiply(0.7), chatView.heightProperty().subtract(messageControlContainer.heightProperty()));
		Utility.bindPrefProperties(chatRightContainer, chatView.widthProperty().multiply(0.3), chatView.heightProperty());
		messageArea.prefWidthProperty().bind(chatLeftContainer.widthProperty().subtract(sendButton.prefWidthProperty()));
	}
}