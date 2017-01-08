package ihmTable.controller;

import java.io.IOException;
import java.util.Random;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

/**
 * Controller which manages a dice view
 */
public class DiceController {
	/**
	 * Style class for a selected dice
	 */
	private static final String SELECTED_CLASS = "selected";
	/**
	 * Style class for a dice without value
	 */
	private static final String DICE0 = "dice0";
	/**
	 * Style class for a dice with value 1
	 */
	private static final String DICE1 = "dice1";
	/**
	 * Style class for a dice with value 2
	 */
	private static final String DICE2 = "dice2";
	/**
	 * Style class for a dice with value 3
	 */
	private static final String DICE3 = "dice3";
	/**
	 * Style class for a dice with value 4
	 */
	private static final String DICE4 = "dice4";
	/**
	 * Style class for a dice with value 5
	 */
	private static final String DICE5 = "dice5";
	/**
	 * Style class for a dice with value 6
	 */
	private static final String DICE6 = "dice6";

    /**
     * The dice view
     */
    @FXML
    private StackPane dice;

	/**
	 * Random object to generate a random value for the dice
	 *
	 * @see Random
	 */
	private Random random;
	/**
	 * Whether the dice is selectable
	 */
	private boolean selectable;
	/**
	 * The value of the dice
	 */
	private int value;
	/**
	 * Whether the dice is selected
	 */
	private SimpleBooleanProperty selected;
	/**
	 * The width of the dice
	 */
	private ObservableValue<? extends Number> widthProperty;
	/**
	 * The height of the dice
	 */
	private ObservableValue<? extends Number> heightProperty;

	/**
	 * Initialize the controller
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		this.random = new Random();
		setRandomValue();
		this.dice.setOnMouseClicked(event -> clicked());
		this.selected = new SimpleBooleanProperty(false);
		this.selectable = true;
	}

	/**
	 * Set different properties of the dice
	 * @param selectable whether the dice is selectable
	 * @param widthProperty the width of the dice
	 * @param heightProperty the height of the dice
	 */
	public void setProperties(boolean selectable, ObservableValue<? extends Number> widthProperty, ObservableValue<? extends Number> heightProperty) {
		setSelectable(selectable);
		this.widthProperty = widthProperty;
		this.heightProperty = heightProperty;
		this.widthProperty.addListener((observable, oldValue, newValue) -> updateSize());
		this.heightProperty.addListener((observable, oldValue, newValue) -> updateSize());
	}

	/**
	 * Set random value to the dice view
	 */
	public void setRandomValue() {
		setValue(1 + this.random.nextInt(6));
	}

	/**
	 * Return the value of the dice
	 * @return the value of the dice
	 *
	 * @see DiceController#value
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Set the value of the dice
	 * @param value the value of the dice
	 *
	 * @see DiceController#value
	 */
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

	/**
	 * Whether the dice is selected
	 * @return whether the dice is selected
	 *
	 * @see DiceController#selected
	 */
	public boolean isSelected() {
		return this.selected.getValue();
	}

	/**
	 * The selected property
	 * @return the selected property
	 *
	 * @see DiceController#selected
	 */
	public SimpleBooleanProperty selectedProperty() {
		return this.selected;
	}

	/**
	 * Set whether the dice is selectable
	 * @param selectable whether the dice is selectable
	 *
	 * @see DiceController#selectable
	 */
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	/**
	 * Set whether the dice is selected
	 * @param selected whether the dice is selected
	 */
	public void setSelected(boolean selected) {
		this.selected.setValue(selected);
	}

	/**
	 * Set the size of the dice
	 * @param size the new size of the dice
	 */
	public void setSize(double size) {
		this.dice.setPrefSize(size, size);
	}

	/**
	 * Return the width of the dice
	 * @return the width of the dice
	 */
	public double getPrefSize() {
		return this.dice.getPrefWidth();
	}

	/**
	 * Update the size of the dice according the parent width and height
	 */
	private void updateSize() {
		double width = this.widthProperty.getValue().doubleValue();
		double height = this.heightProperty.getValue().doubleValue();
		if(width < height) {
			setSize(width);
		} else {
			setSize(height);
		}
	}

	/**
	 * Perform action when the dice is clicked
	 */
	private void clicked() {
		if(this.selectable) {
			setSelected(!this.selected.getValue());
			if(this.selected.getValue()) {
				this.dice.getStyleClass().add(SELECTED_CLASS);
			} else {
				this.dice.getStyleClass().remove(SELECTED_CLASS);
			}
		}
	}

	/**
	 * Set the style of the dice
	 * @param styleClass the new styleClass of the dice
	 */
	private void setStyleClass(String styleClass) {
		this.dice.getStyleClass().removeAll(DICE0, DICE1, DICE2, DICE3, DICE4, DICE5, DICE6);
		this.dice.getStyleClass().add(styleClass);
	}
}
