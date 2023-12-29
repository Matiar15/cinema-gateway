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
                        is_active               ENUM('YES', 'NO')   NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE repertoire (
                            id                      INT                 NOT NULL    AUTO_INCREMENT      PRIMARY KEY,
                            id_cinema               INT					NOT NULL,
                            played_at               DATE                NOT NULL,
                            created_at              DATETIME            NOT NULL,

                            FOREIGN KEY (id_cinema) REFERENCES cinema(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE repertoire ADD INDEX id_cinema_played_at_idx (id_cinema, played_at);

CREATE TABLE film (
                      id 					    INT 				                                    NOT NULL 	AUTO_INCREMENT 		PRIMARY KEY,
                      title 				    VARCHAR(50) 		                                    NOT NULL,
                      pegi 				        ENUM('THREE', 'SEVEN', 'TWELVE', 'SIXTEEN', 'EIGHTEEN') NOT NULL,
                      duration 			        INT 				                                    NOT NULL,
                      release_date 		        DATE 				                                    NOT NULL,
                      original_language 	    VARCHAR(5)			                                    NOT NULL,
                      created_at 			    DATETIME			                                    NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table room (
                      id 					    int					not null	auto_increment		primary key,
                      id_cinema 			    int					not null,
                      number    			    int					not null,
                      created_at 			    datetime			not null,

                      foreign key (id_cinema) references cinema(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE event (
                       id_repertoire               INT                 NOT NULL,
                       id_film                     INT                 NOT NULL,
                       id_room                     INT                 NOT NULL,
                       played_at                   TIME                NOT NULL,

                       PRIMARY KEY (id_repertoire, id_room, id_film),
                       FOREIGN KEY (id_repertoire)           REFERENCES repertoire(id),
                       FOREIGN KEY (id_film)                 REFERENCES film(id),
                       FOREIGN KEY (id_room)                 REFERENCES room(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE event ADD INDEX played_at_idx (played_at);

CREATE TABLE seat (
                      id 					        INT					NOT NULL	AUTO_INCREMENT		PRIMARY KEY,
                      id_room                       INT                 NOT NULL,
                      occupied                      ENUM('YES', 'NO')   NOT NULL,
                      number     			        INT					NOT NULL,
                      created_at 			        DATETIME            NOT NULL,

                      FOREIGN KEY (id_room)                 REFERENCES room(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;