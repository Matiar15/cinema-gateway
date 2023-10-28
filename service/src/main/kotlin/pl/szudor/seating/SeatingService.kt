package pl.szudor.seating

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import pl.szudor.exception.SeatingNotExistsException
import pl.szudor.room.RoomRepository
import pl.szudor.room.findRoom
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
    override fun saveSeating(seating: SeatingDto, roomId: Int): Seating =
        seatingRepository.save(seating.toEntity().apply {
            room = roomRepository.findRoom(roomId)
            isTaken = Taken.NO
        })


    override fun updateSeating(id: Int, seating: SeatingDto): Seating =
        seatingRepository.save(seatingRepository.findSeating(id).apply {
            seatNumber = seating.seatNumber!!
        })


    override fun deleteSeating(id: Int) =
        try {
            seatingRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw SeatingNotExistsException(id)
        }
}

fun Seating.toDto() = SeatingDto(
    id,
    seatNumber,
    room,
    isTaken
)

fun SeatingDto.toEntity() = Seating(
    seatNumber!!,
    room,
    isTaken
)