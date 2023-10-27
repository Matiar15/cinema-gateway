create table cinema (
                        id                      int                 not null    auto_increment       primary key,
                        name                    varchar(50)         not null,
                        email			        varchar(50)			not null,
                        address                 varchar(50)         not null,
                        phone_number            varchar(50)         not null,
                        postal_code             varchar(50)         not null,
                        director                varchar(50)         not null,
                        nip_code                varchar(50)         not null,
                        build_date              date                not null,
                        created_at              timestamp           not null,
                        current_state           enum('ON', 'OFF')   not null
);

create table repertoire (
                            id                      int                 not null    auto_increment      primary key,
                            played_at               date                not null,
                            cinema_id               int					not null,
                            created_at              datetime            not null,

                            foreign key (cinema_id) references cinema(id)
);

create table room (
                      id 					    int					not null	auto_increment		primary key,
                      room_number			    int					not null,
                      cinema_id 			    int					not null,
                      created_at 			    datetime			not null,

                      foreign key (cinema_id) references cinema(id)
);

create table film (
                      id 					    int 				not null 	auto_increment 		primary key,
                      played_at 			    time 				not null,
                      title 				    varchar(50) 		not null,
                      pegi 				        enum('THREE', 'SEVEN', 'TWELVE', 'SIXTEEN', 'EIGHTEEN') not null,
                      duration 			        int 				not null,
                      release_date 		        date 				not null,
                      original_language 	    varchar(5)			not null,
                      created_at 			    datetime			not null,
                      room_id 			        int 				not null,
                      repertoire_id		        int					not null,

                      foreign key (room_id) references room(id),
                      foreign key (repertoire_id) references repertoire(id)
);

create table seating (
                         id 					    int					not null	auto_increment		primary key,
                         seat_number			    int					not null,
                         room_id				    int					not null,
                         created_at 			    datetime			not null,
                         is_taken			        enum('YES', 'NO')	not null,

                         foreign key (room_id) references room(id)
);

create table person (
                        id 					    int 				not null 	auto_increment 		primary key,
                        first_name			    varchar(50) 		not null,
                        last_name			    varchar(50) 		not null,
                        birth_date			    date				not null,
                        occupation			    varchar(50)			not null
);