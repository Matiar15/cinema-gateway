insert into cinema
       (name         , 	address	   , phone_number	 , postal_code		  ,  director	   , nip_code  , build_date, current_state, created_at)
values ( 'nazwa_test', 'adres_test', 'numer_tel_test', 'kod_pocztowy_test', 'dyrektor_test', 'nip_test', curdate() , 'on'		  , current_time());

insert into repertoire
        (when_played, cinema_id, created_at)
values  (curdate()  , 1        , current_timestamp);

insert into film
        (played_at          , room_number, repertoire_id, created_at)
values  (current_timestamp(), 9			 , 1            , current_timestamp);