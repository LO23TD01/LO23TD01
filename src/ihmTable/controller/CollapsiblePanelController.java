package ihmTable.controller;

import java.io.IOException;

import ihmTable.util.Utility;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class CollapsiblePanelController {

	@FXML
	private BorderPane collapsiblePanel;

	private Button collapseButton;
	private boolean opened;
	private Position position;
	private Pane content;
	private ReadOnlyDoubleProperty width;
	private ReadOnlyDoubleProperty height;
	private double collapsedSize;

	public void initialize() {
		this.opened = true;
	}

	public void setContent(Pane content, Position position, Pane parent) throws IOException {
		this.position = position;
		this.content = content;
		this.collapsiblePanel.setCenter(content);
		this.collapseButton = getCollapseButton();
		BorderPane.setAlignment(collapseButton, Pos.CENTER);
		this.width = parent.widthProperty();
		this.height = parent.heightProperty();
		this.collapsedSize = this.collapseButton.getPrefWidth();
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
				Utility.bindPrefProperties(collapsiblePanel, width.multiply(TableController.PANELS_PERCENTAGE), height);
			} else {
				Utility.bindPrefProperties(collapsiblePanel, width, height.multiply(TableController.PANELS_PERCENTAGE));
			}
		} else {
			collapsiblePanel.prefWidthProperty().unbind();
			collapsiblePanel.prefHeightProperty().unbind();
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
		top, bottom, left, right, center
	}
}
