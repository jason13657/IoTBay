CREATE TABLE cart (
    cart_id INT NOT NULL,
    user_id INT NOT NULL,
    cart_item VARCHAR, 
    PRIMARY KEY (cart_id),
    FOREIGN KEY (user_id) REFERENCES usr(user_id)
);