package pl.szudor.seat.reserved

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.szudor.event.EventRepository
import pl.szudor.event.requireById
import pl.szudor.seat.SeatRepository
import pl.szudor.seat.requireById
import javax.transaction.Transactional

interface ReservedSeatService {
    fun create(eventId: Int, seatId: Int): ReservedSeat
    fun delete(eventId: Int, seatId: Int)
    fun fetch(eventId: Int, pageRequest: Pageable): Page<ReservedSeat>
}

@Service
@Transactional
class ReservedSeatServiceImpl(
    private val reservedSeatRepository: ReservedSeatRepository,
    private val seatRepository: SeatRepository,
    private val eventRepository: EventRepository,
    private val reservedSeatFactory: ReservedSeatFactory,
) : ReservedSeatService {
    override fun create(eventId: Int, seatId: Int): ReservedSeat =
        reservedSeatRepository.save(
            reservedSeatFactory.create(
                eventRepository.requireById(eventId),
                seatRepository.requireById(seatId)
            )
        )

    override fun delete(eventId: Int, seatId: Int) = reservedSeatRepository.remove(eventId, seatId)

    override fun fetch(eventId: Int, pageRequest: Pageable): Page<ReservedSeat> =
        reservedSeatRepository.fetch(eventId, pageRequest)

}