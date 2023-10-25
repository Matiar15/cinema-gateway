package pl.szudor.room

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.CinemaRepositoryExtension.findCinema
import pl.szudor.cinema.toDto
import pl.szudor.cinema.toEntity
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.film.FilmRepository
import pl.szudor.room.RoomRepositoryExtension.findRoom
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
    private val filmRepository: FilmRepository
) : RoomService {

    @Autowired
    lateinit var logger: Logger

    override fun saveRoom(room: RoomDto, cinemaId: Int): Room {
        logger.info("SAVING ROOM...")
        return roomRepository.save(room.toEntity().apply { cinema = cinemaRepository.findCinema(cinemaId) })
    }

    override fun updateRoom(id: Int, roomPayload: RoomPayload): Room {
        logger.info("UPDATING ROOM...")
        return roomRepository.save(roomRepository.findRoom(id).apply {
            roomNumber = roomPayload.roomNumber
        })
    }

    override fun deleteRoom(id: Int) =
        try {
            logger.info("DELETING FILMS...")
            filmRepository.deleteAllByRoomId(id)
            logger.info("DELETING ROOM...")
            roomRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw RoomNotExistsException(id)
        }



}

fun Room.toDto(): RoomDto = RoomDto(
    id,
    roomNumber,
    cinema?.toDto(),
    createdAt
)


fun RoomDto.toEntity(): Room = Room(
    roomNumber!!,
    cinema?.toEntity()
)
