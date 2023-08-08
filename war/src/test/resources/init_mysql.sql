create table cinema (
    cinema_id int not null auto_increment,
    cinema_name varchar(50) not null,
    cinema_street varchar(50) not null,
    cinema_director varchar(50) not null,
    cinema_phone_number varchar(50) not null,
    cinema_postal_code varchar(50) not null,
    cinema_created_at timestamp not null,

    primary key (cinema_id)
);