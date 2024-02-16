package pl.szudor.room

import org.springframework.stereotype.Service
import pl.szudor.cinema.Cinema


interface RoomFactory {
    fun createRoom(number: Int, cinema: Cinema): Room
}

@Service
class RoomFactoryImpl: RoomFactory {
    override fun createRoom(number: Int, cinema: Cinema) =
        Room().apply { this.cinema = cinema; this.number = number }
}