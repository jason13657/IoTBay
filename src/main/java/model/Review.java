package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Review implements Serializable{
    private final int id;
    private final int userId;
    private final int productId;
    private int rating;
    private String comment;
    private LocalDateTime reviewedAt;

    public Review(int id, int userId, int productId, int rating, String comment, LocalDateTime reviewedAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.reviewedAt = reviewedAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public boolean isPositive() {
        return rating >= 4;
    }
}
