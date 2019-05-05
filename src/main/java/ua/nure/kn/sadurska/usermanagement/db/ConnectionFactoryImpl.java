package ua.nure.kn.sadurska.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public ConnectionFactoryImpl() {

    }

    @Override
    public Connection createConnection() throws DatabaseException {
        final String driverClass = "org.hsqldb.jdbcDriver";
        final String url = "jdbc:hsqldb:file:db/usermanagement";
        final String user = "sa";
        final String password = "";
        try {
            Class.forName(driverClass).newInstance();
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
