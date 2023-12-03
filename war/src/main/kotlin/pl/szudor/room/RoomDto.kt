package pl.szudor.room

import io.swagger.v3.oas.annotations.media.Schema
import pl.szudor.cinema.CinemaDto
import java.time.LocalDateTime

data class RoomDto(
    @field:Schema(description = "ID")
    val id: Int?,
    @field:Schema(description = "Cinema")
    val cinema: CinemaDto?,
    @field:Schema(description = "Number")
    val number: Int?,
    @field:Schema(description = "Created at")
    val createdAt: LocalDateTime?
)
