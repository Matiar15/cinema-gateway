package pl.szudor.cinema

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import pl.szudor.exception.CinemaNotExistsException
import pl.szudor.utils.between

interface CinemaRepository : JpaRepository<Cinema, Int>, CinemaCustomRepository

fun CinemaRepository.requireById(id: Int): Cinema = findById(id).orElseThrow { CinemaNotExistsException(id) }
interface CinemaCustomRepository {
    fun fetchByFilter(page: Pageable, filter: CinemaFilter): Page<Cinema>

    fun asPredicate(root: QCinema, filter: CinemaFilter): Predicate?
}

class CinemaCustomRepositoryImpl : CinemaCustomRepository, QuerydslRepositorySupport(Cinema::class.java) {

    override fun fetchByFilter(page: Pageable, filter: CinemaFilter): Page<Cinema> {
        val root = QCinema.cinema
        var query = from(root).where(asPredicate(root, filter))

        query = querydsl!!.applyPagination(page, query)
        return PageableExecutionUtils.getPage(query.fetch(), page, query::fetchCount)
    }

    override fun asPredicate(root: QCinema, filter: CinemaFilter): Predicate? =
        BooleanBuilder()
            .and(filter.name?.let { root.name.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.address?.let { root.address.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.email?.let { root.email.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.phoneNumber?.let { root.phoneNumber.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.postalCode?.let { root.postalCode.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.director?.let { root.director.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.nipCode?.let { root.nipCode.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.buildDate?.between(root.buildDate))
            .and(filter.createdAt?.between(root.createdAt))
            .and(filter.active?.let { root.active.eq(it) })
            .value
}

fun String?.prefixAndSuffix(prefix: String, suffix: String) =
    this?.let { prefix + it + suffix }

