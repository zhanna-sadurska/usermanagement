package ua.nure.kn.sadurska.usermanagement.db;

import ua.nure.kn.sadurska.usermanagement.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockUserDao implements UserDao {

    private long id = 0;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User create(final User user) throws DatabaseException {
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User find(final Long id) throws DatabaseException {
        return users.get(id);
    }

    @Override
    public void update(final User user) throws DatabaseException {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(final User user) throws DatabaseException {
        users.remove(user.getId());
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        return users.values();
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }
}
