CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  email TEXT NOT NULL,
  password TEXT NOT NULL,
  first_name TEXT,
  last_name TEXT,
  gender TEXT,
  favorite_color TEXT,
  date_of_birth TEXT,
  created_at TEXT,
  updated_at TEXT,
  role TEXT,
  is_active BOOLEAN
);