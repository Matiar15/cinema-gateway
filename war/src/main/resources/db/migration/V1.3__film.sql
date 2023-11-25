CREATE TABLE film (
id 					    INT 				                                    NOT NULL 	AUTO_INCREMENT 		PRIMARY KEY,
room_number			    INT 				                                    NOT NULL,
played_at 			    TIME 				                                    NOT NULL,
title 				    VARCHAR(50) 		                                    NOT NULL,
pegi 				    ENUM('THREE', 'SEVEN', 'TWELVE', 'SIXTEEN', 'EIGHTEEN') NOT NULL,
duration 			    INT 				                                    NOT NULL,
release_date 		    DATE 				                                    NOT NULL,
original_language 	    VARCHAR(5)			                                    NOT NULL,
created_at 			    DATETIME			                                    NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

