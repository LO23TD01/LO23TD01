package ihmTable.controller;

import java.io.IOException;
import java.util.Random;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	private boolean selectable;
	public SimpleIntegerProperty value;

	public static final Boolean FALSE = false;

	private Integer position;
	public SimpleBooleanProperty selected;

	public void initialize() throws IOException {
		random = new Random();
		value = new SimpleIntegerProperty();
		setValue();
		dice.setOnMouseClicked(event -> clicked());
		selected = new SimpleBooleanProperty(false);
		selectable = true;
		dice.setPreserveRatio(true);
		dice.setSmooth(true);
		dice.setCache(true);
	}

	public void setValue() {
		setValue(1 + random.nextInt(6 - 1 + 1));
	}

	public int getValue() {
		return this.value.getValue();
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
		this.value.setValue(value);
	}

	public void setPosition(Integer pos)
	{
		this.position = pos;
	}

	public boolean isSelected(){
		return this.selected.getValue();
	}

	public void setDice(boolean selectable, int size) {
	    setSelectable(selectable);
	    setSize(size);
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public void setSelected(boolean selected) {
		this.selected.setValue(selected);
	}

	public void setSize(int size) {
	    diceContainer.setPrefSize(size, size);
	    dice.setFitHeight(size);
	    dice.setFitWidth(size);
	}

	private void clicked() {
		if(selectable) {
			selected.setValue(!selected.getValue());
			if(selected.getValue()) {
			    diceContainer.getStyleClass().add(SELECTED_CLASS);
			    System.out.println("dice "+ position.intValue() +" is select");
			} else {
			    diceContainer.getStyleClass().remove(SELECTED_CLASS);
			    System.out.println("dice "+ position.intValue() +" is not select");
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
