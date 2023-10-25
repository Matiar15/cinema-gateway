insert into cinema
       (name            , address	      , phone_number	 , postal_code		  ,  director	   , nip_code   , build_date, current_state, created_at    , email           )
values ( 'Kino Kowalska', 'Kowalskiej 12c', '+48-111-222-333', '12-345'           , 'Dylan Johns'  , '123456789', curdate() , 'ON'		   , current_time(), 'matwhat@wp.pl' );

insert into repertoire
        (played_at  , cinema_id, created_at         )
values  (curdate()  , 1        , current_timestamp());

insert into room
        (room_number, cinema_id, created_at         )
values  (12         ,         1, current_timestamp());

insert into film
        (played_at          , created_at          , original_language, duration, release_date,      pegi, title, room_id, repertoire_id)
values  (current_timestamp(), current_timestamp() , 'PL'             , 10      , '2023-03-03', 'SIXTEEN', 'ASD',       1,             1);

insert into seating
(seat_number, room_id, created_at                 )
values  (         13,       1, current_timestamp());

