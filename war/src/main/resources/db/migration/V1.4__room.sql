create table room (
id 					    INT					NOT NULL	AUTO_INCREMENT		PRIMARY KEY,
id_cinema 			    INT					NOT NULL,
number    			    INT					NOT NULL,
created_at 			    DATETIME			NOT NULL,

FOREIGN KEY (id_cinema) REFERENCES cinema(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
