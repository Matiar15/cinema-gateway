package pl.szudor.room

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import pl.szudor.exception.RoomNotExistsException

interface RoomRepository: JpaRepository<Room, Int>

object RoomRepositoryExtension {
    fun RoomRepository.findRoom(id: Int): Room = this.findByIdOrNull(id) ?: throw RoomNotExistsException(id)
}
