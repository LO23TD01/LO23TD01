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

/**
 * Class which gives the possibility to collapse and open a panel
 */
public class CollapsiblePanelController {

	/**
	 * The view of the collapsible panel
	 */
	@FXML
	private BorderPane collapsiblePanel;

	/**
	 * The button which allow to close and open the panel
	 */
	private Button collapseButton;
	/**
	 * Whether the panel is opened
	 */
	private boolean opened;
	/**
	 * Position of the panel
	 *
	 * @see Position
	 */
	private Position position;
	/**
	 * The content of the panel
	 */
	private Pane content;
	/**
	 * The width of panel when opened
	 */
	private ReadOnlyDoubleProperty width;
	/**
	 * The height of the panel when opened
	 */
	private ReadOnlyDoubleProperty height;
	/**
	 * The size of the panel when collapsed
	 */
	private double collapsedSize;

	/**
	 * Initialize the controller
	 */
	public void initialize() {
		this.opened = true;
	}

	/**
	 * Set the content of the panel, it's position and it's parent
	 * @param content the content of the panel
	 * @param position the position of the panel
	 * @param parent the parent of the panel
	 * @throws IOException
	 *
	 * @see Position
	 */
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

	/**
	 * Set the position of the collapseButton according to the position of the panel
	 *
	 * @see CollapsiblePanelController#collapseButton
	 */
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

	/**
	 * Set the size of the panel
	 */
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

	/**
	 * Return a view for collapseButton
	 * @return the button view
	 * @throws IOException
	 */
	private Button getCollapseButton() throws IOException {
		FXMLLoader buttonLoader = new FXMLLoader(getClass().getResource("/ihmTable/resources/view/CollapseButton.fxml"));
		Button button = buttonLoader.load();
		CollapseButtonController collapseButtonController = (CollapseButtonController) buttonLoader.getController();
		collapseButtonController.setCollapseButton(this);
		return button;
	}

	/**
	 * Open or close the panel
	 */
	public void collapse() {
		if(opened) {
			close();
		} else {
			open();
		}
	}

	/**
	 * Close the panel
	 */
	private void close() {
		this.collapsiblePanel.setCenter(null);
		this.opened = false;
		setCollapsiblePanelSize();
	}

	/**
	 * Open the panel
	 */
	private void open() {
		this.collapsiblePanel.setCenter(this.content);
		this.opened = true;
		setCollapsiblePanelSize();
	}

	/**
	 * Return the position of the panel
	 * @return the position
	 *
	 * @see Position
	 */
	public Position getPosition() {
		return this.position;
	}

	/**
	 * enum which gives the different positions of a collapsible panel
	 */
	public enum Position {
		top, bottom, left, right, center
	}
}
