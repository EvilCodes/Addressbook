package me.paulvogel.addressbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Pop-Up box for updating and creating a person
/*
  Taken from https://github.com/mfinan-webelevate/WebelevateLabs01To05/blob/master/javabook/MultiInputBox.java
 */
public class MultiInputPane extends JOptionPane {

    private JTextField[] fields;
    private boolean ok;

    /* Private constructor because it's only used by the two methods above */
    private MultiInputPane(final String[] prompts, final String[] initialValues) {

        super("Person Information");
        removeAll();

        setLayout(new GridLayout(prompts.length + 1, 2, 5, 5));
        fields = new JTextField[prompts.length];

        for (int i = 0; i < prompts.length; i++) {
            add(new JLabel(prompts[i]));
            fields[i] = new JTextField();
            add(fields[i]);
            if (initialValues != null && initialValues[i] != null) {
                fields[i].setText(initialValues[i]);
            }
        }

        JPanel okPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okPanel.add(okButton);
        add(okPanel);
        JPanel cancelPanel = new JPanel();
        JButton cancelButton = new JButton("Abbrechen");
        cancelPanel.add(cancelButton);
        add(cancelPanel);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ok = true;
                getTopLevelAncestor().setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getTopLevelAncestor().setVisible(false);
            }
        });
    }

    public static String[] showMultiInputDialog(final Component parentComponent, final String[] prompts, final String[] initialValues, final String title) {
        final MultiInputPane pane = new MultiInputPane(prompts, initialValues);
        final JDialog dialog = pane.createDialog(parentComponent, title != null ? title : "MultiInputPane");
        dialog.pack();
        dialog.setVisible(true);

        if (!pane.ok)
            return null;

        final String[] results = new String[prompts.length];
        for (int i = 0; i < prompts.length; i++)
            results[i] = pane.fields[i].getText();

        return results;
    }

    public static String[] showMultiInputDialog(final Component parentComponent, final String[] prompts, final String title) {
        return showMultiInputDialog(parentComponent, prompts, null, title);
    }

}

