CREATE TABLE repertoire (
id                      INT                 NOT NULL    AUTO_INCREMENT      PRIMARY KEY,
cinema_id               INT					NOT NULL,
played_at               DATE                NOT NULL,
created_at              DATETIME            NOT NULL,

FOREIGN KEY (cinema_id) REFERENCES cinema(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;