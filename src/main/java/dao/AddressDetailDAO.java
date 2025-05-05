package dao;

import model.AddressDetail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDetailDAO {
    private final Connection connection;

    public AddressDetailDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void createAddress(AddressDetail address) throws SQLException {
        String query = "INSERT INTO address_detail (user_id, recipient_name, street_address, city, state, postal_code, country, phone_number, is_default) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, address.getUserId());
            statement.setString(2, address.getRecipientName());
            statement.setString(3, address.getStreetAddress());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getState());
            statement.setString(6, address.getPostalCode());
            statement.setString(7, address.getCountry());
            statement.setString(8, address.getPhoneNumber());
            statement.setBoolean(9, address.isDefault());
            statement.executeUpdate();
        }
    }

    // READ by ID
    public AddressDetail getAddressById(int id) throws SQLException {
        String query = "SELECT * FROM address_detail WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    AddressDetail address = new AddressDetail(rs.getInt("id"), rs.getInt("user_id"));
                    address.setRecipientName(rs.getString("recipient_name"));
                    address.setStreetAddress(rs.getString("street_address"));
                    address.setCity(rs.getString("city"));
                    address.setState(rs.getString("state"));
                    address.setPostalCode(rs.getString("postal_code"));
                    address.setCountry(rs.getString("country"));
                    address.setPhoneNumber(rs.getString("phone_number"));
                    address.setDefault(rs.getBoolean("is_default"));
                    return address;
                }
            }
        }
        return null;
    }

    // UPDATE
    public void updateAddress(AddressDetail address) throws SQLException {
        String query = "UPDATE address_detail SET recipient_name = ?, street_address = ?, city = ?, state = ?, postal_code = ?, country = ?, phone_number = ?, is_default = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, address.getRecipientName());
            statement.setString(2, address.getStreetAddress());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getState());
            statement.setString(5, address.getPostalCode());
            statement.setString(6, address.getCountry());
            statement.setString(7, address.getPhoneNumber());
            statement.setBoolean(8, address.isDefault());
            statement.setInt(9, address.getId());
            statement.executeUpdate();
        }
    }

    // DELETE
    public void deleteAddress(int id) throws SQLException {
        String query = "DELETE FROM address_detail WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // READ All by userId
    public List<AddressDetail> getAddressesByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM address_detail WHERE user_id = ?";
        List<AddressDetail> addresses = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    AddressDetail address = new AddressDetail(rs.getInt("id"), rs.getInt("user_id"));
                    address.setRecipientName(rs.getString("recipient_name"));
                    address.setStreetAddress(rs.getString("street_address"));
                    address.setCity(rs.getString("city"));
                    address.setState(rs.getString("state"));
                    address.setPostalCode(rs.getString("postal_code"));
                    address.setCountry(rs.getString("country"));
                    address.setPhoneNumber(rs.getString("phone_number"));
                    address.setDefault(rs.getBoolean("is_default"));
                    addresses.add(address);
                }
            }
        }
        return addresses;
    }
}