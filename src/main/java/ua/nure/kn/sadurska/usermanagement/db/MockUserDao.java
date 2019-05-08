package ua.nure.kn.sadurska.usermanagement.db;

import ua.nure.kn.sadurska.usermanagement.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockUserDao implements UserDao {

    private long id = 0;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User create(final User user) {
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User find(final Long id) {
        return users.get(id);
    }

    @Override
    public void update(final User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(final User user) {
        users.remove(user.getId());
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }
}
