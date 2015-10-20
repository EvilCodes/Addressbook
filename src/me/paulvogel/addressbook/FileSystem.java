package me.paulvogel.addressbook;

import java.io.*;
import java.util.ArrayList;

//Saving and reading of the addressbook files
public class FileSystem {

    public AddressBook readFile(final File bookFile) {
        final AddressBook addressBook = new AddressBook();

        final ArrayList<String> lineList = new ArrayList<String>();

        try {
            final BufferedReader in = new BufferedReader(new FileReader(bookFile)); //Buffered reader is better for many short lines
            String line;
            while ((line = in.readLine()) != null)
                lineList.add(line);

            in.close();
        } catch (FileNotFoundException e) {
            //Could not find file to read
            e.printStackTrace();
        } catch (IOException e) {
            //Could not read file
            e.printStackTrace();
        }

        for (final String line : lineList) {
            final String[] items = line.split(" || ");
            if (items.length >= 9)
                addressBook.addPerson(Integer.parseInt(items[0]), items[1], items[2], items[3], items[4], items[5], items[6], items[7], items[8]);
        }

        addressBook.setChangedSinceLastSave(false);
        addressBook.setFile(bookFile);
        return addressBook;
    }

    public void saveFile(final AddressBook book, final File bookFile) {
        if (!bookFile.exists()) {
            try {
                bookFile.createNewFile();
            } catch (IOException e) {
                //Could not create new file
                e.printStackTrace();
            }
        }

        try {
            final FileWriter fileWriter = new FileWriter(bookFile);
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); //Buffered writer is better for many short lines

            for (final Person person : book.getAllPersons()) {
                String outputString = person.getPersonID() + " || ";
                outputString += person.getFirstName() + " || ";
                outputString += person.getLastName() + " || ";
                outputString += person.getAddress() + " || ";
                outputString += person.getCity() + " || ";
                outputString += person.getState() + " || ";
                outputString += person.getZIP() + " || ";
                outputString += person.getPhone() + " || ";
                outputString += person.getEmail();
                bufferedWriter.write(outputString);
            }

            bufferedWriter.close();
        } catch (IOException e) {
            //Could not write file
            e.printStackTrace();
        }

    }


    /*
    public AddressBook readFile(final File bookFile) throws IOException, ClassCastException, ClassNotFoundException {
        final ObjectInputStream localObjectInputStream = new ObjectInputStream(new FileInputStream(bookFile));
        final AddressBook tempAddressBook = (AddressBook) localObjectInputStream.readObject();
        tempAddressBook.setChangedSinceLastSave(false);
        tempAddressBook.setFile(bookFile);
        return tempAddressBook;
    }

    public void saveFile(final AddressBook book, final File bookFile) throws IOException {
        final ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(new FileOutputStream(bookFile));
        localObjectOutputStream.writeObject(book);
        book.setChangedSinceLastSave(false);
        book.setFile(bookFile);
    }
    */

}

