package pl.szudor.room

import pl.szudor.cinema.CinemaDto
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

data class RoomDto(
    val id: Int?,
    val cinema: CinemaDto?,
    val roomNumber: Int?,
    val createdAt: LocalDateTime?
)
