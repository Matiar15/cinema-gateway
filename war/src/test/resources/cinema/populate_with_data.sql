INSERT INTO cinema
       (id,     name            , address	      , phone_number	 , postal_code		  ,  director	   , nip_code   , build_date, is_active, created_at    , email           )
VALUES ( 1,     'Kino Kowalska' , 'Kowalskiej 12c', '+48-111-222-333', '12-345'           , 'Dylan Johns'  , '123456789', curdate() , 'YES'	   , current_time(), 'matwhat@wp.pl' );