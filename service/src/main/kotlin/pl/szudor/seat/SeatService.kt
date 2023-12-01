package pl.szudor.seat

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import pl.szudor.exception.SeatingNotExistsException
import javax.transaction.Transactional

interface SeatService {
    fun saveSeating(seating: SeatDto, roomId: Int): Seat
    fun updateSeating(id: Int, seating: SeatDto): Seat
    fun deleteSeating(id: Int)
}

@Service
@Transactional
class SeatServiceImpl(
    private val seatRepository: SeatRepository,
) : SeatService {
    override fun saveSeating(seating: SeatDto, roomId: Int): Seat =
        seatRepository.save(seating.toEntity())


    override fun updateSeating(id: Int, seating: SeatDto): Seat =
        seatRepository.save(seatRepository.findSeating(id).apply {
            number = seating.seatNumber!!
        })


    override fun deleteSeating(id: Int) =
        try {
            seatRepository.deleteById(id)
        } catch (_: EmptyResultDataAccessException) {
            throw SeatingNotExistsException(id)
        }
}

fun Seat.toDto() = SeatDto(
    id,
    number,
)

fun SeatDto.toEntity() = Seat(
    seatNumber!!,
)