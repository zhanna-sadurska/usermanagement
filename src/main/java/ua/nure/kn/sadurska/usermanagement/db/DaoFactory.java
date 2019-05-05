package ua.nure.kn.sadurska.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {

    protected static final String USER_DAO = "dao.ua.nure.kn.sadurska.usermanagement.db.UserDao";
    private static final String DAO_FACTORY = "dao.factory";
    protected static Properties properties = new Properties();

    private static DaoFactory INSTANCE;

    static {
        try {
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized DaoFactory getInstance() {
        try {
            if (INSTANCE == null) {
                final Class factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                INSTANCE = (DaoFactory) factoryClass.newInstance();
            }
            return INSTANCE;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected DaoFactory() {
    }

    public static void init(final Properties properties) {
        DaoFactory.properties = properties;
        INSTANCE = null;
    }

    public ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImpl(properties);
    }

    public abstract UserDao getUserDao();
}
