package me.paulvogel.addressbook;

import java.io.*;

//Saving and reading of the addressbook files
public class FileSystem {

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

}

