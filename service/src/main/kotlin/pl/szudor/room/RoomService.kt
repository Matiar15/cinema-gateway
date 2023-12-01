package pl.szudor.room

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.findCinema
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.film.FilmRepository
import pl.szudor.room.RoomRepositoryExtension.findRoom
import pl.szudor.seat.SeatRepository
import javax.transaction.Transactional


interface RoomService {
    fun saveRoom(room: RoomDto, cinemaId: Int): Room
    fun updateRoom(id: Int, roomPayload: RoomPayload): Room
    fun deleteRoom(id: Int)
}

@Service
@Transactional
class RoomServiceImpl(
    private val roomRepository: RoomRepository,
    private val cinemaRepository: CinemaRepository,
    private val filmRepository: FilmRepository,
    private val seatRepository: SeatRepository
) : RoomService {
    override fun saveRoom(room: RoomDto, cinemaId: Int): Room =
        roomRepository.save(room.toEntity().apply { cinema = cinemaRepository.findCinema(cinemaId) })


    override fun updateRoom(id: Int, roomPayload: RoomPayload): Room =
        roomRepository.save(roomRepository.findRoom(id).apply {
            number = roomPayload.roomNumber
        })


    override fun deleteRoom(id: Int) =
        try {
            roomRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw RoomNotExistsException(id)
        }


}

fun Room.toDto() = RoomDto(
    id,
    number,
    /*cinema?.toDto(),*/
    createdAt
)


fun RoomDto.toEntity() = Room(
    roomNumber!!,
    /*cinema?.toEntity()*/
)
