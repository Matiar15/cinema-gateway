CREATE TABLE repertoire (
id                      INT                 NOT NULL    AUTO_INCREMENT      PRIMARY KEY,
id_cinema               INT					NOT NULL,
played_at               DATE                NOT NULL,
created_at              DATETIME            NOT NULL,

FOREIGN KEY (id_cinema) REFERENCES cinema(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE repertoire ADD INDEX id_cinema_played_at_idx (id_cinema, played_at);