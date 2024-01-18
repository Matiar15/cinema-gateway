package pl.szudor.seat.reserved

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import pl.szudor.event.QEvent
import pl.szudor.seat.QSeat
import pl.szudor.seat.Seat

interface ReservedSeatRepository : JpaRepository<ReservedSeat, Int>, ReservedSeatCustomRepository

interface ReservedSeatCustomRepository {
    fun fetch(eventId: Int, pageRequest: Pageable): Page<Seat>
}

class ReservedSeatRepositoryImpl : QuerydslRepositorySupport(ReservedSeat::class.java), ReservedSeatCustomRepository {
    override fun fetch(eventId: Int, pageRequest: Pageable): Page<Seat> {
        val root = QReservedSeat.reservedSeat
        val seat = QSeat.seat
        val event = QEvent.event

        var query = from(root)
            .select(seat)
            .join(root.event, event)
            .join(root.seat, seat)
            .where(event.id.eq(eventId))

        query = querydsl!!.applyPagination(pageRequest, query)
        return PageableExecutionUtils.getPage(query.fetch(), pageRequest, query::fetchCount)
    }

}