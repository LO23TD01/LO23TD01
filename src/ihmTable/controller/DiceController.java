package ihmTable.controller;

import java.io.IOException;
import java.util.Random;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class DiceController {
	private static final String SELECTED_CLASS = "selected";
	private static final String DICE0 = "dice0", DICE1 = "dice1", DICE2 = "dice2", DICE3 = "dice3", DICE4 = "dice4", DICE5 = "dice5", DICE6 = "dice6";

    @FXML
    private StackPane dice;

	private Random random;
	private boolean selectable;
	private int value;
	private SimpleBooleanProperty selected;
	private ObservableValue<? extends Number> widthProperty, heightProperty;

	public void initialize() throws IOException {
		random = new Random();
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
		return this.value;
	}

	public void setValue(int value) {
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
		this.value = value;
	}

	public boolean isSelected() {
		return this.selected.getValue();
	}

	public SimpleBooleanProperty selectedProperty() {
		return this.selected;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public void setSelected(boolean selected) {
		this.selected.setValue(selected);
		if(this.selected.getValue()) {
		    dice.getStyleClass().add(SELECTED_CLASS);
		} else {
		    dice.getStyleClass().remove(SELECTED_CLASS);
		}
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
			setSelected(!selected.getValue());
		}
	}

	private void setStyleClass(String styleClass) {
		dice.getStyleClass().removeAll(DICE0, DICE1, DICE2, DICE3, DICE4, DICE5, DICE6);
		dice.getStyleClass().add(styleClass);
	}
}
