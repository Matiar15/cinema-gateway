# Cinema-Gateway
Two enthusiasts working in symbiosis in order to gain experience
## What is Cinema-Gateway?
Cinema-gateway is a cinema system, built mainly in Kotlin. Its purpose is to give us some experience on working in group.
Everything was done by [**Matiar15**](https://github.com/Matiar15) and [**SQD.exe**](https://github.com/SQDexe) 
## What do we want to accomplish?
We want to connect two applications - cinema-gateway and ~~how tf can we name the other app~~ by creating endpoints to let apps communicate with each other
## Endpoints 
### cinema package
[POST]  
- `/cinema` with correct body lets you create a record of a brand-new cinema

[GET]
- `/cinema` gets you a list of cinemas

[PUT]
- `/cinema/{id}` updates cinema with this `{id}` with new parameters
- `/cinema/state/{id}` changes the state of the cinema
### film package
[POST]
- `/film/repertoire/{repertoireId}/room/{roomId}` with correct body and path variables lets you create a record of a brand-new film

[GET]
- `/film` gets you a list of cinemas

[DELETE]
- `/film/{id}` deletes film with this `{id}`
### repertoire package
[POST]
- `/repertoire/cinema/{cinemaId}` with correct body and path variable lets you create a record of a brand-new repertoire

[GET]
- `/repertoire` gets you a list of repertoires

[DELETE]
- `/repertoire/{id}` deletes repertoire with this `{id}`
### room package
[POST]
- `/room/cinema/{cinemaId}` with correct body and path variable lets you create a record of a brand-new room

[PUT]
- `/room/{id}` updates room with this `{id}` with new room number

[DELETE]
- `/room/{id}` deletes room with this `{id}`
### seating package
[POST]
- `/seat/repertoire/{cinemaId}` with correct body and path variable lets you create a record of a brand-new seat

[PUT]
- `/seat/{id}` updates seat with this `{id}` with new room number

[DELETE]
- `/seat/{id}` deletes seat with this `{id}`