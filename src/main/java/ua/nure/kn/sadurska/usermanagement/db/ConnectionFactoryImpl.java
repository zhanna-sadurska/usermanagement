package ua.nure.kn.sadurska.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private String driver;
    private String url;
    private String user;
    private String password;

    public ConnectionFactoryImpl(final String driver, final String url, final String user, final String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public ConnectionFactoryImpl(final Properties properties) {
        driver = properties.getProperty("connection.driver");
        url = properties.getProperty("connection.url");
        user = properties.getProperty("connection.user");
        password = properties.getProperty("connection.password");
    }

    public ConnectionFactoryImpl() {
        driver = "org.hsqldb.jdbcDriver";
        url = "jdbc:hsqldb:file:db/usermanagement";
        user = "sa";
        password = "";
    }

    @Override
    public Connection createConnection() throws DatabaseException {
        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }
}
