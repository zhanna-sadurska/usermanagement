package ua.nure.kn.sadurska.usermanagement.gui;

import ua.nure.kn.sadurska.usermanagement.User;
import ua.nure.kn.sadurska.usermanagement.db.DatabaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditPanel extends SubPanel {

    private final User editableUser;

    public EditPanel(final MainFrame parent, final User editableUser) {
        super(parent, "editPanel");
        this.editableUser = editableUser;
        setUsersInfo();
    }

    private void setUsersInfo() {
        firstNameField.setText(editableUser.getFirstName());
        lastNameField.setText(editableUser.getLastName());
        dateOfBirthField.setText(new SimpleDateFormat("MMM dd, yyyy").format(editableUser.getDateOfBirth()));
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("ok")) {
            editableUser.setFirstName(firstNameField.getText());
            editableUser.setLastName(lastNameField.getText());
            try {
                editableUser.setDateOfBirth(DateFormat.getDateInstance().parse(getDateOfBirthField().getText()));
            } catch (ParseException ex) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getUserDao().update(editableUser);
            } catch (DatabaseException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        clearFields();
        this.setVisible(false);
        parent.showBrowsePanel();
    }
}
