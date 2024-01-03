package pl.szudor.seat.reserved

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import pl.szudor.event.QEvent
import pl.szudor.seat.QSeat

interface ReservedSeatRepository : JpaRepository<ReservedSeat, Int>, ReservedSeatCustomRepository

interface ReservedSeatCustomRepository {
    fun remove(eventId: Int, seatId: Int)
    fun fetch(eventId: Int, pageRequest: Pageable): Page<ReservedSeat>
}

class ReservedSeatRepositoryImpl : QuerydslRepositorySupport(ReservedSeat::class.java), ReservedSeatCustomRepository {
    override fun remove(eventId: Int, seatId: Int) {
        val root = QReservedSeat.reservedSeat

        delete(root)
            .where(root.id.seatId.eq(seatId))
            .where(root.id.eventId.eq(eventId))
            .execute()
    }

    override fun fetch(eventId: Int, pageRequest: Pageable): Page<ReservedSeat> {
        val root = QReservedSeat.reservedSeat
        val seat = QSeat.seat
        val event = QEvent.event

        var query = from(root)
            .join(root.event, event).fetchJoin()
            .join(root.seat, seat).fetchJoin()
            .where(event.id.eq(eventId))

        query = querydsl!!.applyPagination(pageRequest, query)
        return PageableExecutionUtils.getPage(query.fetch(), pageRequest, query::fetchCount)
    }

}