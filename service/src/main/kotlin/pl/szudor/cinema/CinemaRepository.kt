package pl.szudor.cinema

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.querydsl.applyPagination

interface CinemaRepository : JpaRepository<Cinema, Int>, CinemaCustomRepository

fun CinemaRepository.findCinema(id: Int): Cinema = this.findByIdOrNull(id) ?: throw CinemaNotExistsException(id)
interface CinemaCustomRepository {
    fun findAllCinemas(page: Pageable): Page<Cinema>
}

@Repository
class CinemaCustomRepositoryImpl : CinemaCustomRepository, QuerydslRepositorySupport(Cinema::class.java) {

    override fun findAllCinemas(page: Pageable): Page<Cinema> {
        val query = from(QCinema.cinema)
        return querydsl!!.applyPagination(page, query) { query.fetchCount() }
    }

}