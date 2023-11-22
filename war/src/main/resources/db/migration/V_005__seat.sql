CREATE TABLE seating (
                         id 					INT					NOT NULL	AUTO_INCREMENT		PRIMARY KEY,
                         seat_number			INT					NOT NULL,
                         room_id				INT					NOT NULL,
                         created_at 			DATETIME			NOT NULL,
                         is_taken			    ENUM('yes', 'no')	NOT NULL,

                         FOREIGN KEY (room_id) REFERENCES room(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;