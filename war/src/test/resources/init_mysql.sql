CREATE TABLE cinema (
                        id                      INT                 NOT NULL    AUTO_INCREMENT       PRIMARY KEY,
                        name                    VARCHAR(50)         NOT NULL,
                        email			        VARCHAR(50)			NOT NULL,
                        address                 VARCHAR(50)         NOT NULL,
                        phone_number            VARCHAR(50)         NOT NULL,
                        postal_code             VARCHAR(50)         NOT NULL,
                        director                VARCHAR(50)         NOT NULL,
                        nip_code                VARCHAR(50)         NOT NULL,
                        build_date              DATE                NOT NULL,
                        created_at              TIMESTAMP           NOT NULL,
                        current_state           ENUM('on', 'off')   NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE repertoire (
                            id                      INT                 NOT NULL    AUTO_INCREMENT      PRIMARY KEY,
                            played_at               DATE                NOT NULL,
                            cinema_id               INT					NOT NULL,
                            created_at              DATETIME            NOT NULL,

                            FOREIGN KEY (cinema_id) REFERENCES cinema(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE room (
                      id 					    INT					NOT NULL	AUTO_INCREMENT		PRIMARY KEY,
                      room_number			    INT					NOT NULL,
                      cinema_id 			    INT					NOT NULL,
                      created_at 			    DATETIME			NOT NULL,

                      FOREIGN KEY (cinema_id) REFERENCES cinema(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE film (
                      id 					    INT 				NOT NULL 	AUTO_INCREMENT 		PRIMARY KEY,
                      played_at 			    TIME 				NOT NULL,
                      title 				    VARCHAR(50) 		NOT NULL,
                      pegi 				        ENUM('three', 'seven', 'twelve', 'sixteen', 'eighteen') NOT NULL,
                      duration 			        INT 				NOT NULL,
                      release_date 		        DATE 				NOT NULL,
                      original_language 	    VARCHAR(5)			NOT NULL,
                      created_at 			    DATETIME			NOT NULL,
                      room_id 			        INT 				NOT NULL,
                      repertoire_id		        INT					NOT NULL,

                      FOREIGN KEY (room_id) REFERENCES room(id),
                      FOREIGN KEY (repertoire_id) REFERENCES repertoire(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE seating (
                         id 					    INT					NOT NULL	AUTO_INCREMENT		PRIMARY KEY,
                         seat_number			    INT					NOT NULL,
                         room_id				    INT					NOT NULL,
                         created_at 			    DATETIME			NOT NULL,
                         is_taken			        ENUM('yes', 'no')	NOT NULL,

                         FOREIGN KEY (room_id) REFERENCES room(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE person (
                        id 					    INT 				NOT NULL 	AUTO_INCREMENT 		PRIMARY KEY,
                        first_name			    VARCHAR(50) 		NOT NULL,
                        last_name			    VARCHAR(50) 		NOT NULL,
                        birth_date			    DATE				NOT NULL,
                        occupation			    VARCHAR(50)			NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;