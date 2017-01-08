package ihmTable.controller;

import ihmTable.controller.CollapsiblePanelController.Position;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.SVGPath;

/**
 * Class which manages the collapse button of a collapsible panel
 *
 * @see CollapsiblePanelController
 */
public class CollapseButtonController {

	/**
	 * SVG code for a right arrow
	 */
	private static String RIGHT_ARROW = "M8 0c-4.418 0-8 3.582-8 8s3.582 8 8 8 8-3.582 8-8-3.582-8-8-8zM8 14.5c-3.59 0-6.5-2.91-6.5-6.5s2.91-6.5 6.5-6.5 6.5 2.91 6.5 6.5-2.91 6.5-6.5 6.5zM5.543 11.043l1.414 1.414 4.457-4.457-4.457-4.457-1.414 1.414 3.043 3.043z";
	/**
	 * SVG code for a left arrow
	 */
	private static String LEFT_ARROW = "M8 16c4.418 0 8-3.582 8-8s-3.582-8-8-8-8 3.582-8 8 3.582 8 8 8zM8 1.5c3.59 0 6.5 2.91 6.5 6.5s-2.91 6.5-6.5 6.5-6.5-2.91-6.5-6.5 2.91-6.5 6.5-6.5zM10.457 4.957l-1.414-1.414-4.457 4.457 4.457 4.457 1.414-1.414-3.043-3.043z";
	/**
	 * SVG code for a up arrow
	 */
	private static String UP_ARROW = "M0 8c0 4.418 3.582 8 8 8s8-3.582 8-8-3.582-8-8-8-8 3.582-8 8zM14.5 8c0 3.59-2.91 6.5-6.5 6.5s-6.5-2.91-6.5-6.5 2.91-6.5 6.5-6.5 6.5 2.91 6.5 6.5zM11.043 10.457l1.414-1.414-4.457-4.457-4.457 4.457 1.414 1.414 3.043-3.043z";
	/**
	 * SVG code for a down arrow
	 */
	private static String DOWN_ARROW = "M16 8c0-4.418-3.582-8-8-8s-8 3.582-8 8 3.582 8 8 8 8-3.582 8-8zM1.5 8c0-3.59 2.91-6.5 6.5-6.5s6.5 2.91 6.5 6.5-2.91 6.5-6.5 6.5-6.5-2.91-6.5-6.5zM4.957 5.543l-1.414 1.414 4.457 4.457 4.457-4.457-1.414-1.414-3.043 3.043z";

    /**
     * The collapse button
     */
    @FXML
    private Button collapseButton;

    /**
     * SVGPath including in the button
     *
     * @see SVGPath
     */
    @FXML
    private SVGPath buttonSVG;

    /**
     * The collapsiblePanelController linked to this button
     *
     * @see CollapsiblePanelController
     */
    private CollapsiblePanelController collapsiblePanelController;
    /**
     * The position of the collapsiblePanelController
     *
     * @see CollapsiblePanelController
     * @see Position
     */
    private Position position;
    /**
     * Boolean which indicates if the panel is opened or not
     */
    private boolean opened;

    /**
     * Initialize the controller
     */
    public void initialize() {
    	this.opened = true;
    }

    /**
     * Set the collapsiblePanelController
     * @param collapsiblePanelController the collapsible panel linked to the button
     *
     * @see CollapsiblePanelController
     */
    public void setCollapseButton(CollapsiblePanelController collapsiblePanelController) {
    	this.collapsiblePanelController = collapsiblePanelController;
    	this.position = collapsiblePanelController.getPosition();
    	setCollapseButtonImage();
    	this.collapseButton.setOnAction(event -> collapse());
    }

    /**
     * Collpase or open the panel
     */
    private void collapse() {
    	this.collapsiblePanelController.collapse();
    	this.opened = !this.opened;
    	setCollapseButtonImage();
    }

    /**
     * Set the image of the button
     */
    private void setCollapseButtonImage() {
		switch (this.position) {
		case top:
			if(opened) {
				buttonSVG.setContent(UP_ARROW);
			} else {
				buttonSVG.setContent(DOWN_ARROW);
			}
			break;
		case bottom:
			if(opened) {
				buttonSVG.setContent(DOWN_ARROW);
			} else {
				buttonSVG.setContent(UP_ARROW);
			}
			break;
		case right:
			if(opened) {
				buttonSVG.setContent(RIGHT_ARROW);
			} else {
				buttonSVG.setContent(LEFT_ARROW);
			}
			break;
		default:
			if(opened) {
				buttonSVG.setContent(LEFT_ARROW);
			} else {
				buttonSVG.setContent(RIGHT_ARROW);
			}
			break;
		}
	}

}
