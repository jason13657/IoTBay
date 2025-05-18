package model;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class User implements Serializable {
    private final int id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    private String phone;
    private String postalCode;
    private String addressLine1;
    private String addressLine2;
    private String paymentMethod;
    // private String gender;
    // we don't need a gender field for e-commerce website
    // private String favoriteColor;
    //we dont need a favorite color field for e-commerce website
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String role;
    private boolean isActive;

    public User(int id, String email, String password, String firstName, String lastName,
                String phone, String postalCode, String addressLine1, String addressLine2,
                LocalDate dateOfBirth, String paymentMethod,
                LocalDateTime createdAt, LocalDateTime updatedAt,
                String role, boolean isActive)  {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        // this.gender = gender;
        // this.favoriteColor = favoriteColor;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // public String getGender() {
    //     return gender;
    // }

    // public void setGender(String gender) {
    //     this.gender = gender;
    // }

    // public String getFavoriteColor() {
    //     return favoriteColor;
    // }

    // public void setFavoriteColor(String favoriteColor) {
    //     this.favoriteColor = favoriteColor;
    // }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getDateOfBirthAsString() {
        return dateOfBirth != null ? dateOfBirth.toString() : null;
    }

    public String getPostalCode() {
        // TODO Auto-generated method stub
        return postalCode;
    }

    public String getPhone() {
        // TODO Auto-generated method stub
        return phone;
    }


    public String getAddressLine1() {
        // TODO Auto-generated method stub
        return addressLine1;
    }

    public String getAddressLine2() {
        // TODO Auto-generated method stub
        return addressLine2;
    }

    public void setPostalCode(String postalCode) {
        // TODO Auto-generated method stub
        this.postalCode = postalCode;
    }
    public void setPhone(String phone) {
        // TODO Auto-generated method stub
        this.phone = phone;
    }
    public void setAddressLine1(String addressLine1) {
        // TODO Auto-generated method stub
        this.addressLine1 = addressLine1;
    }
    public void setAddressLine2(String addressLine2) {
        // TODO Auto-generated method stub
        this.addressLine2 = addressLine2;
    }
    public void setPaymentMethod(String paymentMethod) {
        // TODO Auto-generated method stub
        this.paymentMethod = paymentMethod;
    }
    

    public String getPaymentMethod() {
        // TODO Auto-generated method stub
        return paymentMethod;
    
    }
}