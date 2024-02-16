package pl.szudor.seat

import org.springframework.stereotype.Service
import pl.szudor.room.Room

interface SeatFactory {
    fun createSeat(number: Int, room: Room): Seat
}

@Service
class SeatFactoryImpl: SeatFactory {
    override fun createSeat(number: Int, room: Room) =
        Seat().apply {
            this.number = number
            this.room = room
        }
}