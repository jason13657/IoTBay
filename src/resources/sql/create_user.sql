-- CREATE TABLE users (
--   id INTEGER PRIMARY KEY AUTOINCREMENT,
--   email TEXT NOT NULL,
--   password TEXT NOT NULL,
--   first_name TEXT,
--   last_name TEXT,
--   gender TEXT,
--   favorite_color TEXT,
--   date_of_birth TEXT,
--   created_at TEXT,
--   updated_at TEXT,
--   role TEXT,
--   is_active BOOLEAN
-- );


-- -- User
CREATE TABLE users (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phoneNumber VARCHAR(20),
    dateOfBirth DATE,
    role VARCHAR(20) DEFAULT 'user',
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);