CREATE TABLE reserved_seat(
id_seat      INT      NOT NULL,
id_event     INT      NOT NULL,
created_at   DATETIME NOT NULL,
PRIMARY KEY (id_seat, id_event),
FOREIGN KEY (id_seat) REFERENCES seat(id),
FOREIGN KEY (id_event) REFERENCES event(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;