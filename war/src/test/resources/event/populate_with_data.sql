INSERT INTO cinema
       (id,     name            , address	      , phone_number	 , postal_code		  ,  director	   , nip_code   , build_date, is_active, created_at    , email           )
VALUES ( 1,     'Kino Kowalska' , 'Kowalskiej 12c', '+48-111-222-333', '12-345'           , 'Dylan Johns'  , '123456789', curdate() , 'YES'	   , current_time(), 'matwhat@wp.pl' );

INSERT INTO repertoire
        (id,    played_at     , id_cinema, created_at         )
VALUES  ( 1,    '2023-12-12'  , 1        , current_timestamp());

INSERT INTO film
        (id, created_at          , original_language, duration, release_date,      pegi, title)
VALUES  ( 1, current_timestamp() , 'PL'             , 10      , '2023-03-03', 'SIXTEEN', 'ASD');

INSERT INTO room
        (id, id_cinema, created_at         , number)
VALUES  (1 ,         1, current_timestamp(), 12    );

INSERT INTO event
        (id_repertoire, id_film, id_room, played_at)
VALUES  (1            ,       1,       1,   '10:10');