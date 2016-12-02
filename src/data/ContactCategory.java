package data;

import java.util.List;
import java.util.UUID;

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
@XmlType(propOrder = { "uuid","name", "rights", "contactList" })
public class ContactCategory {
	private final ObjectProperty<UUID> uuid;
	private final StringProperty name;
	private final ObservableList<Contact> contactList = FXCollections.observableArrayList();;
	private final ObjectProperty<Rights> rights;

	public ContactCategory() {
		this.uuid = new SimpleObjectProperty<UUID>(UUID.randomUUID());
		this.name = new SimpleStringProperty();
		this.rights = new SimpleObjectProperty<Rights>();
	}

	public ContactCategory(String name, Rights rights) {
		this.uuid = new SimpleObjectProperty<UUID>(UUID.randomUUID());
		this.name = new SimpleStringProperty(name);
		this.rights = new SimpleObjectProperty<Rights>(rights);
	}

	public boolean modifyCategory(String name, Rights rights) {
		this.setName(name);
		this.setRights(rights);
		return true;
	}

	public boolean addContact(Contact contact) {
		if (contact != null)
			return contactList.add(contact);
		else
			return false;
	}

	public boolean removeContact(Contact contact) {
		if (contact != null)
			return contactList.remove(contact);
		else
			return false;
	}

	public boolean removeContact(UUID contact) {
		if (contact != null)
			return contactList.removeIf(c -> c.getUuid().equals(contact));
		else
			return false;
	}

	public final ObjectProperty<UUID> uuidProperty() {
		return this.uuid;
	}

	public final UUID getUuid() {
		return this.uuidProperty().get();
	}

	@XmlElement
	public final void setUuid(final UUID uuid) {
		this.uuidProperty().set(uuid);
	}

	@XmlElementWrapper
	@XmlElement(name = "contact")
	public ObservableList<Contact> getDataList() {
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
