package pl.szudor.seating

import pl.szudor.room.Room
import javax.validation.constraints.NotNull

data class SeatingDto(
    val id: Int?,
    @NotNull
    val seatNumber: Int?,
    val room: Room?,
    val isTaken: Taken?
)