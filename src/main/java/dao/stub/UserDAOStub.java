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
        users.add(new User(1, "john.doe@example.com", "password123", "John", "Doe", "+61 400 000 001", "2000", "1 George St", "Sydney NSW", LocalDate.of(1990, 1, 1), "Card", LocalDateTime.now(), LocalDateTime.now(), "staff", true));
        users.add(new User(2, "jane.smith@example.com", "password456", "Jane", "Smith", "+61 400 000 002", "2001", "2 Pitt St", "Sydney NSW", LocalDate.of(1992, 5, 15), "PayPal", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
       
    }

    @Override
    public void createUser(User user) throws SQLException {
        int newId = users.isEmpty() ? 1 : users.get(users.size() - 1).getId() + 1;
        User newUser = new User(
                newId,
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getPostalCode(),
                user.getAddressLine1(),
                user.getAddressLine2(),
                user.getDateOfBirth(),
                user.getPaymentMethod(),
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
    public User getUserByEmail(String email) throws SQLException {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isEmailExists(String email) throws SQLException {
        return users.stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public void updateUser(int id, User updatedUser) throws SQLException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, new User(
                        id,
                        updatedUser.getEmail(),
                        updatedUser.getPassword(),
                        updatedUser.getFirstName(),
                        updatedUser.getLastName(),
                        updatedUser.getPhone(),
                        updatedUser.getPostalCode(),
                        updatedUser.getAddressLine1(),
                        updatedUser.getAddressLine2(),
                        updatedUser.getDateOfBirth(),
                        updatedUser.getPaymentMethod(),
                        users.get(i).getCreatedAt(), // 기존 생성일 유지
                        LocalDateTime.now(), // 수정일 갱신
                        updatedUser.getRole(),
                        updatedUser.isActive()
                ));
                return;
            }
        }
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        users.removeIf(user -> user.getId() == id);
    }
}
