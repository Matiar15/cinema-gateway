package pl.szudor.event

import org.springframework.stereotype.Service
import pl.szudor.film.Film
import pl.szudor.repertoire.Repertoire
import pl.szudor.room.Room
import java.time.LocalTime

interface EventFactory {
    fun createEvent(repertoire: Repertoire, film: Film, room: Room, playedAt: LocalTime): Event
}

@Service
class EventFactoryImpl : EventFactory {
    override fun createEvent(repertoire: Repertoire, film: Film, room: Room, playedAt: LocalTime): Event =
        Event().apply {
            this.repertoire = repertoire
            this.film = film
            this.room = room
            this.playedAt = playedAt
        }

}