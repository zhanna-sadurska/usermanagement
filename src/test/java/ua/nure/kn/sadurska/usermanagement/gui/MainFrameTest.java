package ua.nure.kn.sadurska.usermanagement.gui;

import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.sadurska.usermanagement.User;
import ua.nure.kn.sadurska.usermanagement.db.DaoFactory;
import ua.nure.kn.sadurska.usermanagement.db.MockDaoFactory;
import ua.nure.kn.sadurska.usermanagement.db.MockUserDao;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class MainFrameTest extends JFCTestCase {

    private MainFrame mainFrame;
    private Mock mockUserDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        try {
            final Properties properties = new Properties();
            properties.setProperty("dao.factory", MockDaoFactory.class.getName());
            properties.setProperty("dao.ua.nure.kn.sadurska.usermanagement.db.UserDao", MockUserDao.class.getName());
            DaoFactory.init(properties);
            mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
            mockUserDao.expectAndReturn("findAll", new ArrayList<User>());
            setHelper(new JFCTestHelper());
            mainFrame = new MainFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainFrame.setVisible(true);
    }

    @Override
    protected void tearDown() throws Exception {
        try {
            mockUserDao.verify();
            mainFrame.setVisible(false);
            TestHelper.cleanUp(this);
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Component find(final Class componentClass, final String name) {
        final NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        final Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component '" + name + "' ", component);
        return component;
    }

    // Tests

    public void testBrowseControls() {
        find(JPanel.class, "browsePanel");
        final JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(3, table.getColumnCount());
        assertEquals("ID", table.getColumnName(0));
        assertEquals("First Name", table.getColumnName(1));
        assertEquals("Last Name", table.getColumnName(2));

        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "detailsButton");
    }

    public void testAddUser() {
        final String firstName = "John";
        final String lastName = "Doe";
        final Date dateOfBirth = new Date();

        final User user = new User(null, firstName, lastName, dateOfBirth);
        final User expectedUser = new User(1L, firstName, lastName, dateOfBirth);
        mockUserDao.expectAndReturn("create", user, expectedUser);

        final ArrayList<User> users = new ArrayList<>();
        users.add(expectedUser);
        mockUserDao.expectAndReturn("findAll", users);

        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(0, table.getRowCount());
        final JButton addButton = (JButton) find(JButton.class, "addButton");

        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "addPanel");

        final JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        final JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        final JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");

        final JButton okButton = (JButton) find(JButton.class, "okButton");
        final JButton cancelButton = (JButton) find(JButton.class, "cancelButton");

        getHelper().sendString(new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(new StringEventData(this, lastNameField, lastName));
        getHelper().sendString(new StringEventData(this, dateOfBirthField, DateFormat.getDateInstance().format(dateOfBirth)));

        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "browsePanel");
        table = (JTable) find(JTable.class, "userTable");
        assertEquals(1, table.getRowCount());
    }
}
