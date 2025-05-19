CREATE TABLE User (
    userID INTEGER PRIMARY KEY AUTOINCREMENT,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL,
    phoneNumber TEXT,
    postalCode TEXT,
    addressLine1 TEXT,
    addressLine2 TEXT,
    dateOfBirth TEXT,        -- yyyy-MM-dd 형식 권장
    paymentMethod TEXT,
    createdAt TEXT DEFAULT (datetime('now')),
    updatedAt TEXT DEFAULT (datetime('now')),
    role TEXT NOT NULL CHECK(role IN ('customer', 'staff')) DEFAULT 'customer',
    isActive BOOLEAN NOT NULL DEFAULT 1
);
