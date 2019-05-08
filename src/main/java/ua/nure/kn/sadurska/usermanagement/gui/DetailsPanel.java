package ua.nure.kn.sadurska.usermanagement.gui;

import ua.nure.kn.sadurska.usermanagement.User;

import java.text.SimpleDateFormat;

public class DetailsPanel extends SubPanel {


    public DetailsPanel(final MainFrame parent, final User user) {
        super(parent, "detailsPanel");
        initialize(user);
    }

    private void initialize(final User user) {
        firstNameField.setText(user.getFirstName());
        firstNameField.setEditable(false);

        lastNameField.setText(user.getLastName());
        lastNameField.setEditable(false);

        dateOfBirthField.setText(new SimpleDateFormat("MMM dd, yyyy").format(user.getDateOfBirth()));
        dateOfBirthField.setEditable(false);
    }
}
