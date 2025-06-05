CREATE TABLE RBA.user_card_status (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES RBA.users(id)
);
