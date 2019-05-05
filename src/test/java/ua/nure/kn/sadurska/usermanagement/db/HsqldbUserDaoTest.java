package ua.nure.kn.sadurska.usermanagement.db;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Assert;
import org.junit.Before;
import ua.nure.kn.sadurska.usermanagement.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class HsqldbUserDaoTest extends DatabaseTestCase {

    private UserDao userDao;
    private ConnectionFactory connectionFactory;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl();
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new XmlDataSet(getClass().getClassLoader().getResourceAsStream("userDataSet.xml"));
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userDao = new HsqldbUserDao(connectionFactory);
    }

    public void testCreate() throws ParseException {
        try {
            final User user = new User();
            user.setFirstName("Zhanna");
            user.setLastName("Sadurska");
            user.setDateOfBirth(new SimpleDateFormat("dd/MM/yy").parse("22/06/86"));
            Assert.assertNull(user.getId());
            userDao.create(user);
            Assert.assertNotNull(user);
            Assert.assertNotNull(user.getId());
        } catch (final DatabaseException e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }

    public void testFind() {
        try {
            final User testUser = new User(null, "Test", "Test", new Date());
            userDao.create(testUser);
            Assert.assertNotNull(testUser.getId());
            final User foundUser = userDao.find(testUser.getId());
            Assert.assertNotNull(foundUser);
            Assert.assertEquals(testUser.getId(), foundUser.getId());
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }

    public void testDelete() {
        try {
            final User testUser = new User(null, "Test", "Test", new Date());
            userDao.create(testUser);
            Assert.assertNotNull(testUser.getId());
            userDao.delete(testUser);
            final User nullUser = userDao.find(testUser.getId());
            Assert.assertNull(nullUser);
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }

    public void testUpdate() {
        try {
            final User user = userDao.create(new User(null, "Test", "Test", new Date()));
            Assert.assertNotNull(user);
            Assert.assertNotNull(user.getId());
            final String NEW_NAME = "New Test";
            user.setFirstName(NEW_NAME);
            userDao.update(user);
            final User updatedUser = userDao.find(user.getId());
            Assert.assertNotNull(updatedUser);
            Assert.assertEquals(user.getId(), updatedUser.getId());
            Assert.assertEquals(updatedUser.getFirstName(), NEW_NAME);
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }

    public void testFindAll() {
        try {
            final Collection<User> all = userDao.findAll();
            assertNotNull("Collection is null", all);
            assertEquals("Collection size.", 2, all.size());
        } catch (final DatabaseException e) {
            fail(e.toString());
        }
    }
}
