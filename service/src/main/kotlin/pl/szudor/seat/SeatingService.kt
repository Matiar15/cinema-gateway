package pl.szudor.seat

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import pl.szudor.exception.SeatingNotExistsException
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
) : SeatingService {
    override fun saveSeating(seating: SeatingDto, roomId: Int): Seating =
        seatingRepository.save(seating.toEntity())


    override fun updateSeating(id: Int, seating: SeatingDto): Seating =
        seatingRepository.save(seatingRepository.findSeating(id).apply {
            number = seating.seatNumber!!
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
    number,
)

fun SeatingDto.toEntity() = Seating(
    seatNumber!!,
)