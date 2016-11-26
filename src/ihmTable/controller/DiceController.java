package ihmTable.controller;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DiceController {

	private static final BorderWidths BORDER_WIDTHS = new BorderWidths(2);
	private static final CornerRadii CORNER_RADII = new CornerRadii(5);

    @FXML
    private AnchorPane diceContainer;

	@FXML
	private GridPane dice;

	private Random random;
	private boolean selected;
	private Border selectedBorder, unselectedBorder;


	public void initialize() {
		selectedBorder = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CORNER_RADII, BORDER_WIDTHS));
		unselectedBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CORNER_RADII, BORDER_WIDTHS));
		random = new Random();
		setDiceValue();
		dice.setOnMouseClicked(event -> clicked());
		selected = false;
	}

	private AnchorPane getDot() {
		AnchorPane anchorPane = new AnchorPane();
		Circle circle = new Circle(12, 12, 5);
		circle.setFill(Color.BLACK);
		anchorPane.getChildren().add(circle);
		return anchorPane;
	}

	public void clicked() {
		selected = !selected;
		if(selected) {
			diceContainer.setBorder(selectedBorder);
		} else {
			diceContainer.setBorder(unselectedBorder);
		}
	}

	public void setDiceValue() {
		dice.getChildren().clear();
		int value = 1 + random.nextInt(6 - 1 + 1);
		switch (value) {
		case 1:
			setOne();
			break;
		case 2:
			setTwo();
			break;
		case 3:
			setThree();
			break;
		case 4:
			setFour();
			break;
		case 5:
			setFive();
			break;
		case 6:
			setSix();
			break;
		default:
			break;
		}
	}

	private void setOne() {
		dice.add(getDot(), 1, 1);
	}

	private void setTwo() {
		dice.add(getDot(), 0, 0);
		dice.add(getDot(), 2, 2);
	}

	private void setThree() {
		dice.add(getDot(), 1, 0);
		dice.add(getDot(), 1, 1);
		dice.add(getDot(), 1, 2);
	}

	private void setFour() {
		dice.add(getDot(), 0, 0);
		dice.add(getDot(), 2, 0);
		dice.add(getDot(), 0, 2);
		dice.add(getDot(), 2, 2);
	}

	private void setFive() {
		dice.add(getDot(), 0, 0);
		dice.add(getDot(), 2, 0);
		dice.add(getDot(), 0, 2);
		dice.add(getDot(), 2, 2);
		dice.add(getDot(), 1, 1);
	}

	private void setSix() {
		dice.add(getDot(), 0, 0);
		dice.add(getDot(), 2, 0);
		dice.add(getDot(), 0, 1);
		dice.add(getDot(), 2, 1);
		dice.add(getDot(), 0, 2);
		dice.add(getDot(), 2, 2);
	}
}
