package me.paulvogel.addressbook;

import java.io.Serializable;
import java.util.Comparator;

public class Person implements Serializable {

    private int personID;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String phone;

    public Person(int id, String first, String last,
                  String address, String city,
                  String state, String zip,
                  String phone, String email) {

        this.personID = id;
        this.firstName = first;
        this.lastName = last;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;

    }


    public int getPersonID() {
        return this.personID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getZIP() {
        return this.zip;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }


    public void update(String address, String city, String state,
                       String zip, String phone, String email) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    public static class CompareByName implements Comparator {

        /**
         * @return a negative number if person1 belongs before person2 in alphabetical order of name;
         * 0 if they are equal; a positive number if person1 belongs after person2
         * @throws ClassCastException if a parameter is not a Person object
         */
        public int compare(Object person1, Object person2) {
            int compareByLast = ((Person) person1).getLastName().compareTo(((Person) person2).getLastName());
            if (compareByLast != 0) {
                return compareByLast;
            } else {
                return ((Person) person1).getFirstName().compareTo(((Person) person2).getFirstName());
            }
        }
    }

}
