package ihmTable.controller;

import java.io.IOException;
import java.util.Random;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class DiceController {
	private static final String SELECTED_CLASS = "selected";
	private static final String DICE0 = "dice0", DICE1 = "dice1", DICE2 = "dice2", DICE3 = "dice3", DICE4 = "dice4", DICE5 = "dice5", DICE6 = "dice6";
//	private static final Image DICE0 = new Image("/ihmTable/resources/png/0.png");
//	private static final Image DICE1 = new Image("/ihmTable/resources/png/1.png");
//	private static final Image DICE2 = new Image("/ihmTable/resources/png/2.png");
//	private static final Image DICE3 = new Image("/ihmTable/resources/png/3.png");
//	private static final Image DICE4 = new Image("/ihmTable/resources/png/4.png");
//	private static final Image DICE5 = new Image("/ihmTable/resources/png/5.png");
//	private static final Image DICE6 = new Image("/ihmTable/resources/png/6.png");

    @FXML
    private StackPane dice;

	private Random random;
	private boolean selectable;
	public SimpleIntegerProperty value;

	public static final Boolean FALSE = false;

	private Integer position;
	public SimpleBooleanProperty selected;
	private ObservableValue<? extends Number> widthProperty, heightProperty;

	public void initialize() throws IOException {
		random = new Random();
		value = new SimpleIntegerProperty();
		setRandomValue();
		dice.setOnMouseClicked(event -> clicked());
		selected = new SimpleBooleanProperty(false);
		selectable = true;
	}

	public void setDice(boolean selectable, ObservableValue<? extends Number> widthProperty, ObservableValue<? extends Number> heightProperty) {
		setSelectable(selectable);
		this.widthProperty = widthProperty;
		this.heightProperty = heightProperty;
		this.widthProperty.addListener((observable, oldValue, newValue) -> updateSize());
		this.heightProperty.addListener((observable, oldValue, newValue) -> updateSize());
	}

	public void setRandomValue() {
		setValue(1 + random.nextInt(6));
	}

	public int getValue() {
		return this.value.getValue();
	}

	public void setValue(int value) {
		dice.setVisible(true);
		switch (value) {
		case 1:
			setStyleClass(DICE1);
			break;
		case 2:
			setStyleClass(DICE2);
			break;
		case 3:
			setStyleClass(DICE3);
			break;
		case 4:
			setStyleClass(DICE4);
			break;
		case 5:
			setStyleClass(DICE5);
			break;
		case 6:
			setStyleClass(DICE6);
			break;
		default:
			setStyleClass(DICE0);
			break;
		}
		this.value.setValue(value);
	}

	public void setPosition(Integer pos) {
		this.position = pos;
	}

	public boolean isSelected() {
		return this.selected.getValue();
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public void setSelected(boolean selected) {
		this.selected.setValue(selected);
	}

	private void updateSize() {
		double width = widthProperty.getValue().doubleValue();
		double height = heightProperty.getValue().doubleValue();
		if(width < height) {
			setSize(width);
		} else {
			setSize(height);
		}
	}

	public void setSize(double size) {
		dice.setPrefSize(size, size);
	}

	private void clicked() {
		if(selectable) {
			selected.setValue(!selected.getValue());
			if(selected.getValue()) {
			    dice.getStyleClass().add(SELECTED_CLASS);
			    System.out.println("dice "+ position.intValue() +" is selected");
			} else {
			    dice.getStyleClass().remove(SELECTED_CLASS);
			    System.out.println("dice "+ position.intValue() +" is not selected");
			}
		}
	}

	private void setStyleClass(String styleClass) {
		dice.getStyleClass().removeAll(DICE0, DICE1, DICE2, DICE3, DICE4, DICE5, DICE6);
		dice.getStyleClass().add(styleClass);
	}

}
