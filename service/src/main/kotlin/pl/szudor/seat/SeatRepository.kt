package pl.szudor.seat

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import pl.szudor.exception.SeatingNotExistsException

interface SeatRepository : JpaRepository<Seat, Int>, SeatCustomRepository

fun SeatRepository.requireById(id: Int): Seat = findById(id).orElseThrow { SeatingNotExistsException(id) }

interface SeatCustomRepository {
    fun removeByRoom(roomId: Int)
}

class SeatRepositoryImpl : QuerydslRepositorySupport(SeatRepository::class.java), SeatCustomRepository {
    override fun removeByRoom(roomId: Int) {
        val root = QSeat.seat

        delete(root)
            .where(root.room.id.eq(roomId))
            .execute()
    }
}