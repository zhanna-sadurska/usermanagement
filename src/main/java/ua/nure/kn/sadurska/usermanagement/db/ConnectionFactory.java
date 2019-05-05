package ua.nure.kn.sadurska.usermanagement.db;

import java.sql.Connection;

public interface ConnectionFactory {

    Connection createConnection() throws DatabaseException;

}
