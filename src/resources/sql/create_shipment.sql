CREATE TABLE Shipment (
    shipmentId INT PRIMARY KEY AUTO_INCREMENT,
    orderId INT,
    trackingNumber VARCHAR(100),
    carrier VARCHAR(100),
    status VARCHAR(50),
    address VARCHAR(255),
    estimatedDelivery DATE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (orderId) REFERENCES `Order`(orderId)
);