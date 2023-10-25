package pl.szudor.seating

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import pl.szudor.exception.SeatingNotExistsException
import pl.szudor.room.RoomRepository
import pl.szudor.room.RoomRepositoryExtension.findRoom
import pl.szudor.seating.SeatingRepositoryExtension.findSeating
import javax.transaction.Transactional

interface SeatingService {
    fun saveSeating(seating: SeatingDto, roomId: Int): Seating
    fun updateSeating(id: Int, seating: SeatingDto): Seating
    fun deleteSeating(id: Int)
}

@Service
@Transactional
class SeatingServiceImpl(
    private val seatingRepository: SeatingRepository,
    private val roomRepository: RoomRepository
) : SeatingService {
    @Autowired
    lateinit var logger: Logger


    override fun saveSeating(seating: SeatingDto, roomId: Int): Seating {
        logger.info("SAVING SEATING IN ROOM NUMBER $roomId...")
        return seatingRepository.save(seating.toEntity().apply {
            room = roomRepository.findRoom(roomId)
        })
    }

    override fun updateSeating(id: Int, seating: SeatingDto): Seating {
        logger.info("UPDATING SEATING IN ROOM NUMBER $id...")
        return seatingRepository.save(seatingRepository.findSeating(id).apply {
            seatNumber = seating.seatNumber!!
        })
    }

    override fun deleteSeating(id: Int) =
        try {
            logger.info("DELETING SEATING IN ROOM NUMBER $id...")
            seatingRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw SeatingNotExistsException(id)
        }
}

fun Seating.toDto(): SeatingDto = SeatingDto(
    id,
    seatNumber,
    room
)

fun SeatingDto.toEntity(): Seating = Seating(
    seatNumber!!,
    room
)