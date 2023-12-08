package pl.szudor.room

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import pl.szudor.exception.RoomNotExistsException

interface RoomRepository : JpaRepository<Room, Int>

fun RoomRepository.requireById(id: Int): Room = this.findById(id).orElseThrow { RoomNotExistsException(id) }
