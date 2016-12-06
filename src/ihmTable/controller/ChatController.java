package ihmTable.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import data.Chat;
import data.ChatMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class ChatController {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    @FXML
    private AnchorPane chatView;

    @FXML
    private ListView<ChatMessage> listMessages;

    @FXML
    private TextArea messageArea;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<?> listUsers;

    private Chat localChat;

    public void initialize() {
    	initListMessagesCellFactory();
    	sendButton.setOnAction(event -> onSendButtonClick());
    }

	public void setLocalChat(Chat localChat) {
		this.localChat = localChat;
		bindListMessages();
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
                        	String sender = chatMessage.getSender().getPublicData().getNickName();
                        	String content = chatMessage.getContent();
                        	String date = DATE_FORMAT.format(chatMessage.getDate());
                            setText(sender + "\n" + date + "\n" + content);
                        }
                    }
                };
                return cell;
            }
        });
	}

	private void bindListMessages() {
		listMessages.setItems(localChat.getMessageList());
	}

	private void onSendButtonClick() {

	}
}