create table cinema (
id              int                 not null    auto_increment       primary key,
name            varchar(50)         not null,
email			varchar(50)			not null,
address         varchar(50)         not null,
phone_number    varchar(50)         not null,
postal_code     varchar(50)         not null,
director        varchar(50)         not null,
nip_code        varchar(50)         not null,
build_date      date                not null,
created_at      timestamp           not null,
current_state   enum('ON', 'OFF')   not null
);

create table repertoire (
    id              int                 not null    auto_increment      primary key,
    when_played     date                not null,
    cinema_id       int                 not null,
    created_at      datetime            not null,

    foreign key (cinema_id) references cinema(id)
);

create table film (
    id                int                 									  not null    auto_increment      primary key,
    played_at         time                									  not null,
    room_number       int                 									  not null,
    repertoire_id     int                 									  not null,
    title 		      varchar(50)		  									  not null,
    pegi			  enum('THREE', 'SEVEN', 'TWELVE', 'SIXTEEN', 'EIGHTEEN') not null,
    duration 	      int		   		  									  not null,
    release_date      date 			   	  									  not null,
    original_language varchar(50)	   	  									  not null,
    created_at        datetime            									  not null,
    foreign key (repertoire_id) references repertoire(id)
);