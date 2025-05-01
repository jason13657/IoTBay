CREATE TABLE usr (
    user_id INT NOT NULL, 
    user_name VARCHAR NOT NULL, 
    first_name CHAR NOT NULL, 
    last_name CHAR NOT NULL, 
    email VARCHAR NOT NULL, 
    passwrd VARCHAR NOT NULL, 
    gender CHAR NOT NULL, 
    favcol CHAR NOT NULL,
    dateOfBirth DATE,
    street_name CHAR, 
    suburb CHAR, 
    territory CHAR, 
    postcode INT, 
    PRIMARY KEY (user_id)
);