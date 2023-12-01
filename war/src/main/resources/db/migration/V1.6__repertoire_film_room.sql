CREATE TABLE repertoire_film_room (
id_repertoire               INT                 NOT NULL,
id_film                     INT                 NOT NULL,
id_room                     INT                 NOT NULL,

PRIMARY KEY (id_repertoire, id_room, id_film),
FOREIGN KEY (id_repertoire)           REFERENCES repertoire(id),
FOREIGN KEY (id_film)                 REFERENCES film(id),
FOREIGN KEY (id_room)                 REFERENCES room(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;