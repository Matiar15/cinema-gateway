package pl.szudor.event

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.support.PageableExecutionUtils
import pl.szudor.exception.EventNotExistsException
import pl.szudor.film.FilmRepository
import pl.szudor.film.QFilm
import pl.szudor.repertoire.QRepertoire
import pl.szudor.repertoire.RepertoireRepository
import pl.szudor.room.QRoom
import pl.szudor.utils.between
import java.time.LocalTime

interface EventRepository : JpaRepository<Event, Int>, EventCustomRepository

fun EventRepository.requireById(id: Int) = findByIdOrNull(id) ?: throw EventNotExistsException(id)

interface EventCustomRepository {
    fun findByRepertoireAndRoomAndPlayedAt(repertoireId: Int, roomId: Int, playedAt: LocalTime): Event?
    fun fetchByFilter(filter: EventFilter, request: Pageable): Page<Event>
    fun asPredicate(filter: EventFilter, root: QEvent): Predicate?
    fun removeByFilm(filmId: Int)
}


class EventRepositoryImpl(
    private val repertoireRepository: RepertoireRepository,
    private val filmRepository: FilmRepository,
) : QuerydslRepositorySupport(Event::class.java), EventCustomRepository {
    override fun findByRepertoireAndRoomAndPlayedAt(repertoireId: Int, roomId: Int, playedAt: LocalTime): Event? {
        val root = QEvent.event

        val repertoire = QRepertoire.repertoire
        val film = QFilm.film
        val room = QRoom.room

        return from(root)
            .join(root.repertoire, repertoire).fetchJoin()
            .join(root.film, film).fetchJoin()
            .join(root.room, room).fetchJoin()
            .where(repertoire.id.eq(repertoireId))
            .where(room.id.eq(roomId))
            .where(root.playedAt.eq(playedAt))
            .fetchFirst()
    }

    override fun fetchByFilter(filter: EventFilter, request: Pageable): Page<Event> {
        val root = QEvent.event

        val repertoire = QRepertoire.repertoire
        val film = QFilm.film
        val room = QRoom.room

        var query = from(root)
            .join(root.repertoire, repertoire).fetchJoin()
            .join(root.film, film).fetchJoin()
            .join(root.room, room).fetchJoin()
            .where(asPredicate(filter, root))

        query = querydsl!!.applyPagination(request, query)
        return PageableExecutionUtils.getPage(query.fetch(), request, query::fetchCount)
    }

    override fun asPredicate(filter: EventFilter, root: QEvent): Predicate? =
        BooleanBuilder()
            .and(filter.playedAt?.between(root.playedAt))
            .and(filter.film?.let { filmRepository.asPredicate(root.film, it) })
            .and(filter.repertoire?.let { repertoireRepository.asPredicate(root.repertoire, it) })
            .value

    override fun removeByFilm(filmId: Int) {
        val root = QEvent.event

        delete(root)
            .where(root.film.id.eq(filmId))
            .execute()
    }
}