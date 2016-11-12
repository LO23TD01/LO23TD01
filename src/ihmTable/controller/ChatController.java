package ihmTable.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class ChatController {

    @FXML
    private AnchorPane chatView;

    @FXML
    private ListView<?> listMessages;

    @FXML
    private TextArea messageArea;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<?> listUsers;

}