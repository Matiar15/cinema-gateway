package pl.szudor.event

import pl.szudor.film.Film
import pl.szudor.repertoire.Repertoire
import pl.szudor.room.Room
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "event")
class Event {
    @EmbeddedId
    var id: EventKey? = null

    @ManyToOne
    @MapsId("repertoireId")
    @JoinColumn(name = "id_repertoire")
    var repertoire: Repertoire? = null

    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "id_film")
    var film: Film? = null

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "id_room")
    var room: Room? = null

    @Column
    var playedAt: LocalTime? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}