CREATE TABLE orders (
  order_id INT AUTO_INCREMENT PRIMARY KEY,
  customer_id INT, -- nullable for anonymous users or link to user table
  order_date DATETIME,
  status VARCHAR(20), -- e.g., 'saved', 'submitted'
  total_price DECIMAL(10,2),
  -- other fields like shipping info if needed
  FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);