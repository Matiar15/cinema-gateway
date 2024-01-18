package pl.szudor.seat.reserved

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.szudor.event.EventRepository
import pl.szudor.event.requireById
import pl.szudor.seat.Seat
import pl.szudor.seat.SeatRepository
import pl.szudor.seat.requireById
import javax.transaction.Transactional

interface ReservedSeatService {
    fun create(eventId: Int, seatId: Int): ReservedSeat
    fun delete(seatId: Int)
    fun fetch(eventId: Int, pageRequest: Pageable): Page<Seat>
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

    override fun delete(seatId: Int) {
        try {
            reservedSeatRepository.deleteById(seatId)
        } catch (_: DataIntegrityViolationException) {
            return
        }
    }

    override fun fetch(eventId: Int, pageRequest: Pageable): Page<Seat> =
        reservedSeatRepository.fetch(eventId, pageRequest)

}