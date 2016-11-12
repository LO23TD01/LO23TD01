package ihmTable.controller;

import ihmTable.controller.CollapsiblePanelController.Position;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.SVGPath;

public class CollapseButtonController {

	private static String RIGHT_ARROW = "M8 0c-4.418 0-8 3.582-8 8s3.582 8 8 8 8-3.582 8-8-3.582-8-8-8zM8 14.5c-3.59 0-6.5-2.91-6.5-6.5s2.91-6.5 6.5-6.5 6.5 2.91 6.5 6.5-2.91 6.5-6.5 6.5zM5.543 11.043l1.414 1.414 4.457-4.457-4.457-4.457-1.414 1.414 3.043 3.043z";
	private static String LEFT_ARROW = "M8 16c4.418 0 8-3.582 8-8s-3.582-8-8-8-8 3.582-8 8 3.582 8 8 8zM8 1.5c3.59 0 6.5 2.91 6.5 6.5s-2.91 6.5-6.5 6.5-6.5-2.91-6.5-6.5 2.91-6.5 6.5-6.5zM10.457 4.957l-1.414-1.414-4.457 4.457 4.457 4.457 1.414-1.414-3.043-3.043z";
	private static String UP_ARROW = "M0 8c0 4.418 3.582 8 8 8s8-3.582 8-8-3.582-8-8-8-8 3.582-8 8zM14.5 8c0 3.59-2.91 6.5-6.5 6.5s-6.5-2.91-6.5-6.5 2.91-6.5 6.5-6.5 6.5 2.91 6.5 6.5zM11.043 10.457l1.414-1.414-4.457-4.457-4.457 4.457 1.414 1.414 3.043-3.043z";
	private static String DOWN_ARROW = "M16 8c0-4.418-3.582-8-8-8s-8 3.582-8 8 3.582 8 8 8 8-3.582 8-8zM1.5 8c0-3.59 2.91-6.5 6.5-6.5s6.5 2.91 6.5 6.5-2.91 6.5-6.5 6.5-6.5-2.91-6.5-6.5zM4.957 5.543l-1.414 1.414 4.457 4.457 4.457-4.457-1.414-1.414-3.043 3.043z";

    @FXML
    private Button collapseButton;

    @FXML
    private SVGPath buttonSVG;

    private CollapsiblePanelController collapsiblePanelController;
    private Position position;
    private boolean opened;

    public void initialize() {
    	this.opened = true;
    }

    public void setCollapseButton(CollapsiblePanelController collapsiblePanelController) {
    	this.collapsiblePanelController = collapsiblePanelController;
    	this.position = collapsiblePanelController.getPosition();
    	setCollapseButtonImage();
    	this.collapseButton.setOnAction(event -> collapse());
    }

    private void collapse() {
    	this.collapsiblePanelController.collapse();
    	this.opened = !this.opened;
    	setCollapseButtonImage();
    }

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
