package ua.nure.kn.sadurska.usermanagement.gui;

import ua.nure.kn.sadurska.usermanagement.User;
import ua.nure.kn.sadurska.usermanagement.util.Messages;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = { "ID", Messages.getString("AddPanel.first_name"), Messages.getString("AddPanel.last_name") };
    private static final Class[] COLUMN_CLASSES = { Long.class, String.class, String.class };
    private List<User> users;

    public UserTableModel(final Collection<User> users) {
        this.users = new ArrayList<>(users);
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    public Class getColumnClass(final int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    public String getColumnName(final int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final User user = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getFirstName();
            case 2:
                return user.getLastName();
            default:
                return null;
        }
    }
}
