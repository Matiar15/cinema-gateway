package pl.szudor.repertoire

import io.swagger.v3.oas.annotations.media.Schema
import pl.szudor.cinema.CinemaDto
import java.time.LocalDate


data class RepertoireDto(
    @field:Schema(description = "ID")
    val id: Int,
    @field:Schema(description = "Date of the repertoire")
    val playedAt: LocalDate,
    @field:Schema(description = "Related cinema")
    var cinema: CinemaDto
)