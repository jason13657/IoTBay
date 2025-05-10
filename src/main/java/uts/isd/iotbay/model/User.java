package uts.isd.iotbay.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id; // Assuming ID is an int for simplicity, adjust if it's UUID/String
    private String email; // Used as username
    private String password;
    private String fullName;
    private String phone;
    private boolean isAdmin; // To differentiate between customer and staff/admin if needed by PaymentServlet logic

    // Default constructor
    public User() {
    }

    // Constructor with all fields
    public User(int id, String email, String password, String fullName, String phone, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.isAdmin = isAdmin;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + "'" +
                ", fullName='" + fullName + "'" +
                ", phone='" + phone + "'" +
                ", isAdmin=" + isAdmin +
                '}';
    }
} 