CREATE TABLE user (
    id                      INT                    NOT NULL    AUTO_INCREMENT,
    user_name               VARCHAR(60)            NOT NULL,
    password                VARCHAR(60)            NOT NULL,
    email                   VARCHAR(60)        DEFAULT NULL,
    locked                  ENUM('YES', 'NO')  DEFAULT 'NO',
    non_expired             ENUM('YES', 'NO')  DEFAULT 'NO',
    account_non_locked      ENUM('YES', 'NO')  DEFAULT 'NO',
    non_credentials_expired ENUM('YES', 'NO')  DEFAULT 'NO',
    enabled                 ENUM('YES', 'NO')  DEFAULT 'NO',
    UNIQUE (user_name),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;