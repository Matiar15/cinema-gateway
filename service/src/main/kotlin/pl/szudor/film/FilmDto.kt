package pl.szudor.film

import pl.szudor.repertoire.RepertoireDto
import pl.szudor.room.RoomDto
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.validation.constraints.NotNull

data class FilmDto (
    var id: Int?,
    @field:NotNull
    val playedAt: LocalTime?,
    val repertoire: RepertoireDto?,
    @field:NotNull
    val title: String?,
    @field:NotNull
    val pegi: Pegi?,
    @field:NotNull
    val duration: Int?,
    @field:NotNull
    val releaseDate: LocalDate?,
    @field:NotNull
    val originalLanguage: String?,
    val room: RoomDto?,
    val createdAt: LocalDateTime?
)