package ihmTable.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class CollapsiblePanelController {

	@FXML
	private BorderPane collapsiblePanel;

	private Button collapseButton;
	private boolean opened;
	private Position position;
	private AnchorPane content;
	private double defaultSize;
	private double collapsedSize;

	public void initialize() {
		this.opened = true;
	}

	public void setCollapsiblePanel(AnchorPane content, Position position, double defaultSize) throws IOException {
		this.position = position;
		this.content = content;
		this.collapsiblePanel.setCenter(content);
		this.collapseButton = getCollapseButton();
		BorderPane.setAlignment(collapseButton, Pos.CENTER);
		this.defaultSize = defaultSize;
		this.collapsedSize = this.collapseButton.getPrefHeight();
		setCollapsiblePanelSize();
		setCollapseButtonPosition();
	}

	private void setCollapseButtonPosition() {
		switch (this.position) {
		case top:
			this.collapsiblePanel.setBottom(collapseButton);
			break;
		case bottom:
			this.collapsiblePanel.setTop(collapseButton);
			break;
		case right:
			this.collapsiblePanel.setLeft(collapseButton);
			break;
		default:
			this.collapsiblePanel.setRight(collapseButton);
			break;
		}
	}

	private void setCollapsiblePanelSize() {
		if(opened) {
			if(position == Position.left || position == Position.right) {
				collapsiblePanel.setPrefWidth(defaultSize);
			} else {
				collapsiblePanel.setPrefHeight(defaultSize);
			}
		} else {
			if(position == Position.left || position == Position.right) {
				collapsiblePanel.setPrefWidth(collapsedSize);
			} else {
				collapsiblePanel.setPrefHeight(collapsedSize);
			}
		}
	}

	private Button getCollapseButton() throws IOException {
		FXMLLoader buttonLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/CollapseButton.fxml"));
		Button button = buttonLoader.load();

		CollapseButtonController collapseButtonController = (CollapseButtonController) buttonLoader.getController();
		collapseButtonController.setCollapseButton(this);
		return button;
	}

	public void collapse() {
		if(opened) {
			close();
		} else {
			open();
		}
	}

	private void close() {
		this.collapsiblePanel.setCenter(null);
		this.opened = false;
		setCollapsiblePanelSize();
	}

	private void open() {
		this.collapsiblePanel.setCenter(this.content);
		this.opened = true;
		setCollapsiblePanelSize();
	}

	public Position getPosition() {
		return this.position;
	}

	public enum Position {
		top, bottom, left, right
	}
}
