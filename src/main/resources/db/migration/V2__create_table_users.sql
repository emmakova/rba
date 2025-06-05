CREATE TABLE RBA.users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    oib VARCHAR(11) UNIQUE NOT NULL
);

CREATE INDEX idx_users_oib ON RBA.users(oib);