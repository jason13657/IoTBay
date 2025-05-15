package dao.stubs;

import dao.interfaces.UserDAO;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOStub implements UserDAO {

    private final List<User> users = new ArrayList<>();
    private int idCounter = 1;

    @Override
    public void createUser(User user) throws SQLException {
        user.setUserId(idCounter++);
        users.add(user);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return new ArrayList<>(users);
    }

    @Override
    public User getUserById(int id) throws SQLException {
        return users.stream()
                .filter(user -> user.getUserId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsersByEmail(String email) throws SQLException {
        List<User> matching = new ArrayList<>();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                matching.add(user);
            }
        }
        return matching;
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateUser(int id, User updatedUser) throws SQLException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == id) {
                updatedUser.setUserId(id);
                users.set(i, updatedUser);
                return;
            }
        }
    }

    @Override
    public boolean deleteUserById(int id) throws SQLException {
        return users.removeIf(user -> user.getUserId() == id);
    }

    @Override
    public boolean isEmailExists(String email) throws SQLException {
        return users.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }
}
