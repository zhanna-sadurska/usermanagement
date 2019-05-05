package ua.nure.kn.sadurska.usermanagement.db;

public class DaoFactoryImpl extends DaoFactory {

    @Override
    public UserDao getUserDao() {
        try {
            final Class clazz = Class.forName(properties.getProperty(USER_DAO));
            final UserDao userDao = (UserDao) clazz.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
            return userDao;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
