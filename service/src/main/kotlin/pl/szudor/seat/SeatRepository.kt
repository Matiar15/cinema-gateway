package pl.szudor.seat

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import pl.szudor.exception.SeatingNotExistsException

interface SeatRepository : JpaRepository<Seat, Int>

fun SeatRepository.findSeating(id: Int): Seat = this.findByIdOrNull(id) ?: throw SeatingNotExistsException(id)