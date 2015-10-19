package me.paulvogel.addressbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

//The whole GUI
public class AddressBookGUI extends JFrame implements Observer {
//Observer -> If AddressBook (the observed) gets changed it will automatically call the update method below.

    private NameListModel nameListModel;
    private JList nameList;
    private JButton addButton, editButton, deleteButton, sortByNameButton;
    private JMenuItem newItem, openItem, saveItem, quitItem;
    private AddressBookController controller;
    private AddressBook addressBook;

    public AddressBookGUI(final AddressBookController controller, AddressBook addressBook) {

        super("Adressbuch");
        this.controller = controller;

        // Create and add file menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Datei");
        newItem = new JMenuItem("Neues Buch", 'N');
        fileMenu.add(newItem);
        openItem = new JMenuItem("Buch Oeffnen", 'O');
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        saveItem = new JMenuItem("Buch Speichern", 'S');
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        quitItem = new JMenuItem("Beenden", 'B');
        fileMenu.add(quitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        nameListModel = new NameListModel();

        setAddressBook(addressBook);


        JPanel buttonPanel = new JPanel();
        addButton = new JButton("    Hinzufuegen    ");
        buttonPanel.add(addButton);
        editButton = new JButton("    Bearbeiten    ");
        buttonPanel.add(editButton);
        deleteButton = new JButton("   Loeschen   ");
        buttonPanel.add(deleteButton);
        sortByNameButton = new JButton("Nach Namen Sortieren");
        buttonPanel.add(sortByNameButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        nameList = new JList(nameListModel);
        JScrollPane listPane = new JScrollPane(nameList);
        nameList.setVisibleRowCount(20);
        listPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.gray, 1)));
        getContentPane().add(listPane, BorderLayout.CENTER);

        //Add a new person
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.doAdd(AddressBookGUI.this);
                int index = getAddressBook().getNumberOfPersons() - 1;
                // This will ensure that the person just added is visible in list
                nameList.ensureIndexIsVisible(index);
            }
        });

        //Edit a person
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = nameList.getSelectedIndex();
                if (index < 0) {
                    reportError("Du musst vorher eine Person auswaehlen!");
                } else {
                    controller.doEdit(AddressBookGUI.this, index);
                }
            }
        });

        //Delete a person
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = nameList.getSelectedIndex();
                if (index < 0) {
                    reportError("Du musst vorher eine Person auswaehlen!");
                } else {
                    controller.doDelete(AddressBookGUI.this, index);
                }
            }
        });

        //Sort by name
        sortByNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.doSortByName(AddressBookGUI.this);
            }
        });


        //New addressbook
        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getAddressBook().getChangedSinceLastSave()) {
                        controller.doOfferSaveChanges(AddressBookGUI.this);
                    }
                    controller.doNew(AddressBookGUI.this);
                } catch (IOException exception) {
                    reportError("Konnte nicht auf Datei schreiben:\n" + exception);
                } catch (InterruptedException exception) {
                } catch (SecurityException exception) {
                    reportError("Konnte wegen nicht ausreichenden Berechtigungen nicht schreiben:\n" + exception);
                }
            }
        });

        //Open addressbook
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getAddressBook().getChangedSinceLastSave()) {
                        controller.doOfferSaveChanges(AddressBookGUI.this);
                    }
                    controller.doOpen(AddressBookGUI.this);
                } catch (IOException exception) {
                    reportError("Konnte Datei nicht lesen oder schreiben:\n" + exception);
                } catch (InterruptedException exception) {
                } catch (SecurityException exception) {
                    reportError("Konnte wegen nicht ausreichenden Berechtigungen nicht schreiben:\n" + exception);
                } catch (Exception exception) {
                    reportError("Die Datei muss eine gueltige Adressbuchdatei sein!");
                }
            }
        });

        //Save addressbook
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.doSave(AddressBookGUI.this);
                } catch (IOException exception) {
                    reportError("Konnte Datei nicht schreiben:\n" + exception);
                } catch (InterruptedException exception) {
                } catch (SecurityException exception) {
                    reportError("Konnte wegen nicht ausreichenden Berechtigungen nicht schreiben:\n" + exception);
                }
            }
        });

        //Quit programm
        quitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddressBookMain.quitApplication();
            }
        });

        //Overridden by method above
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    if (getAddressBook().getChangedSinceLastSave())
                        controller.doOfferSaveChanges(AddressBookGUI.this);
                    dispose();
                    if (Frame.getFrames().length == 0)
                        AddressBookMain.quitApplication();
                } catch (IOException exception) {
                    reportError("Konnte Datei nicht schreiben:\n" + exception);
                } catch (InterruptedException exception) {
                } catch (SecurityException exception) {
                    reportError("Konnte wegen nicht ausreichenden Berechtigungen nicht schreiben:\n" + exception);
                }

            }
        });


        //Doubleclick on item in list -> Edit item
        nameList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = nameList.locationToIndex(e.getPoint());
                    controller.doEdit(AddressBookGUI.this, index);
                }
            }
        });
        pack();
    }


    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        if (this.addressBook != null)
            this.addressBook.deleteObserver(this);
        this.addressBook = addressBook;
        addressBook.addObserver(this);
        update(addressBook, null);
    }

    public void reportError(String message) {
        //Simply show message
        JOptionPane.showMessageDialog(this, message, "Adressbuch | Error", JOptionPane.ERROR_MESSAGE);
    }

    public void update(Observable o, Object arg) {
        if (o == addressBook) {
            setTitle("Adressbuch | " + addressBook.getTitle());
            saveItem.setEnabled(addressBook.getChangedSinceLastSave());
            nameListModel.contentsChanged();
        }
    }


    //Names in list are made with this little class
    private class NameListModel extends AbstractListModel {
        void contentsChanged() {
            super.fireContentsChanged(this, 0, 0);
        }

        public Object getElementAt(int index) {
            return getAddressBook().getFullInfoOfPerson(index);
        }

        public int getSize() {
            return getAddressBook().getNumberOfPersons();
        }
    }

}
