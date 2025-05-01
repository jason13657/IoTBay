CREATE TABLE card_detail (
    card_id INT, 
    user_id INT, 
    card_name INT, 
    card_holder_name CHAR, 
    exp_date DATE, 
    CVV INT, 
    PRIMARY KEY (card_id), 
    FOREIGN KEY (user_id) REFERENCES usr(user_id)
);