package pl.szudor.event

import io.swagger.v3.oas.annotations.media.Schema
import pl.szudor.film.FilmDto
import pl.szudor.repertoire.RepertoireDto
import pl.szudor.room.RoomDto
import java.time.LocalTime

data class EventDto(
    @field:Schema(description = "Repertoire")
    val repertoire: RepertoireDto,
    @field:Schema(description = "Film")
    val film: FilmDto,
    @field:Schema(description = "Room")
    val room: RoomDto,
    @field:Schema(description = "Played at")
    val playedAt: LocalTime
)