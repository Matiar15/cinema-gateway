package pl.szudor.seat

import org.springframework.stereotype.Service
import pl.szudor.room.Room

interface SeatFactory {
    fun createSeat(number: Int, occupied: Occupied, room: Room): Seat
}

@Service
class SeatFactoryImpl: SeatFactory {
    override fun createSeat(number: Int, occupied: Occupied, room: Room) =
        Seat().apply {
            this.number = number
            this.occupied = occupied
            this.room = room
        }
}