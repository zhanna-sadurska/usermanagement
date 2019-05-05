package ua.nure.kn.sadurska.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {

    public static final String USER_DAO = "dao.ua.nure.kn.sadurska.usermanagement.db.UserDao";
    private final Properties properties;

    private static final DaoFactory INSTANCE = new DaoFactory();

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    private DaoFactory() {
        try {
            properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ConnectionFactory getConnectionFactory() {
        final String user = properties.getProperty("connection.user");
        final String password = properties.getProperty("connection.password");
        final String url = properties.getProperty("connection.url");
        final String driver = properties.getProperty("connection.driver");
        return new ConnectionFactoryImpl(driver, url, user, password);
    }

    public UserDao getUserDao() {
        UserDao userDao = null;
        try {
            final Class clazz = Class.forName(properties.getProperty(USER_DAO));
            userDao = (UserDao) clazz.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        return userDao;
    }
}
