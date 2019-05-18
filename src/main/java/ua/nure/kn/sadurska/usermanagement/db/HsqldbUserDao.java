package ua.nure.kn.sadurska.usermanagement.db;

import ua.nure.kn.sadurska.usermanagement.User;

import java.sql.*;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

class HsqldbUserDao implements UserDao {

    private static final String INSERT_USER = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_USERS = "SELECT id, firstname, lastname, dateofbirth from users";
    private static final String FIND_USER_BY_ID = "SELECT id, firstname, lastname, dateofbirth from users WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_BY_NAMES = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE firstname = ? AND lastname = ?";

    private ConnectionFactory connectionFactory;

    public HsqldbUserDao(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public HsqldbUserDao() {

    }

    @Override
    public User create(final User user) throws DatabaseException {
        try {
            final Connection connection = connectionFactory.createConnection();
            final PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            final int n = statement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Number of the inserted rows: " + n);
            }
            final CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
            final ResultSet keys = callableStatement.executeQuery();
            if (keys.next()) {
                user.setId(keys.getLong(1));
            }
            keys.close();
            callableStatement.close();
            statement.close();
            connection.close();
            return user;
        } catch (final Exception e) {
            throw new DatabaseException(e.getLocalizedMessage());
        }
    }

    @Override
    public User find(final Long id) {
        try {
            final Connection connection = connectionFactory.createConnection();
            final PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID);
            statement.setLong(1, id);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                final User user = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4));
                resultSet.close();
                statement.close();
                connection.close();
                return user;
            } else {
                throw new RuntimeException("No user with such id");
            }
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(final User user) {
        try {
            final Connection connection = connectionFactory.createConnection();
            final PreparedStatement statement = connection.prepareStatement(DELETE_USER);
            statement.setLong(1, user.getId());
            final int n = statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(final User user) {
        try {
            final Connection connection = connectionFactory.createConnection();
            final PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            statement.setLong(4, user.getId());
            final int n = statement.executeUpdate();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        try {
            final Collection<User> result = new LinkedList<>();
            final Connection connection = connectionFactory.createConnection();
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);
            while (resultSet.next()) {
                final User user = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4));
                result.add(user);
            }
            return result;
        } catch (final SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Collection<User> find(final String firstName, final String lastName) throws DatabaseException {
        try {
            final Collection<User> result = new LinkedList<>();
            final Connection connection = connectionFactory.createConnection();
            final PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAMES);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final User user = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4));
                result.add(user);
            }
            return result;
        } catch (final Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}
