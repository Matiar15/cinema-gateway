package pl.szudor.seat

import org.springframework.data.jpa.repository.JpaRepository
import pl.szudor.exception.SeatingNotExistsException

interface SeatRepository : JpaRepository<Seat, Int>

fun SeatRepository.findSeat(id: Int): Seat = this.findById(id).orElseThrow { SeatingNotExistsException(id) }