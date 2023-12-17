package pl.szudor.event

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.szudor.exception.EventAlreadyExistsException
import pl.szudor.exception.EventNotExistsException
import pl.szudor.film.FilmRepository
import pl.szudor.film.requireById
import pl.szudor.repertoire.RepertoireRepository
import pl.szudor.repertoire.requireById
import pl.szudor.room.RoomRepository
import pl.szudor.room.requireById
import java.time.LocalTime

interface EventService {
    fun create(repertoireId: Int, filmId: Int, roomId: Int, playedAt: LocalTime): Event
    fun fetchByFilter(filter: EventFilter, request: Pageable): Page<Event>
    fun patch(repertoireId: Int, filmId: Int, roomId: Int, oldPlayedAt: LocalTime, newPlayedAt: LocalTime): Event
    fun delete(repertoireId: Int, filmId: Int, roomId: Int, playedAt: LocalTime)
}

@Service
@Transactional
class EventServiceImpl(
    private val eventFactory: EventFactory,
    private val eventRepository: EventRepository,
    private val repertoireRepository: RepertoireRepository,
    private val filmRepository: FilmRepository,
    private val roomRepository: RoomRepository,
) : EventService {
    override fun create(repertoireId: Int, filmId: Int, roomId: Int, playedAt: LocalTime): Event =
        if (eventRepository.findByRepertoireAndPlayedAt(repertoireId, playedAt) != null)
            throw EventAlreadyExistsException(playedAt)
        else eventRepository.save(
            eventFactory.createEvent(
                repertoireRepository.requireById(repertoireId),
                filmRepository.requireById(filmId),
                roomRepository.requireById(roomId),
                playedAt
            )
        )

    override fun fetchByFilter(filter: EventFilter, request: Pageable): Page<Event> =
        eventRepository.fetchByFilter(filter, request)

    override fun patch(
        repertoireId: Int,
        filmId: Int,
        roomId: Int,
        oldPlayedAt: LocalTime,
        newPlayedAt: LocalTime,
    ): Event =
        if (eventRepository.findByPlayedAt(repertoireId, filmId, roomId, newPlayedAt) != null)
            throw EventAlreadyExistsException(oldPlayedAt)
        else eventRepository.findByPlayedAt(repertoireId, filmId, roomId, oldPlayedAt)?.apply { playedAt = newPlayedAt }
            ?: throw EventNotExistsException(repertoireId, filmId, roomId)

    override fun delete(repertoireId: Int, filmId: Int, roomId: Int, playedAt: LocalTime) =
        eventRepository.remove(repertoireId, filmId, roomId, playedAt)
}