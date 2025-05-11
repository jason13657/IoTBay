package uts.isd.iotbay.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class User implements Serializable {
    private int id; // Assuming ID is an int for simplicity, adjust if it's UUID/String
    private String email; // Used as username
    private String password;
    private String fullName; // firstName + lastName
    private String phone;
    private boolean isAdmin; // Can be derived from userType or kept separate

    // New fields based on welcome.jsp
    private String firstName;
    private String lastName;
    private String gender;
    private String favoriteColor; // favcol
    private LocalDate dateOfBirth;
    private LocalDateTime registrationDate; // First 'now'
    private LocalDateTime lastLoginDate;    // Second 'now'
    private String userType;             // e.g., "customer"

    // Default constructor
    public User() {
    }

    // Existing constructor (for PaymentServlet and potentially other uses)
    public User(int id, String email, String password, String fullName, String phone, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.isAdmin = isAdmin;
        // Sensible defaults for other fields if this constructor is used
        this.userType = isAdmin ? "admin" : "customer_basic"; // Example default
    }

    // New constructor for welcome.jsp
    public User(int id, String email, String firstName, String lastName, String password,
                String gender, String favoriteColor, LocalDate dateOfBirth,
                LocalDateTime registrationDate, LocalDateTime lastLoginDate, String userType) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = (firstName != null ? firstName : "") + (lastName != null ? " " + lastName : ""); // Combine first and last name
        this.password = password;
        this.gender = gender;
        this.favoriteColor = favoriteColor;
        this.dateOfBirth = dateOfBirth;
        this.registrationDate = registrationDate;
        this.lastLoginDate = lastLoginDate;
        this.userType = userType;
        this.isAdmin = "admin".equalsIgnoreCase(userType) || "staff".equalsIgnoreCase(userType); // Derive isAdmin from userType
        // phone is not set by this constructor, will be null or default
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
        // Ensure fullName is up-to-date if firstName or lastName are set independently
        if ((this.firstName != null || this.lastName != null) && 
            (this.fullName == null || !this.fullName.equals((this.firstName != null ? this.firstName : "") + (this.lastName != null ? " " + this.lastName : "")))) {
            this.fullName = (this.firstName != null ? this.firstName : "") + (this.lastName != null ? " " + this.lastName : "");
        }
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isAdmin() {
        // Optionally, derive isAdmin from userType if it's the source of truth
        // this.isAdmin = "admin".equalsIgnoreCase(userType) || "staff".equalsIgnoreCase(userType);
        return isAdmin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public String getUserType() {
        return userType;
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
        // If fullName is set directly, we might want to clear/update firstName and lastName,
        // or decide on a convention. For simplicity, not doing that now.
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
        // If isAdmin is set, we might want to update userType accordingly
        // if (admin && (this.userType == null || !this.userType.equals("admin"))) {
        //     this.userType = "admin";
        // } else if (!admin && (this.userType == null || this.userType.equals("admin"))) {
        //     this.userType = "customer";
        // }
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void setUserType(String userType) {
        this.userType = userType;
        // Also update isAdmin if userType changes
        this.isAdmin = "admin".equalsIgnoreCase(userType) || "staff".equalsIgnoreCase(userType);
    }

    @Override
    public String toString() {
        // Update toString to include new fields for better debugging
        return "User{" +
                "id=" + id +
                ", email='" + email + "'" +
                ", fullName='" + getFullName() + "'" +
                ", phone='" + phone + "'" +
                ", isAdmin=" + isAdmin +
                ", gender='" + gender + "'" +
                ", favoriteColor='" + favoriteColor + "'" +
                ", dateOfBirth=" + dateOfBirth +
                ", registrationDate=" + registrationDate +
                ", lastLoginDate=" + lastLoginDate +
                ", userType='" + userType + "'" +
                '}';
    }
} 