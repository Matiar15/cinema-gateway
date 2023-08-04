create table cinema (
    cinema_id serial primary key,
    name varchar(50) not null,
    street varchar(50) not null,
    director varchar(50) not null,
    phone_number varchar(50) not null,
    postal_code varchar(50) not null,
    created_at timestamp not null
);