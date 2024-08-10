CREATE TABLE IF NOT EXISTS users(
                                     user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     user_name VARCHAR(100) NOT NULL,
                                     age INT NOT NULL
);

CREATE TABLE IF NOT EXISTS notes(
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    user_id BIGINT NOT NULL,
                                    title VARCHAR(100) NOT NULL,
                                    content VARCHAR(1000) NOT NULL,
                                    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
