package ihmTable.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import data.Chat;
import data.ChatMessage;
import data.GameTable;
import data.User;
import data.client.InterImplDataTable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ChatController {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    @FXML
    private AnchorPane chatView;

    @FXML
    private ListView<ChatMessage> listMessages;

    @FXML
    private TextArea messageArea;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<User> listUsers;

    private InterImplDataTable interImplDataTable;
    private Chat localChat;
    private User user;
    private ObservableList<User> users;
    private ObservableList<User> players;
    private ObservableList<User> spectators;

    public void initialize() {
    	initListMessagesCellFactory();
    	initListUsersCellFactory();
    	sendButton.setOnAction(event -> onSendButtonClick());
    }

    public void setData(InterImplDataTable interImplDataTable, User user) {
		this.interImplDataTable = interImplDataTable;
		GameTable gameTable = interImplDataTable.getActualTable();
		this.localChat = gameTable.getLocalChat();
		this.user = user;
		this.users = FXCollections.observableArrayList();
		this.players = gameTable.getPlayerList();
		this.spectators = gameTable.getSpectatorList();
		this.users.addAll(players);
		this.users.addAll(spectators);
		bindListMessages();
		bindListUsers();
	}

	private void initListMessagesCellFactory() {
		listMessages.setCellFactory(new Callback<ListView<ChatMessage>, ListCell<ChatMessage>>(){
            @Override
            public ListCell<ChatMessage> call(ListView<ChatMessage> p) {
                ListCell<ChatMessage> cell = new ListCell<ChatMessage>(){
                    @Override
                    protected void updateItem(ChatMessage chatMessage, boolean bln) {
                        super.updateItem(chatMessage, bln);
                    	if (chatMessage != null) {
                            VBox messageCell = getMessageCell(chatMessage);
                            setGraphic(messageCell);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        });
	}

	//return a custom cell for chatMessage
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

	private void initListUsersCellFactory() {
		listUsers.setCellFactory(new Callback<ListView<User>, ListCell<User>>(){
            @Override
            public ListCell<User> call(ListView<User> p) {
                ListCell<User> cell = new ListCell<User>(){
                    @Override
                    protected void updateItem(User user, boolean bln) {
                        super.updateItem(user, bln);
                        if (user != null) {
                            setText(user.getPublicData().getNickName());
                        }
                    }
                };
                return cell;
            }
        });
	}

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
		listUsers.setItems(users);
	}

	private void bindListMessages() {
		listMessages.setItems(localChat.getMessageList());
	}

	private void onSendButtonClick() {
		interImplDataTable.sendMessage(new ChatMessage(user, messageArea.getText()));
		messageArea.clear();
	}
}