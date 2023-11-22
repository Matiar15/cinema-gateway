package pl.szudor.cinema

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import pl.szudor.exception.CinemaNotExistsException

interface CinemaRepository : JpaRepository<Cinema, Int>, CinemaCustomRepository

fun CinemaRepository.findCinema(id: Int): Cinema = this.findByIdOrNull(id) ?: throw CinemaNotExistsException(id)
interface CinemaCustomRepository {
    fun fetchAll(page: Pageable): Page<Cinema>
}

@Repository
class CinemaCustomRepositoryImpl : CinemaCustomRepository, QuerydslRepositorySupport(Cinema::class.java) {

    override fun fetchAll(page: Pageable): Page<Cinema> {
        val root = from(QCinema.cinema)
        val query = querydsl!!.applyPagination(page, root)
        return PageableExecutionUtils.getPage(query.fetch(), page, query::fetchCount)
    }

}