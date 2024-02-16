INSERT INTO cinema
       (id,     name            , address	      , phone_number	 , postal_code		  ,  director	   , nip_code   , build_date, is_active, created_at    , email           )
VALUES ( 1,     'Kino Kowalska' , 'Kowalskiej 12c', '+48-111-222-333', '12-345'           , 'Dylan Johns'  , '123456789', curdate() , 'YES'	   , current_time(), 'matwhat@wp.pl' );

INSERT INTO room
        (id, id_cinema, created_at         , number)
VALUES  (1 ,         1, current_timestamp(), 12    );

INSERT INTO seat
        (id, id_room, created_at         , occupied, number)
VALUES  ( 1,       1, current_timestamp(), 'YES'   ,      2);