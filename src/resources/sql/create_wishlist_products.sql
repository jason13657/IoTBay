CREATE TABLE Wishlist_Product (
    wishlistId INT,
    productId INT,
    quantity INT DEFAULT 1,
    PRIMARY KEY (wishlistId, productId),
    FOREIGN KEY (wishlistId) REFERENCES Wishlist(wishlistId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);