CREATE TABLE RBA.user_card_status (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    card_id INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    createdAt DATE NOT NULL,

CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES RBA.users(id),
CONSTRAINT fk_card FOREIGN KEY (card_id) REFERENCES RBA.cards(id)
    );
);
