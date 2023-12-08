CREATE TABLE seat (
id 					        INT					NOT NULL	AUTO_INCREMENT		PRIMARY KEY,
id_room                     INT                 NOT NULL,
occupied                    ENUM('YES', 'NO')   NOT NULL,
number     			        INT					NOT NULL,
created_at 			        DATETIME            NOT NULL,

FOREIGN KEY (id_room)                 REFERENCES room(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;