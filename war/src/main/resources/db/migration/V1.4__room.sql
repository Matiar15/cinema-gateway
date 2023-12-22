create table room (
id 					    int					not null	auto_increment		primary key,
cinema_id 			    int					not null,
number    			    int					not null,
created_at 			    datetime			not null,

foreign key (cinema_id) references cinema(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
