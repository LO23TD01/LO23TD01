package data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import data.User;

@XmlRootElement
@XmlType(propOrder = {"contactList", "defaultRight", "categoryList"})
public class Client {
	private Rights defaultRight;
	private List<Contact> contactList;
	private List<ContactCategory> categoryList;
	
	public Client() {

	}
	
	public Client(Rights defaultRight) {
		this.defaultRight = defaultRight;
		this.contactList = new ArrayList<Contact>();
		this.categoryList = new ArrayList<ContactCategory>();
	}
	
	public Profile giveProfileData(User user){
		//TO-DO : Pas sûr de ce que doit faire cette fonction
		return null;
	}
	
	public void addContact(Contact contact){
		if(contact != null)
			contactList.add(contact);
	}
	
	public void removeContact(Contact contact){
		if(contact != null)
			contactList.remove(contact);
	}
	
	/*
	 * 
	 * Getters & Setters
	 * 
	 * 
	 */
	
	public List<Contact> getContactList(){
		return contactList;
	}

	public Rights getDefaultRight() {
		return defaultRight;
	}

	@XmlElement
	public void setDefaultRight(Rights defaultRight) {
		this.defaultRight = defaultRight;
	}

	
	public List<ContactCategory> getCategoryList() {
		return categoryList;
	}

	@XmlElementWrapper
	@XmlElement (name = "categorie")
	public void setCategoryList(List<ContactCategory> categoryList) {
		this.categoryList = categoryList;
	}

	@XmlElementWrapper
	@XmlElement (name = "contact")
	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}
	
	
	
}
