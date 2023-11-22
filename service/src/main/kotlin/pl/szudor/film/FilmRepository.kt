package pl.szudor.film

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import pl.szudor.exception.FilmNotExistsException

interface FilmRepository : JpaRepository<Film, Int>, FilmCustomRepository
fun FilmRepository.requireById(id: Int) = this.findByIdOrNull(id) ?: throw FilmNotExistsException(id)
interface FilmCustomRepository {
    fun findAllFilms(page: Pageable): Page<Film>
}

@Repository
class FilmCustomRepositoryImpl : FilmCustomRepository, QuerydslRepositorySupport(Film::class.java) {
    override fun findAllFilms(page: Pageable): Page<Film> {
        val root = from(QFilm.film)
        val query = querydsl!!.applyPagination(page, root)
        return PageableExecutionUtils.getPage(query.fetch(), page, query::fetchCount)
    }
}