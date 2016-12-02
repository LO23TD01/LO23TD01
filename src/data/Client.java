package data;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import data.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement
@XmlType(propOrder = { "contactList", "defaultRight", "categoryList" })
public class Client {
	private final ObjectProperty<Profile> profile;
	private final ObjectProperty<Rights> defaultRight;
	private final ObservableList<Contact> contactList = FXCollections.observableArrayList();;
	private final ObservableList<ContactCategory> categoryList = FXCollections.observableArrayList();;

	public Client() {
		this.defaultRight = new SimpleObjectProperty<Rights>();
		this.profile = new SimpleObjectProperty<Profile>();
	}

	public Client(Rights defaultRight) {
		this.defaultRight = new SimpleObjectProperty<Rights>(defaultRight);
		this.profile = new SimpleObjectProperty<Profile>();
	}

	public Profile giveProfileData(User user) {
		return this.profile.get();
	}

	public boolean addContact(Contact contact) {
		if (contact != null)
			return contactList.add(contact);
		return false;
	}

	private Contact getContactByUuid(UUID uuidContact) {
		return contactList.stream().filter(c -> c.getUuid().equals(uuidContact)).findFirst().get();
	}

	public boolean deleteCategoryByUuid(UUID uuidCategory) {
		return categoryList.removeIf(c -> c.getUuid().equals(uuidCategory));
	}

	private ContactCategory getContactCategoryByUuid(UUID uuidContactCategory) {
		return categoryList.stream().filter(c -> c.getUuid().equals(uuidContactCategory)).findFirst().get();
	}

	public boolean addContactToCategory(UUID uuidContact, UUID uuidCategory) {
		return this.getContactCategoryByUuid(uuidCategory).addContact(getContactByUuid(uuidContact));
	}

	public boolean removeContact(UUID uuidContact) {
		this.getCategoryList().forEach(c -> c.removeContact(uuidContact));
		return this.getContactList().removeIf(c -> c.getUuid().equals(uuidContact));
	}

	public boolean removeContactFromCategory(UUID uuidContact, UUID uuidCategory) {
		if (uuidContact != null)
			return categoryList.stream().filter(c -> c.getUuid().equals(uuidCategory)).findFirst().get()
					.removeContact(uuidContact);
		else
			return false;
	}

	public boolean modifyCategory(UUID uuidCategory, String name, Rights rights) {
		return this.getContactCategoryByUuid(uuidCategory).modifyCategory(name, rights);
	}

	public boolean addCategory(String name, Rights rights) {
		return this.categoryList.add(new ContactCategory(name, rights));
	}

	/*
	 *
	 * Getters & Setters
	 *
	 *
	 */

	@XmlElementWrapper
	@XmlElement(name = "contact")
	public ObservableList<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(List<Contact> contactList) {
		this.contactList.clear();
		this.contactList.addAll(contactList);
	}

	@XmlElementWrapper
	@XmlElement(name = "categorie")
	public ObservableList<ContactCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<ContactCategory> categorieList) {
		this.categoryList.clear();
		this.categoryList.addAll(categorieList);
	}

	public final ObjectProperty<Rights> defaultRightProperty() {
		return this.defaultRight;
	}

	public final Rights getDefaultRight() {
		return this.defaultRightProperty().get();
	}

	@XmlElement
	public final void setDefaultRight(final Rights defaultRight) {
		this.defaultRightProperty().set(defaultRight);
	}

}
