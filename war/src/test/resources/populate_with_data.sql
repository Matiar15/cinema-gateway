INSERT INTO cinema
       (name            , address	      , phone_number	 , postal_code		  ,  director	   , nip_code   , build_date, current_state, created_at    , email           )
VALUES ( 'Kino Kowalska', 'Kowalskiej 12c', '+48-111-222-333', '12-345'           , 'Dylan Johns'  , '123456789', curdate() , 'ON'		   , current_time(), 'matwhat@wp.pl' );

INSERT INTO repertoire
        (played_at  , cinema_id, created_at         )
VALUES  (curdate()  , 1        , current_timestamp());

INSERT INTO room
        (room_number, cinema_id, created_at         )
VALUES  (12         ,         1, current_timestamp());

INSERT INTO film
        (played_at          , created_at          , original_language, duration, release_date,      pegi, title, room_id, repertoire_id)
VALUES  (current_timestamp(), current_timestamp() , 'PL'             , 10      , '2023-03-03', 'SIXTEEN', 'ASD',       1,             1);

INSERT INTO seating
        (seat_number, room_id, created_at         , is_taken)
VALUES  (         13,       1, current_timestamp(), 'YES'   );

