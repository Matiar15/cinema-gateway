package pl.szudor.room

import org.springframework.stereotype.Service
import pl.szudor.cinema.CinemaRepository
import pl.szudor.cinema.requireById
import pl.szudor.exception.RoomNotExistsException
import javax.transaction.Transactional


interface RoomService {
    fun saveRoom(number: Int, cinemaId: Int): Room
    fun deleteRoom(id: Int)
}

@Service
@Transactional
class RoomServiceImpl(
    private val roomRepository: RoomRepository,
    private val cinemaRepository: CinemaRepository,
    private val roomFactory: RoomFactory
) : RoomService {
    override fun saveRoom(number: Int, cinemaId: Int): Room =
        roomRepository.save(roomFactory.createRoom(number, cinemaRepository.requireById(cinemaId)))

    override fun deleteRoom(id: Int) = runCatching {
        roomRepository.deleteById(id)
    }.getOrElse { throw RoomNotExistsException(id) }
}