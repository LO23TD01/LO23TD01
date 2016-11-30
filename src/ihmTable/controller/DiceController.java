package ihmTable.controller;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class DiceController {

	private static final String SELECTED_CLASS = "selected";
	private static final Image DICE1 = new Image("/ihmTable/resources/png/1.png");
	private static final Image DICE2 = new Image("/ihmTable/resources/png/2.png");
	private static final Image DICE3 = new Image("/ihmTable/resources/png/3.png");
	private static final Image DICE4 = new Image("/ihmTable/resources/png/4.png");
	private static final Image DICE5 = new Image("/ihmTable/resources/png/5.png");
	private static final Image DICE6 = new Image("/ihmTable/resources/png/6.png");

    @FXML
    private StackPane diceContainer;

    @FXML
    private ImageView dice;

	private Random random;
	private boolean selected, selectable;
	private int value;

	public void initialize() {
		random = new Random();
		setValue();
		dice.setOnMouseClicked(event -> clicked());
		selected = false;
		selectable = true;
		dice.setPreserveRatio(true);
		dice.setSmooth(true);
		dice.setCache(true);
	}

	public void setValue() {
		setValue(1 + random.nextInt(6 - 1 + 1));
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
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
		this.value = value;
	}

	public void setDice(boolean selectable, int size) {
	    setSelectable(selectable);
	    setSize(size);
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public void setSize(int size) {
	    diceContainer.setPrefSize(size, size);
	    dice.setFitHeight(size);
	    dice.setFitWidth(size);
	}

	private void clicked() {
		if(selectable) {
			selected = !selected;
			if(selected) {
			    diceContainer.getStyleClass().add(SELECTED_CLASS);
			} else {
			    diceContainer.getStyleClass().remove(SELECTED_CLASS);
			}
		}
	}

	private void setOne() {
		dice.setImage(DICE1);
	}

	private void setTwo() {
		dice.setImage(DICE2);
	}

	private void setThree() {
		dice.setImage(DICE3);
	}

	private void setFour() {
		dice.setImage(DICE4);
	}

	private void setFive() {
		dice.setImage(DICE5);
	}

	private void setSix() {
		dice.setImage(DICE6);
	}

}
