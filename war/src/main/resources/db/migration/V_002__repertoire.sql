CREATE TABLE repertoire (
                        id                      INT                 NOT NULL    AUTO_INCREMENT      PRIMARY KEY,
                        played_at               DATE                NOT NULL,
                        cinema_id               INT					NOT NULL,
                        created_at              DATETIME            NOT NULL,

                        FOREIGN KEY (cinema_id) REFERENCES cinema(id)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;