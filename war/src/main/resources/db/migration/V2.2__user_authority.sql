CREATE TABLE user_authority(
    id           INT                 NOT NULL AUTO_INCREMENT,
    authority    VARCHAR(60)         NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;