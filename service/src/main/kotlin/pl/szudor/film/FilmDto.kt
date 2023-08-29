package pl.szudor.film

import javax.validation.constraints.NotNull
import pl.szudor.repertoire.RepertoireDto
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class FilmDto (
    var id: Int?,
    @NotNull
    val playedAt: LocalTime?,
    @NotNull
    val roomNumber: Int?,

    val repertoire: RepertoireDto?,

    @NotNull
    val title: String?,

    @NotNull
    val pegi: Pegi?,

    @NotNull
    val duration: Int?,

    @NotNull
    val releaseDate: LocalDate?,

    @NotNull
    val originalLanguage: String?,

    val createdAt: LocalDateTime?,

)