package pl.szudor.film

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import pl.szudor.querydsl.applyPagination

interface FilmRepository : JpaRepository<Film, Int>, FilmCustomRepository {
    fun deleteAllByRoomId(roomId: Int)
    fun deleteAllByRepertoireId(repertoireId: Int)
}

interface FilmCustomRepository {
    fun findAllFilms(page: Pageable): Page<Film>
}

@Repository
class FilmCustomRepositoryImpl : FilmCustomRepository, QuerydslRepositorySupport(Film::class.java) {
    override fun findAllFilms(page: Pageable): Page<Film> {
        val query = from(QFilm.film)
        return querydsl!!.applyPagination(page, query) { query.fetchCount() }
    }
}