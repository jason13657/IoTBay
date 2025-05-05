CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  email TEXT NOT NULL UNIQUE,         -- 사용자 이메일(로그인 ID)
  password TEXT NOT NULL,             -- 해시된 비밀번호
  type TEXT NOT NULL DEFAULT 'user',  -- 사용자 유형(user, staff, admin 등)
  is_active BOOLEAN NOT NULL DEFAULT 1, -- 계정 활성 상태(1: 활성, 0: 비활성)
  created_at TEXT NOT NULL DEFAULT (datetime('now')), -- 생성일시
  updated_at TEXT NOT NULL DEFAULT (datetime('now'))  -- 수정일시
);
