package data;

import java.util.ArrayList;
import java.util.List;

import data.User;

public class Client {
	private Rights defaultRight;
	private List<Contact> contactList;
	private List<ContactCategory> categoryList;
	
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
	
	public List<Contact> getContactList(){
		return contactList;
	}
	
}
