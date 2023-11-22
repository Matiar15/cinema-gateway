CREATE TABLE film (
                      id 					INT 				NOT NULL 	AUTO_INCREMENT 		PRIMARY KEY,
                      played_at 			TIME 				NOT NULL,
                      title 				VARCHAR(50) 		NOT NULL,
                      pegi 				    ENUM('three', 'seven', 'twelve', 'sixteen', 'eighteen') NOT NULL,
                      duration 			    INT 				NOT NULL,
                      release_date 		    DATE 				NOT NULL,
                      original_language 	VARCHAR(5)			NOT NULL,
                      created_at 			DATETIME			NOT NULL,
                      room_id 			    INT 				NOT NULL,
                      repertoire_id		    INT					NOT NULL,

                      FOREIGN KEY (room_id) REFERENCES room(id),
                      FOREIGN KEY (repertoire_id) REFERENCES repertoire(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

