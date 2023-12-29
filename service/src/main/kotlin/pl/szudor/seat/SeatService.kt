package pl.szudor.seat

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.szudor.exception.SeatingNotExistsException
import pl.szudor.room.RoomRepository
import pl.szudor.room.requireById

interface SeatService {
    fun saveSeat(roomId: Int, number: Int): Seat
    fun patchSeat(id: Int, occupied: Occupied): Seat
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
        seatRepository.save(seatFactory.createSeat(number, Occupied.NO, roomRepository.requireById(roomId)))

    override fun patchSeat(id: Int, occupied: Occupied): Seat =
        seatRepository.save(seatRepository.requireById(id).apply { this.occupied = occupied })

    override fun deleteSeat(id: Int) = runCatching {
        seatRepository.deleteById(id)
    }.getOrElse { throw SeatingNotExistsException(id) }
}