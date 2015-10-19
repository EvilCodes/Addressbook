package me.paulvogel.addressbook;


import javax.swing.*;
import java.io.File;
import java.io.IOException;

// Controller class to connect GUI, actual application, and file system
public class AddressBookController {

    private FileSystem fileSystem;

    public AddressBookController(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void doAdd(AddressBookGUI gui) {
        final String[] person = {"ID", "Vorname", "Nachname", "Strasse", "Stadt", "Bundesland", "PLZ", "Telefonnr", "Email"};
        final String[] personData = MultiInputPane.showMultiInputDialog(gui, person, "Informationen ueber Person");

        if (personData != null)
            gui.getAddressBook().addPerson(Integer.parseInt(personData[0]), personData[1], personData[2], personData[3], personData[4], personData[5], personData[6], personData[7], personData[8]);
    }

    public void doEdit(AddressBookGUI gui, int id) {
        final String name = gui.getAddressBook().getFullNameOfPerson(id);
        final String[] personInfo = gui.getAddressBook().getOtherPersonInformation(id);
        final String[] editInfo = {"Strasse", "Stadt", "Bundesland", "PLZ", "Telefonnr", "Email"};
        final String[] personData = MultiInputPane.showMultiInputDialog(gui, editInfo, personInfo, "Bearbeite " + name);
        if (personData != null)
            gui.getAddressBook().updatePerson(id, personData[0], personData[1], personData[2], personData[3], personData[4], personData[5]);
    }

    public void doDelete(AddressBookGUI gui, int id) {
        final String name = gui.getAddressBook().getFullNameOfPerson(id);
        if (JOptionPane.showConfirmDialog(gui, "Sicher, dass " + name + " geloescht werden soll?", "Loeschen!", 0) == 0)
            gui.getAddressBook().removePerson(id);
    }

    public void doSortByName(AddressBookGUI gui) {
        gui.getAddressBook().sortByName();
    }

    public void doNew(AddressBookGUI gui) {
        gui.setAddressBook(new AddressBook());
    }

    public void doOpen(AddressBookGUI gui) throws IOException, ClassCastException, ClassNotFoundException, InterruptedException, SecurityException {
        final JFileChooser localJFileChooser = new JFileChooser(System.getProperty("user.dir"));
        if (localJFileChooser.showOpenDialog(gui) == 0) {
            final File localFile = localJFileChooser.getSelectedFile();
            gui.setAddressBook(this.fileSystem.readFile(localFile));
        } else {
            throw new InterruptedException("Cancelled by user");
        }

    }

    public void doSave(AddressBookGUI gui) throws IOException, InterruptedException, SecurityException {
        File localFile = gui.getAddressBook().getFile();
        if (localFile == null) {
            JFileChooser localJFileChooser = new JFileChooser(System.getProperty("user.dir"));
            if (localJFileChooser.showSaveDialog(gui) == 0) {
                localFile = localJFileChooser.getSelectedFile();
                this.fileSystem.saveFile(gui.getAddressBook(), localFile);
            } else {
                throw new InterruptedException("Cancelled by user");
            }
        } else {
            this.fileSystem.saveFile(gui.getAddressBook(), localFile);
        }
    }

    //Warning if you want to close programm and have not saved the book
    public void doOfferSaveChanges(AddressBookGUI gui) throws InterruptedException, IOException, SecurityException {
        int i = JOptionPane.showConfirmDialog(gui, "Aenderungen speichern?", "Speichern", 1);
        switch (i) {
            case 1: //No (does not exist in this case)
                break;
            case 0: //Yes -> Save
                doSave(gui);
                break;
            case 2: //Close
            default:
                throw new InterruptedException("Cancelled by user");
        }

    }

}

