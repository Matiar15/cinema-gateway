package pl.szudor.seat

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.szudor.exception.SeatNotExistsException
import pl.szudor.room.RoomRepository
import pl.szudor.room.requireById

interface SeatService {
    fun saveSeat(roomId: Int, number: Int): Seat
    fun deleteSeat(id: Int)
}

@Service
@Transactional
class SeatServiceImpl(
    private val seatRepository: SeatRepository,
    private val roomRepository: RoomRepository,
    private val seatFactory: SeatFactory
) : SeatService {
    override fun saveSeat(roomId: Int, number: Int): Seat =
        seatRepository.save(seatFactory.createSeat(number, roomRepository.requireById(roomId)))

    override fun deleteSeat(id: Int) = runCatching {
        seatRepository.deleteById(id)
    }.getOrElse { throw SeatNotExistsException(id) }
}