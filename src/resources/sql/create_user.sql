CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  email TEXT NOT NULL,
  password TEXT NOT NULL,
  first_name TEXT,
  last_name TEXT,
  phone TEXT,
  postal_code TEXT,
  address_line1 TEXT,
  address_line2 TEXT,
  payment_method TEXT,
  date_of_birth TEXT,
  created_at TEXT,
  updated_at TEXT,
  role TEXT,
  is_active BOOLEAN
);
