package pl.szudor.room

import org.springframework.data.jpa.repository.JpaRepository
import pl.szudor.exception.RoomNotExistsException

interface RoomRepository : JpaRepository<Room, Int>

fun RoomRepository.requireById(id: Int): Room = findById(id).orElseThrow { RoomNotExistsException(id) }
