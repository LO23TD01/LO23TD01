package data;

import java.util.ArrayList;
import java.util.List;

public class ContactCategory {
	private String name;
	private List<Contact> contactList;
	private Rights rights;
	
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
}
