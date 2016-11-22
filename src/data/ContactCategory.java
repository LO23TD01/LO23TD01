package data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"name", "rights", "contactList"})
public class ContactCategory {
	private String name;
	private List<Contact> contactList;
	private Rights rights;
	
	public ContactCategory(){
		
	}
	
	public ContactCategory(String name, Rights rights) {
		this.name = name;
		this.contactList = new ArrayList<Contact>();
		this.rights = rights;
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

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public List<Contact> getContactList() {
		return contactList;
	}

	@XmlElementWrapper
	@XmlElement (name = "contact")
	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}

	public Rights getRights() {
		return rights;
	}

	@XmlElement
	public void setRights(Rights rights) {
		this.rights = rights;
	}
	
	
	
}
