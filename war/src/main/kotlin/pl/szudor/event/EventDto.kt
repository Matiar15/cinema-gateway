package pl.szudor.event

import io.swagger.v3.oas.annotations.media.Schema
import pl.szudor.cinema.CinemaDto
import pl.szudor.film.Pegi
import java.time.LocalDate
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

data class RepertoireDto(
    @field:Schema(description = "ID")
    val id: Int,
    @field:Schema(description = "Date of the repertoire")
    val playedAt: LocalDate,
    @field:Schema(description = "Related cinema")
    var cinema: CinemaDto
)
data class FilmDto(
    @field:Schema(description = "ID")
    var id: Int,
    @field:Schema(description = "Title")
    val title: String,
    @field:Schema(description = "PEGI")
    val pegi: Pegi,
    @field:Schema(description = "Duration")
    val duration: Int,
    @field:Schema(description = "Original language")
    val originalLanguage: String,
)

data class RoomDto(
    @field:Schema(description = "ID")
    val id: Int,
    @field:Schema(description = "Number")
    val number: Int
)