package pl.szudor.repertoirefilmroom

import pl.szudor.film.Film
import pl.szudor.repertoire.Repertoire
import pl.szudor.room.Room
import javax.persistence.*

@Entity
@Table(name = "repertoire_film_room")
class RepertoireFilmRoom(
    @field:EmbeddedId
    var id: Key? = null,

    @ManyToOne
    @MapsId("repertoireId")
    @JoinColumn(name = "id_repertoire")
    var repertoire: Repertoire? = null,

    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "id_film")
    var film: Film? = null,

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "id_room")
    var room: Room? = null
)
