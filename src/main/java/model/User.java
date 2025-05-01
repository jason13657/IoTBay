package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * User 도메인 모델 클래스
 */
public class User implements Serializable {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    private String gender;
    private String favoriteColor;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String role;
    private boolean active;

    // 기본 생성자 (필수: JavaBean 규약)
    public User() {}

    // 전체 필드 생성자
    public User(int id, String email, String firstName, String lastName, String password,
                String phone, String gender, String favoriteColor, LocalDate dateOfBirth,
                LocalDateTime createdAt, LocalDateTime updatedAt, String role, boolean active) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.favoriteColor = favoriteColor;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.active = active;
    }

    // Getter/Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }
    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

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
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    // 유틸리티 메서드
    /**
     * 전체 이름 반환 (firstName + lastName)
     */
    public String getFullName() {
        return (firstName != null ? firstName : "") + 
               (lastName != null && !lastName.isEmpty() ? " " + lastName : "");
    }

    /**
     * 전체 이름을 firstName, lastName으로 분리하여 설정
     */
    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            this.firstName = "";
            this.lastName = "";
            return;
        }
        String[] names = fullName.trim().split("\\s+", 2);
        this.firstName = names[0];
        this.lastName = names.length > 1 ? names[1] : "";
    }

    /**
     * 계정 비활성화
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * 생년월일을 문자열로 반환 (null-safe)
     */
    public String getDateOfBirthAsString() {
        return dateOfBirth != null ? dateOfBirth.toString() : "";
    }
}
