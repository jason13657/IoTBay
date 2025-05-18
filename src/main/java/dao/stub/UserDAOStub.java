package dao.stub;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dao.interfaces.UserDAO;
import jdk.jshell.spi.ExecutionControl;
import model.User;

public class UserDAOStub implements UserDAO {
    private final List<User> users = new ArrayList<>();

    public UserDAOStub() {
        users.add(new User(1, "john.doe@example.com", "password123", "John", "Doe", "+61 400 000 001", "2000", "1 George St", "Sydney NSW", LocalDate.of(1990, 1, 1), "Card", LocalDateTime.now(), LocalDateTime.now(), "staff", true));
        users.add(new User(2, "jane.smith@example.com", "password456", "Jane", "Smith", "+61 400 000 002", "2001", "2 Pitt St", "Sydney NSW", LocalDate.of(1992, 5, 15), "PayPal", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(3, "alice.johnson@example.com", "alicepass", "Alice", "Johnson", "+61 400 000 003", "2002", "3 Elizabeth St", "Sydney NSW", LocalDate.of(1985, 3, 10), "Bank", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(4, "bob.brown@example.com", "bobpass", "Bob", "Brown", "+61 400 000 004", "2003", "4 Castlereagh St", "Sydney NSW", LocalDate.of(1988, 7, 22), "Card", LocalDateTime.now(), LocalDateTime.now(), "staff", true));
        users.add(new User(5, "carol.wilson@example.com", "carolpass", "Carol", "Wilson", "+61 400 000 005", "2004", "5 Kent St", "Sydney NSW", LocalDate.of(1995, 12, 5), "PayPal", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(6, "dave.lee@example.com", "davepass", "Dave", "Lee", "+61 400 000 006", "2005", "6 Sussex St", "Sydney NSW", LocalDate.of(1991, 9, 18), "Bank", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(7, "eve.kim@example.com", "evepass", "Eve", "Kim", "+61 400 000 007", "2006", "7 Clarence St", "Sydney NSW", LocalDate.of(1993, 4, 30), "Card", LocalDateTime.now(), LocalDateTime.now(), "staff", true));
        users.add(new User(8, "frank.park@example.com", "frankpass", "Frank", "Park", "+61 400 000 008", "2007", "8 York St", "Sydney NSW", LocalDate.of(1987, 11, 2), "PayPal", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(9, "grace.choi@example.com", "gracepass", "Grace", "Choi", "+61 400 000 009", "2008", "9 Liverpool St", "Sydney NSW", LocalDate.of(1996, 6, 12), "Bank", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(10, "henry.lim@example.com", "henrypass", "Henry", "Lim", "+61 400 000 010", "2009", "10 Bathurst St", "Sydney NSW", LocalDate.of(1994, 8, 16), "Card", LocalDateTime.now(), LocalDateTime.now(), "staff", true));
        users.add(new User(11, "irene.yang@example.com", "irenepass", "Irene", "Yang", "+61 400 000 011", "2010", "11 Macquarie St", "Sydney NSW", LocalDate.of(1990, 2, 28), "PayPal", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(12, "jack.lee@example.com", "jackpass", "Jack", "Lee", "+61 400 000 012", "2011", "12 Oxford St", "Sydney NSW", LocalDate.of(1989, 10, 3), "Bank", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(13, "kate.kang@example.com", "katepass", "Kate", "Kang", "+61 400 000 013", "2012", "13 Regent St", "Sydney NSW", LocalDate.of(1997, 5, 21), "Card", LocalDateTime.now(), LocalDateTime.now(), "staff", true));
        users.add(new User(14, "leo.han@example.com", "leopass", "Leo", "Han", "+61 400 000 014", "2013", "14 Harris St", "Sydney NSW", LocalDate.of(1992, 12, 14), "PayPal", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(15, "mia.shin@example.com", "miapass", "Mia", "Shin", "+61 400 000 015", "2014", "15 Bourke St", "Sydney NSW", LocalDate.of(1996, 7, 8), "Bank", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(16, "nick.song@example.com", "nickpass", "Nick", "Song", "+61 400 000 016", "2015", "16 Riley St", "Sydney NSW", LocalDate.of(1986, 3, 25), "Card", LocalDateTime.now(), LocalDateTime.now(), "staff", true));
        users.add(new User(17, "olivia.jung@example.com", "oliviapass", "Olivia", "Jung", "+61 400 000 017", "2016", "17 Crown St", "Sydney NSW", LocalDate.of(1993, 11, 19), "PayPal", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(18, "paul.kim@example.com", "paulpass", "Paul", "Kim", "+61 400 000 018", "2017", "18 Goulburn St", "Sydney NSW", LocalDate.of(1991, 1, 13), "Bank", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        users.add(new User(19, "quinn.cho@example.com", "quinnpass", "Quinn", "Cho", "+61 400 000 019", "2018", "19 College St", "Sydney NSW", LocalDate.of(1988, 6, 27), "Card", LocalDateTime.now(), LocalDateTime.now(), "staff", true));
        users.add(new User(20, "ryan.hwang@example.com", "ryanpass", "Ryan", "Hwang", "+61 400 000 020", "2019", "20 King St", "Sydney NSW", LocalDate.of(1995, 9, 9), "PayPal", LocalDateTime.now(), LocalDateTime.now(), "customer", true));
        
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
    public void deleteUser(int id) throws SQLException {
        users.removeIf(user -> user.getId() == id);
    }
}
