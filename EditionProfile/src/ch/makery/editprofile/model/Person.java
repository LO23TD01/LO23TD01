package ch.makery.editprofile.model;


import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 */
public class Person {
    private final StringProperty Pseudo;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final IntegerProperty Age;


    /**
     * Default constructor.
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param firstName
     * @param lastName
     * @param Pseudo
     */
    public Person(String pseudo, String firstName, String lastName) {
    	this.Pseudo= new SimpleStringProperty(pseudo);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
        this.Age = new SimpleIntegerProperty(18);

    }
    public String getPseudo(){
    	return Pseudo.get();
    }
    public void setPseudo(String pseudo){
    	this.Pseudo.set(pseudo);
    }
    public StringProperty pseudoProperty(){
    	return Pseudo;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }


    public int getAge() {
        return Age.get();
    }

    public void setAge(int age) {
        this.Age.set(age);
    }

    public IntegerProperty ageProperty() {
        return Age;
    }


}
