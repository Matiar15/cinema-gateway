package pl.szudor.film

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import pl.szudor.cinema.prefixAndSuffix
import pl.szudor.exception.FilmNotExistsException

interface FilmRepository : JpaRepository<Film, Int>, FilmCustomRepository
fun FilmRepository.requireById(id: Int) = this.findByIdOrNull(id) ?: throw FilmNotExistsException(id)
interface FilmCustomRepository {
    fun fetchByFilter(filter: FilmFilter, page: Pageable): Page<Film>

    fun asPredicate(root: QFilm, filter: FilmFilter): Predicate?
}

@Repository
class FilmCustomRepositoryImpl : FilmCustomRepository, QuerydslRepositorySupport(Film::class.java) {
    override fun fetchByFilter(filter: FilmFilter, page: Pageable): Page<Film> {
        val root = QFilm.film
        var query = from(root).where(asPredicate(root, filter))

        query = querydsl!!.applyPagination(page, query)
        return PageableExecutionUtils.getPage(query.fetch(), page, query::fetchCount)

    }

    override fun asPredicate(root: QFilm, filter: FilmFilter): Predicate? =
        BooleanBuilder()
            .and(filter.title?.let { root.title.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.pegi?.let { root.pegi.eq(it) })
            .and(filter.duration?.let { root.duration.between(it.lowerEndpoint(), it.upperEndpoint()) })
            .and(filter.releaseDate?.let { root.releaseDate.between(it.lowerEndpoint(), it.upperEndpoint()) })
            .and(filter.originalLanguage?.let { root.originalLanguage.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.createdAt?.let { root.createdAt.between(it.lowerEndpoint(), it.upperEndpoint()) })
            .value
}