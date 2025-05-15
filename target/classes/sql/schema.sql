-- Users 테이블: 회원(고객/직원/관리자) 정보 저장
-- User table to store user information (customer/staff/admin)
CREATE TABLE Users (
    user_id      INT PRIMARY KEY AUTO_INCREMENT,
    full_name    VARCHAR(100) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    phone        VARCHAR(20),
    role         ENUM('customer', 'staff', 'admin') DEFAULT 'customer',
    status       ENUM('active', 'inactive') DEFAULT 'active',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products 테이블: IoT 디바이스(상품) 정보 저장
-- Products table to store IoT device (product) information
CREATE TABLE Products (
    product_id   INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(100) NOT NULL,
    type         ENUM('sensor', 'cable', 'battery', 'gateway') NOT NULL,
    price        DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    stock        INT NOT NULL CHECK (stock >= 0),
    description  TEXT,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- (선택) Orders 테이블: 주문 정보 저장 (외래키 포함)
-- (Optional) Orders table to store order information (with foreign key)
CREATE TABLE Orders (
    order_id     INT PRIMARY KEY AUTO_INCREMENT,
    user_id      INT NOT NULL,
    order_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status       ENUM('pending', 'processing', 'shipped', 'cancelled') DEFAULT 'pending',
    total        DECIMAL(10,2) NOT NULL CHECK (total >= 0),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);
