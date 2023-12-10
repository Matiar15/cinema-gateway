package pl.szudor.repertoire

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import pl.szudor.cinema.QCinema
import pl.szudor.exception.RepertoireNotExistsException
import java.time.LocalDate

interface RepertoireRepository : JpaRepository<Repertoire, Int>, RepertoireCustomRepository

fun RepertoireRepository.requireById(id: Int): Repertoire =
    this.findByIdOrNull(id) ?: throw RepertoireNotExistsException(id)

interface RepertoireCustomRepository {
    fun fetchByFilter(cinemaId: Int, filter: RepertoireFilter, pageRequest: Pageable): Page<Repertoire>

    fun findOneByPlayedAt(playedAt: LocalDate): Repertoire?
    fun asPredicate(root: QRepertoire, filter: RepertoireFilter): Predicate?
}

@Repository
class RepertoireCustomRepositoryImpl : RepertoireCustomRepository, QuerydslRepositorySupport(Repertoire::class.java) {
    override fun fetchByFilter(cinemaId: Int, filter: RepertoireFilter, pageRequest: Pageable): Page<Repertoire> {
        val root = QRepertoire.repertoire
        val cinema = QCinema.cinema
        var query = from(QRepertoire.repertoire)
            .join(root.cinema, cinema).fetchJoin()
            .where(cinema.id.eq(cinemaId))
            .where(asPredicate(root, filter))

        query = querydsl!!.applyPagination(pageRequest, query)
        return PageableExecutionUtils.getPage(query.fetch(), pageRequest, query::fetchCount)
    }

    override fun findOneByPlayedAt(playedAt: LocalDate): Repertoire? {
        val root = QRepertoire.repertoire
        return from(root)
            .where(root.playedAt.eq(playedAt))
            .fetchFirst()
    }

    override fun asPredicate(root: QRepertoire, filter: RepertoireFilter) =
        BooleanBuilder()
            .and(filter.playedAt?.let { root.playedAt.between(it.lowerEndpoint(), it.upperEndpoint()) })
            .value
}