package ua.nure.kn.sadurska.usermanagement.gui;

import ua.nure.kn.sadurska.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

abstract class SubPanel extends JPanel implements ActionListener {

    private static final Color BACKGROUND_COLOR = Color.WHITE;

    MainFrame parent;
    JPanel fieldPanel;
    JPanel buttonPanel;
    JButton okButton;
    JButton cancelButton;
    JTextField firstNameField;
    JTextField lastNameField;
    JTextField dateOfBirthField;

    SubPanel(final MainFrame parent, final String panelName) {
        this.parent = parent;
        initialize(panelName);
    }

    private void initialize(final String panelName) {
        this.setName(panelName);
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.date_of_birth"), getDateOfBirthField());
        }
        return fieldPanel;
    }

    private void addLabeledField(final JPanel panel, final String labelText, final JTextField textField) {
        final JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField");
        }
        return dateOfBirthField;
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText(Messages.getString("AddPanel.ok"));
            okButton.setName("okButton");
            okButton.setActionCommand("ok");
            okButton.addActionListener(this);
        }
        return okButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText(Messages.getString("AddPanel.cancel"));
            cancelButton.setName("cancelButton");
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    void clearFields() {
        getFirstNameField().setText("");
        getFirstNameField().setBackground(BACKGROUND_COLOR);
        getLastNameField().setText("");
        getLastNameField().setBackground(BACKGROUND_COLOR);
        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(BACKGROUND_COLOR);
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        final List<String> commands = Arrays.asList("ok", "cancel");
        if (commands.contains(event.getActionCommand())) {
            this.setVisible(false);
            parent.showBrowsePanel();
        }
    }
}
