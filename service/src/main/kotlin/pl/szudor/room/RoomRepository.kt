package pl.szudor.room

import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository: JpaRepository<Room, Int>
