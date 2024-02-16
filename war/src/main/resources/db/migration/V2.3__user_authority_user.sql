CREATE TABLE user_authority_user(
    id_user        INT    NOT NULL,
    id_authority   INT    NOT NULL,
    PRIMARY KEY (id_user, id_authority),
    FOREIGN KEY (id_user)      REFERENCES user(id),
    FOREIGN KEY (id_authority) REFERENCES user_authority(id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;