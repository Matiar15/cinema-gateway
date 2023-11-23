package pl.szudor.cinema

import com.google.common.collect.Range
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.DatePath
import com.querydsl.core.types.dsl.DateTimePath
import com.querydsl.core.types.dsl.NumberPath
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import pl.szudor.exception.CinemaNotExistsException
import java.time.LocalDate
import java.time.LocalDateTime

interface CinemaRepository : JpaRepository<Cinema, Int>, CinemaCustomRepository

fun CinemaRepository.findCinema(id: Int): Cinema = this.findByIdOrNull(id) ?: throw CinemaNotExistsException(id)
interface CinemaCustomRepository {
    fun fetchByFilter(page: Pageable, filter: CinemaFilter): Page<Cinema>
}

@Repository
class CinemaCustomRepositoryImpl : CinemaCustomRepository, QuerydslRepositorySupport(Cinema::class.java) {

    override fun fetchByFilter(page: Pageable, filter: CinemaFilter): Page<Cinema> {
        val root = QCinema.cinema
        var query = from(root).where(asPredicate(root, filter))
        query = querydsl!!.applyPagination(page, query)
        return PageableExecutionUtils.getPage(query.fetch(), page, query::fetchCount)
    }

    private fun asPredicate(root: QCinema, filter: CinemaFilter): Predicate? =
        BooleanBuilder()
            .and(filter.name?.let { root.name.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.address?.let { root.address.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.email?.let { root.email.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.phoneNumber?.let { root.phoneNumber.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.postalCode?.let { root.postalCode.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.director?.let { root.director.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.nipCode?.let { root.nipCode.like(it.prefixAndSuffix("%", "%")) })
            .and(filter.buildDate?.asRangePredicate(root.buildDate))
            .and(filter.createdAt?.asRangePredicate(root.createdAt))
            .and(filter.state?.let { root.state.eq(it) })
            .value
    // TODO: dodanie walidacji dla RangeDto,
    //  dodanie where() predykatu dla Range, gdy ma tylko upper lub lower bound


}

fun Range<LocalDateTime>.asRangePredicate(date: DateTimePath<LocalDateTime>): Predicate =
    date.between(this.lowerEndpoint(), this.upperEndpoint())
fun Range<LocalDate>.asRangePredicate(date: DatePath<LocalDate>): Predicate =
    date.between(this.lowerEndpoint(), this.upperEndpoint())
fun Range<Int>.asRangePredicate(number: NumberPath<Int>): Predicate =
    number.between(this.lowerEndpoint(), this.upperEndpoint())

fun String?.prefixAndSuffix(prefix: String, suffix: String) =
    this?.let { prefix + it + suffix }

