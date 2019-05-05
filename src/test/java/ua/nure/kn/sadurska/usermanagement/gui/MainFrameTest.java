package ua.nure.kn.sadurska.usermanagement.gui;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.sadurska.usermanagement.db.DaoFactory;
import ua.nure.kn.sadurska.usermanagement.db.DaoFactoryImpl;
import ua.nure.kn.sadurska.usermanagement.db.MockUserDao;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

public class MainFrameTest extends JFCTestCase {

    private MainFrame mainFrame;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        final Properties properties = new Properties();
        properties.setProperty("dao.ua.nure.kn.sadurska.usermanagement.db.UserDao", MockUserDao.class.getName());
        properties.setProperty("dao.factory", DaoFactoryImpl.class.getName());
        DaoFactory.getInstance().init(properties);

        setHelper(new JFCTestHelper());
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    @Override
    protected void tearDown() throws Exception {
        mainFrame.setVisible(false);
        TestHelper.cleanUp(this);
        super.tearDown();
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
        assertEquals("Имя", table.getColumnName(1));
        assertEquals("Фамилия", table.getColumnName(2));

        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "detailsButton");
    }

    public void testAddUser() {
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

        getHelper().sendString(new StringEventData(this, firstNameField, "John"));
        getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));
        getHelper().sendString(new StringEventData(this, dateOfBirthField, DateFormat.getDateInstance().format(new Date())));

        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "browsePanel");
        table = (JTable) find(JTextField.class, "userTable");
        assertEquals(1, table.getRowCount());
    }
}
