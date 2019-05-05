package ua.nure.kn.sadurska.usermanagement.db;

import junit.framework.TestCase;

public class DaoFactoryTest extends TestCase {

    public void testGetUserDao() {
        try {
            final DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull("DaoFactory instance is null", daoFactory);
            final UserDao userDao = daoFactory.getUserDao();
            assertNotNull("UserDao instace is null", userDao);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

}
