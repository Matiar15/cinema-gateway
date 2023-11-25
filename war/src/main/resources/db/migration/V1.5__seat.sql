CREATE TABLE seat (
id 					INT					NOT NULL	AUTO_INCREMENT		PRIMARY KEY,
id_film             INT                 NOT NULL,
number     			INT					NOT NULL,
created_at 			DATETIME            NOT NULL,
FOREIGN KEY (id_film) REFERENCES film(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;