package pl.szudor.event

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import pl.szudor.film.FilmRepository
import pl.szudor.film.QFilm
import pl.szudor.repertoire.QRepertoire
import pl.szudor.repertoire.RepertoireRepository
import pl.szudor.room.QRoom
import java.time.LocalTime

interface EventRepository : JpaRepository<Event, EventKey>, EventCustomRepository

interface EventCustomRepository {
    fun findByRepertoireAndPlayedAt(repertoireId: Int, playedAt: LocalTime): Event?
    fun findByPlayedAt(repertoireId: Int, filmId: Int, roomId: Int, playedAt: LocalTime): Event?
    fun fetchByFilter(filter: EventFilter, request: Pageable): Page<Event>
    fun asPredicate(filter: EventFilter, root: QEvent): Predicate?
    fun remove(repertoireId: Int, filmId: Int, roomId: Int, playedAt: LocalTime)
    fun removeByFilm(filmId: Int)
}

class EventRepositoryImpl(
    private val repertoireRepository: RepertoireRepository,
    private val filmRepository: FilmRepository,
) : QuerydslRepositorySupport(Event::class.java), EventCustomRepository {
    override fun findByRepertoireAndPlayedAt(repertoireId: Int, playedAt: LocalTime): Event? {
        val root = QEvent.event

        val repertoire = QRepertoire.repertoire
        val film = QFilm.film
        val room = QRoom.room

        return from(root)
            .join(root.repertoire, repertoire).fetchJoin()
            .join(root.film, film).fetchJoin()
            .join(root.room, room).fetchJoin()
            .where(repertoire.id.eq(repertoireId))
            .where(root.playedAt.eq(playedAt))
            .fetchFirst()
    }

    override fun findByPlayedAt(repertoireId: Int, filmId: Int, roomId: Int, playedAt: LocalTime): Event? {
        val root = QEvent.event

        val repertoire = QRepertoire.repertoire
        val film = QFilm.film
        val room = QRoom.room

        return from(root)
            .join(root.repertoire, repertoire).fetchJoin()
            .join(root.film, film).fetchJoin()
            .join(root.room, room).fetchJoin()
            .where(repertoire.id.eq(repertoireId))
            .where(film.id.eq(filmId))
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
            .and(filter.playedAt?.let { root.playedAt.between(it.lowerEndpoint(), it.upperEndpoint()) })
            .and(filter.film?.let { filmRepository.asPredicate(root.film, it) })
            .and(filter.repertoire?.let { repertoireRepository.asPredicate(root.repertoire, it) })
            .value

    override fun remove(repertoireId: Int, filmId: Int, roomId: Int, playedAt: LocalTime) {
        val root = QEvent.event

        delete(root)
            .where(root.repertoire.id.eq(repertoireId))
            .where(root.film.id.eq(filmId))
            .where(root.room.id.eq(roomId))
            .where(root.playedAt.eq(playedAt))
            .execute()
    }

    override fun removeByFilm(filmId: Int) {
        val root = QEvent.event

        delete(root)
            .where(root.film.id.eq(filmId))
    }
}