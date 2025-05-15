CREATE TABLE Order_Product (
    orderId INT,
    productId INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (orderId, productId),
    FOREIGN KEY (orderId) REFERENCES `Order`(orderId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);