package data;

import java.util.List;

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

	public void addContact(Contact contact) {
		if (contact != null)
			contactList.add(contact);
	}

	public void removeContact(Contact contact) {
		if (contact != null)
		{
			this.contactList.remove(contact);
			for(int i= 0;i<this.categoryList.size();i++)
			{
				this.categoryList.get(i).removeContact(contact);
			}
		}
			
		
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
