package pl.szudor.seating

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.szudor.exception.RoomNotExistsException
import pl.szudor.room.RoomRepository
import javax.transaction.Transactional

interface SeatingService {
    fun saveSeating(seating: SeatingDto, roomId: Int): Seating
}

@Service
@Transactional
class SeatingServiceImpl(
    private val seatingRepository: SeatingRepository,
    private val roomRepository: RoomRepository
): SeatingService {
    @set:Autowired
    lateinit var logger: Logger


    override fun saveSeating(seating: SeatingDto, roomId: Int): Seating {
        logger.info("SAVING SEATING IN ROOM NUMBER $roomId...")
        return seatingRepository.save(seating.toEntity().apply {
            room = roomRepository.findByIdOrNull(roomId)
                ?: throw RoomNotExistsException(roomId.toString())
        })
    }
}

fun Seating.toDto(): SeatingDto
    = SeatingDto(
        id,
        seatNumber,
        room
    )

fun SeatingDto.toEntity(): Seating
    = Seating(
        seatNumber!!,
        room
    )