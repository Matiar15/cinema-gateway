CREATE TABLE cinema (
                        id                      INT                 NOT NULL    AUTO_INCREMENT       PRIMARY KEY,
                        name                    VARCHAR(50)         NOT NULL,
                        email			        VARCHAR(50)			NOT NULL,
                        address                 VARCHAR(50)         NOT NULL,
                        phone_number            VARCHAR(50)         NOT NULL,
                        postal_code             VARCHAR(50)         NOT NULL,
                        director                VARCHAR(50)         NOT NULL,
                        nip_code                VARCHAR(50)         NOT NULL,
                        build_date              DATE                NOT NULL,
                        created_at              TIMESTAMP           NOT NULL,
                        current_state           ENUM('ON', 'OFF')   NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;