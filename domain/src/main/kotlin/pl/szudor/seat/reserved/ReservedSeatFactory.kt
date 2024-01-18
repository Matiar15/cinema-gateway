package pl.szudor.seat.reserved

import org.springframework.stereotype.Service
import pl.szudor.event.Event
import pl.szudor.seat.Seat

interface ReservedSeatFactory {
    fun create(event: Event, seat: Seat): ReservedSeat
}

@Service
class ReservedSeatFactoryImpl : ReservedSeatFactory {
    override fun create(event: Event, seat: Seat) = ReservedSeat().apply {
        this.id = seat.id!!
        this.event = event
        this.seat = seat
    }
}