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

    ERKLÄRUNG OBSERVER & OBSERVABLE

    Wir wollen uns nun mit dem Observer-Pattern beschäftigen, das seine Ursprünge in Smalltalk-80 hat.
    Dort ist es etwas erweitert unter dem Namen MVC (Model-View-Controller) bekannt, ein Kürzel, mit dem auch wir uns noch näher beschäftigen müssen,
    da dies ein ganz wesentliches Konzept bei der Programmierung grafischer Bedieneroberflächen mit Swing ist.

    Stellen wir uns eine Party mit einer netten Gesellschaft vor. Hier finden sich zurückhaltende, passive Gäste und aktive Erzähler.
    Die Zuhörer sind interessiert an den Gesprächen der Unterhalter. Da die Erzähler nun von den Zuhörern beobachtet werden, bekommen sie den Namen Beobachtete,
    auf Englisch auch observables (Beobachtbare) genannt. Die Erzähler interessieren sich jedoch nicht dafür, wer ihnen zuhört.
    Für sie sind alle Zuhörer gleich. Sie schweigen aber, wenn ihnen überhaupt niemand zuhört.
    Die Zuhörer reagieren auf Witze der Unterhalter und werden dadurch zu Beobachtern (engl. observers).


    Unser Beispiel mit den Erzählern und Zuhörern können wir auf Datenstrukturen übertragen.
    Die Datenstruktur lässt sich beobachten und wird zum Beobachteten. Sie wird in Java als Exemplar der Bibliotheksklasse Observable repräsentiert.
    Der Beobachter wird durch die Schnittstelle Observer abgedeckt und ist der, der informiert werden will, wenn sich die Datenstruktur ändert. (AddressBookGUI)
    Jedes Exemplar der Observable-Klasse informiert nun alle seine Horcher, sobald sich sein Zustand ändert. (AddressBook)


    Eine Klasse, deren Exemplare sich beobachten lassen, muss jede Änderung des Objektzustands nach außen hin mitteilen.
    Dazu bietet die Klasse Observable die Methoden setChanged() und notifyObservers() an. Mit setChanged() wird die Änderung angekündigt,
    und mit notifyObservers() wird sie tatsächlich übermittelt. Gibt es keine Änderung, so wird notifyObservers() auch niemanden benachrichtigen.


    VON: http://openbook.rheinwerk-verlag.de/javainsel/javainsel_10_002.html

     */

}
