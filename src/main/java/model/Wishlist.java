package model;

import java.io.Serializable;

public class Wishlist implements Serializable {
    private final int id;
    private final int userId;
    private String name;
    private String description;

    public Wishlist(int id, int userId, String name, String description) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWishlistInfo() {
        return "Wishlist [id=" + id + ", userId=" + userId + ", name=" + name + ", description=" + description + "]";
    }
}
