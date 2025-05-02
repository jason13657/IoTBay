CREATE TABLE user_profiles (
  user_id INTEGER PRIMARY KEY,
  first_name TEXT,
  last_name TEXT,
  gender TEXT,
  favorite_color TEXT,
  date_of_birth TEXT,
  
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE 
  --- ON UPDATE CASCADE
  -- ON DELETE SET NULL, -- 사용자가 삭제될 때 프로필도 삭제
  
);
