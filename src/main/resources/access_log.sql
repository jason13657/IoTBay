CREATE TABLE access_logs (
    log_id INTEGER,
    user_id INTEGER NOT NULL,
    action TEXT NOT NULL,
    timestamp TEXT NOT NULL,
    PRIMARY KEY (log_id),
    FOREIGN KEY (user_id) REFERENCES usr(user_id) ON DELETE CASCADE
);