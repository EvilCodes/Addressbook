package me.paulvogel.addressbook;

import java.awt.*;
import java.awt.event.WindowEvent;

//Main Class
public class AddressBookMain {

    public static void main(String[] args) {
        final FileSystem fileSystem = new FileSystem(); //Save files (serialization)
        final AddressBookController controller = new AddressBookController(fileSystem); //To connect GUI & Functions
        final AddressBookGUI gui = new AddressBookGUI(controller, new AddressBook()); //Open the GUI
        gui.setVisible(true);
    }

    public static void quitApplication() {
        //Close Frames
        final Frame[] openWindows = Frame.getFrames();

        for (final Frame openWindow : openWindows) {
            //Close frames of AddressBookGUI
            if (openWindow instanceof AddressBookGUI)
                openWindow.dispatchEvent(new WindowEvent(openWindow, WindowEvent.WINDOW_CLOSING));
        }
        System.exit(0);
    }

    /*

    ERKL�RUNG OBSERVER & OBSERVABLE

    Wir wollen uns nun mit dem Observer-Pattern besch�ftigen, das seine Urspr�nge in Smalltalk-80 hat.
    Dort ist es etwas erweitert unter dem Namen MVC (Model-View-Controller) bekannt, ein K�rzel, mit dem auch wir uns noch n�her besch�ftigen m�ssen,
    da dies ein ganz wesentliches Konzept bei der Programmierung grafischer Bedieneroberfl�chen mit Swing ist.

    Stellen wir uns eine Party mit einer netten Gesellschaft vor. Hier finden sich zur�ckhaltende, passive G�ste und aktive Erz�hler.
    Die Zuh�rer sind interessiert an den Gespr�chen der Unterhalter. Da die Erz�hler nun von den Zuh�rern beobachtet werden, bekommen sie den Namen Beobachtete,
    auf Englisch auch observables (Beobachtbare) genannt. Die Erz�hler interessieren sich jedoch nicht daf�r, wer ihnen zuh�rt.
    F�r sie sind alle Zuh�rer gleich. Sie schweigen aber, wenn ihnen �berhaupt niemand zuh�rt.
    Die Zuh�rer reagieren auf Witze der Unterhalter und werden dadurch zu Beobachtern (engl. observers).


    Unser Beispiel mit den Erz�hlern und Zuh�rern k�nnen wir auf Datenstrukturen �bertragen.
    Die Datenstruktur l�sst sich beobachten und wird zum Beobachteten. Sie wird in Java als Exemplar der Bibliotheksklasse Observable repr�sentiert.
    Der Beobachter wird durch die Schnittstelle Observer abgedeckt und ist der, der informiert werden will, wenn sich die Datenstruktur �ndert. (AddressBookGUI)
    Jedes Exemplar der Observable-Klasse informiert nun alle seine Horcher, sobald sich sein Zustand �ndert. (AddressBook)


    Eine Klasse, deren Exemplare sich beobachten lassen, muss jede �nderung des Objektzustands nach au�en hin mitteilen.
    Dazu bietet die Klasse Observable die Methoden setChanged() und notifyObservers() an. Mit setChanged() wird die �nderung angek�ndigt,
    und mit notifyObservers() wird sie tats�chlich �bermittelt. Gibt es keine �nderung, so wird notifyObservers() auch niemanden benachrichtigen.


    VON: http://openbook.rheinwerk-verlag.de/javainsel/javainsel_10_002.html

     */

}
