package pl.szudor.room

import java.time.LocalDateTime
import javax.validation.constraints.NotNull

data class RoomDto(
    val id: Int?,
    @field:NotNull
    val roomNumber: Int?,
    /*val cinema: CinemaDto?,*/
    val createdAt: LocalDateTime?
)
