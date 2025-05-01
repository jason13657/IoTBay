package dao.stub;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dao.interfaces.UserDAO;
import model.User;

public class UserDAOStub implements UserDAO {
    private final List<User> users = new ArrayList<>();

    public UserDAOStub() {
        // Sample dummy data (User 생성자 파라미터 순서에 맞춤)
        users.add(new User(
                1,
                "john.doe@example.com",
                "John",
                "Doe",
                "password123",
                "010-1111-2222", // phone
                "Male",
                "Blue",
                LocalDate.of(1990, 1, 1),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "staff",
                true
        ));
        users.add(new User(
                2,
                "jane.smith@example.com",
                "Jane",
                "Smith",
                "password456",
                "010-3333-4444", // phone
                "Female",
                "Red",
                LocalDate.of(1992, 5, 15),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "customer",
                true
        ));
    }

    @Override
    public void createUser(User user) throws SQLException {
        int newId = users.isEmpty() ? 1 : users.get(users.size() - 1).getId() + 1;
        User newUser = new User(
                newId,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getPhone(),
                user.getGender(),
                user.getFavoriteColor(),
                user.getDateOfBirth(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                user.getRole(),
                user.isActive()
        );
        users.add(newUser);
    }

    @Override
    public User getUserById(int id) throws SQLException {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return new ArrayList<>(users);
    }

    @Override
    public List<User> getUsersByEmail(String email) throws SQLException {
        return users.stream()
                .filter(user -> user.getEmail().contains(email))
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(int id, User updatedUser) throws SQLException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, new User(
                        id,
                        updatedUser.getEmail(),
                        updatedUser.getFirstName(),
                        updatedUser.getLastName(),
                        updatedUser.getPassword(),
                        updatedUser.getPhone(),
                        updatedUser.getGender(),
                        updatedUser.getFavoriteColor(),
                        updatedUser.getDateOfBirth(),
                        users.get(i).getCreatedAt(),
                        LocalDateTime.now(),
                        updatedUser.getRole(),
                        updatedUser.isActive()
                ));
                return;
            }
        }
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        return users.removeIf(user -> user.getId() == id);
    }

    @Override
    public boolean isEmailExists(String email) throws SQLException {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }
}
