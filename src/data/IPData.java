package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IPData {
	private final StringProperty value;

	/**
	 *
	 */
	public IPData() {
		this.value = new SimpleStringProperty();
	}

	public IPData(String value) {
		this.value = new SimpleStringProperty(value);
	}

	public final StringProperty valueProperty() {
		return this.value;
	}

	public final String getValue() {
		return this.valueProperty().get();
	}

	public final void setValue(final String value) {
		this.valueProperty().set(value);
	}

}
