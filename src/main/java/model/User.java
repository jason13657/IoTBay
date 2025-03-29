package model;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String email;
    private String name;
    private String password;
    private String gender;
    private String favoriteColor;

    public User() {
    }

    public User(String email, String name, String password, String gender, String favoriteColor) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.favoriteColor = favoriteColor;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFavoriteColor() {
        return this.favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(gender, user.gender) && Objects.equals(favoriteColor, user.favoriteColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, password, gender, favoriteColor);
    }

    @Override
    public String toString() {
        return "{" +
            " email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", password='" + getPassword() + "'" +
            ", gender='" + getGender() + "'" +
            ", favoriteColor='" + getFavoriteColor() + "'" +
            "}";
    }
    
}