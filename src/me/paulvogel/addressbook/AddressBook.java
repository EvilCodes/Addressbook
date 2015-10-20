package me.paulvogel.addressbook;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

//The OOP Object for the addresses
public class AddressBook extends Observable implements Serializable {
//Observable -> Observed by the AddressBookGUI

    //Similar to array
    private ArrayList<Person> collection;
    private transient File file;
    //To check if file has changes (and has to be saved)
    private transient boolean changedSinceLastSave;

    public AddressBook() {
        this.collection = new ArrayList<Person>();
        this.file = null;
        this.changedSinceLastSave = false;
    }

    public int getNumberOfPersons() {
        return this.collection.size();
    }

    public void addPerson(int id, String first, String last, String address, String city, String state, String zip, String phone, String email) {
        this.collection.add(new Person(id, first, last, address, city, state, zip, phone, email));
        setChangedSinceLastSave(true);
    }

    public String getFullNameOfPerson(int id) {
        final Person localPerson = this.collection.get(id);
        return localPerson.getFirstName() + " " + localPerson.getLastName();
    }

    //How it's displayed in the GUI
    public String getFullInfoOfPerson(int id) {
        Person localPerson = this.collection.get(id);
        return localPerson.getPersonID() + ": " + localPerson.getFirstName() + " " + localPerson.getLastName() + " in " + localPerson.getAddress() + ", " + localPerson.getCity() + ", " + localPerson.getState() + " " + localPerson.getZIP() + " (" + localPerson.getPhone() + ")";
    }

    public String[] getOtherPersonInformation(int id) {
        final Person localPerson = this.collection.get(id);
        //Creating a simple String-array
        final String[] arrayOfString = {localPerson.getAddress(), localPerson.getCity(), localPerson.getState(), localPerson.getZIP(), localPerson.getPhone(), localPerson.getEmail()};
        return arrayOfString;
    }

    //Update all variables at once. If you don't want to, simply get the variables from the object and use them in the arguments
    public void updatePerson(int id, String address, String city, String state, String zip, String phone, String email) {
        this.collection.get(id).update(address, city, state, zip, phone, email);
        setChangedSinceLastSave(true);
    }

    public void removePerson(int id) {
        this.collection.remove(id);
        setChangedSinceLastSave(true);
    }

    public void sortByName() {
        Collections.sort(this.collection, new Person.CompareByName());
        setChangedSinceLastSave(true);
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File bookFile) {
        this.file = bookFile;
        setChanged();
        notifyObservers();
    }

    public String getTitle() {
        if (this.file == null)
            return "Neues Adressbuch";

        return this.file.getName();
    }

    public boolean getChangedSinceLastSave() {
        return this.changedSinceLastSave;
    }

    public void setChangedSinceLastSave(boolean changed) {
        this.changedSinceLastSave = changed;
        setChanged(); //from Observable
        notifyObservers(); //from Observable
    }


    //For saving all persons in a text file
    public ArrayList<Person> getAllPersons() {
        return this.collection;
    }

}

