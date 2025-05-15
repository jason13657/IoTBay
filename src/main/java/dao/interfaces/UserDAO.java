package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import model.User;

public interface UserDAO {
    void createUser(User user) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    User getUserById(int id) throws SQLException;
    List<User> getUsersByEmail(String email) throws SQLException;
    User getUserByEmail(String email) throws SQLException; // updated 
    void updateUser(int id, User user) throws SQLException;
    boolean deleteUserById(int id) throws SQLException;
    boolean isEmailExists(String email) throws SQLException;
}
