package pl.szudor.room

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.findCinema
import pl.szudor.cinema.toDto
import pl.szudor.cinema.toEntity
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.film.FilmRepository
import pl.szudor.seating.SeatingRepository
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
    private val seatingRepository: SeatingRepository
) : RoomService {
    override fun saveRoom(room: RoomDto, cinemaId: Int): Room =
        roomRepository.save(room.toEntity().apply { cinema = cinemaRepository.findCinema(cinemaId) })


    override fun updateRoom(id: Int, roomPayload: RoomPayload): Room =
        roomRepository.save(roomRepository.findRoom(id).apply {
            roomNumber = roomPayload.roomNumber
        })


    override fun deleteRoom(id: Int) =
        try {
            filmRepository.deleteAllByRoomId(id)
            seatingRepository.deleteAllByRoomId(id)
            roomRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw RoomNotExistsException(id)
        }


}

fun Room.toDto() = RoomDto(
    id,
    roomNumber,
    cinema?.toDto(),
    createdAt
)


fun RoomDto.toEntity() = Room(
    roomNumber!!,
    cinema?.toEntity()
)
