CREATE TABLE user_authority(
    id           INT                 NOT NULL AUTO_INCREMENT,
    id_user      INT                 NOT NULL,
    authority    VARCHAR(60)         NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (id_user, authority),
    FOREIGN KEY (id_user) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;