package ua.nure.kn.sadurska.usermanagement.db;

import ua.nure.kn.sadurska.usermanagement.User;

import java.util.Collection;

public interface UserDao {

    User create(final User user) throws DatabaseException;

    User find(final Long id) throws DatabaseException;

    void update(final User user) throws DatabaseException;

    void delete(final User user) throws DatabaseException;

    Collection<User> findAll() throws DatabaseException;

    Collection<User> find(final String firstName, final String lastName) throws DatabaseException;

    void setConnectionFactory(final ConnectionFactory connectionFactory);

}
