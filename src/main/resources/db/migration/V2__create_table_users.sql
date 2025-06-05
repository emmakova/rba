CREATE TABLE RBA.users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    oib INT UNIQUE NOT NULL
);

CREATE INDEX idx_users_oib ON RBA.users(oib);