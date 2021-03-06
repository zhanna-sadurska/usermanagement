package ua.nure.kn.sadurska.usermanagement.gui;

import ua.nure.kn.sadurska.usermanagement.User;
import ua.nure.kn.sadurska.usermanagement.db.DatabaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;

public class AddPanel extends SubPanel {

    public AddPanel(final MainFrame parent) {
        super(parent, "addPanel");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("ok")) {
            final User user = new User();
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            try {
                user.setDateOfBirth(DateFormat.getDateInstance().parse(getDateOfBirthField().getText()));
            } catch (ParseException ex) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getUserDao().create(user);
            } catch (DatabaseException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        clearFields();
        this.setVisible(false);
        parent.showBrowsePanel();
    }
}
