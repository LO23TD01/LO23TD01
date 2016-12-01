package data;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement
@XmlType(propOrder = { "name", "rights", "contactList" })
public class ContactCategory {
	private final StringProperty name;
	private final ObservableList<Contact> contactList = FXCollections.observableArrayList();;
	private final ObjectProperty<Rights> rights;

	public ContactCategory() {
		this.name = new SimpleStringProperty();
		this.rights = new SimpleObjectProperty<Rights>();
	}

	public ContactCategory(String name, Rights rights) {
		this.name = new SimpleStringProperty(name);
		this.rights = new SimpleObjectProperty<Rights>(rights);
	}

	public void addContact(Contact contact) {
		if (contact != null)
			contactList.add(contact);
	}

	public void removeContact(Contact contact) {
		if (contact != null)
			contactList.remove(contact);
	}

	@XmlElementWrapper
	@XmlElement(name = "contact")
	public ObservableList<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(List<Contact> contactList) {
		this.contactList.clear();
		this.contactList.addAll(contactList);
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	@XmlElement
	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final ObjectProperty<Rights> rightsProperty() {
		return this.rights;
	}

	public final Rights getRights() {
		return this.rightsProperty().get();
	}

	@XmlElement
	public final void setRights(final Rights rights) {
		this.rightsProperty().set(rights);
	}

}
