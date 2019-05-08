package ua.nure.kn.sadurska.usermanagement.gui;

import ua.nure.kn.sadurska.usermanagement.User;
import ua.nure.kn.sadurska.usermanagement.db.DatabaseException;
import ua.nure.kn.sadurska.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class BrowsePanel extends JPanel implements ActionListener {

    private static final int NO_ROW_SELECTED_STATUS = -1;
    private static final int USER_FIRST_NAME_COLUMN_INDEX = 0;
    private static final int USER_LAST_NAME_COLUMN_INDEX = 1;
    private static final int USER_DATE_OF_BIRTH_COLUMN_INDEX = 2;

    private MainFrame parent;
    private JPanel buttonPanel;
    private JScrollPane tablePanel;
    private JTable userTable;
    private JButton addButton;
    private JButton detailsButton;
    private JButton deleteButton;
    private JButton editButton;

    public BrowsePanel(final MainFrame mainFrame) {
        this.parent = mainFrame;
        initialize();
    }

    private void initialize() {
        this.setName("browsePanel");
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JScrollPane getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }

    private JTable getUserTable() {
        if (userTable == null) {
            userTable = new JTable();
            userTable.setName("userTable");
        }
        return userTable;
    }

    public void initTable() {
        getUserTable().setModel(getFilledUserTableModelWithUsers());
    }

    private UserTableModel getFilledUserTableModelWithUsers() {
        try {
            return new UserTableModel(parent.getUserDao().findAll());
        } catch (DatabaseException e) {
            return new UserTableModel(new ArrayList<>());
        }
    }

    private JPanel getButtonsPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getAddButton(), null);
            buttonPanel.add(getEditButton(), null);
            buttonPanel.add(getDeleteButton(), null);
            buttonPanel.add(getDetailsButton(), null);
        }
        return buttonPanel;
    }

    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton();
            addButton.setText(Messages.getString("BrowsePanel.add"));
            addButton.setName("addButton");
            addButton.setActionCommand("add");
            addButton.addActionListener(this);
        }
        return addButton;
    }

    private JButton getEditButton() {
        if (editButton == null) {
            editButton = new JButton();
            editButton.setText(Messages.getString("BrowsePanel.edit"));
            editButton.setName("editButton");
            editButton.setActionCommand("edit");
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText(Messages.getString("BrowsePanel.delete"));
            deleteButton.setName("deleteButton");
            deleteButton.setActionCommand("delete");
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getDetailsButton() {
        if (detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setText(Messages.getString("BrowsePanel.details"));
            detailsButton.setName("detailsButton");
            detailsButton.setActionCommand("details");
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final String actionCommand = e.getActionCommand();
        if (actionCommand.equalsIgnoreCase("add")) {
            invokeAddAction();
        } else if (actionCommand.equalsIgnoreCase("delete")) {
            invokeDeleteAction();
        } else if (actionCommand.equalsIgnoreCase("edit")) {
            invokeEditAction();
        } else if (actionCommand.equalsIgnoreCase("details")) {
            invokeDetailsAction();
        }
    }

    private void invokeAddAction() {
        this.setVisible(false);
        parent.showAddPanel();
    }

    private void invokeDeleteAction() {
        final User user = getSelectedUser();
        if (user != null) {
            final String message = "Delete " + user.getFullName() + "?";
            final int option = JOptionPane.showConfirmDialog(this, message);
            if (option == JOptionPane.OK_OPTION) {
                deleteUser(user);
                initTable();
            }
        }
    }

    private User getSelectedUser() {
        final int selectedRow = userTable.getSelectedRow();
        if (selectedRow != NO_ROW_SELECTED_STATUS) {
            final UserTableModel model = (UserTableModel) userTable.getModel();
            final User user = model.getUserAtRow(selectedRow);
            return user;
        }
        return null;
    }

    private void invokeEditAction() {
        this.setVisible(false);
        final int selectedRow = userTable.getSelectedRow();
        if (selectedRow != NO_ROW_SELECTED_STATUS) {
            final UserTableModel model = (UserTableModel) userTable.getModel();
            final User user = model.getUserAtRow(selectedRow);
            parent.showEditPanel(user);
        }
    }

    private void invokeDetailsAction() {
        setVisible(false);
        final int selectedRow = userTable.getSelectedRow();
        if (selectedRow != NO_ROW_SELECTED_STATUS) {
            final UserTableModel model = (UserTableModel) userTable.getModel();
            final User user = model.getUserAtRow(selectedRow);
            parent.showDetailsPanel(user);
        }
    }

    private void deleteUser(final User user) {
        try {
            if (user != null) {
                parent.getUserDao().delete(user);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    private Date parseDateFromString(String dateOfBirthString) {
        try {
            return DateFormat.getDateInstance().parse(dateOfBirthString);
        } catch (ParseException e) {
            return null;
        }
    }
}
