CREATE TABLE repertoire_film (
id                      INT                 NOT NULL    AUTO_INCREMENT		PRIMARY KEY,
id_repertoire			INT					NOT NULL,
id_film			        INT					NOT NULL,
created_at 			    DATETIME			NOT NULL,

FOREIGN KEY (id_repertoire) REFERENCES repertoire(id),
FOREIGN KEY (id_film)       REFERENCES film(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
