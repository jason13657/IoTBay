package dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import model.User;

public interface UserDAO {
    void createUser(User user) throws SQLException;
    User getUserById(int id) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    List<User> getUsersByEmail(String email) throws SQLException;
    void updateUser(int id, User user) throws SQLException;
    void deleteUser(int id) throws SQLException;
}
