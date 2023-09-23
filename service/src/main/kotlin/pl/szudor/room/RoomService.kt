package pl.szudor.room

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.szudor.cinema.toDto
import pl.szudor.cinema.toEntity
import javax.transaction.Transactional


interface RoomService {
    fun saveRoom(room: RoomDto): Room?
}

@Service
@Transactional
class RoomServiceImpl(
    private val roomRepository: RoomRepository
): RoomService {

    @set:Autowired
    lateinit var logger: Logger

    override fun saveRoom(room: RoomDto): Room {
        logger.info("SAVING ROOM...")
        return roomRepository.save(room.toEntity())
    }

}

fun Room.toDto(): RoomDto
    = RoomDto(
        id,
        roomNumber,
        cinema?.toDto(),
        createdAt
    )


fun RoomDto.toEntity(): Room
    = Room(
        roomNumber!!,
        cinema?.toEntity()
    )
