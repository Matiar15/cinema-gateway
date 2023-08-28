package pl.szudor.film

import javax.validation.constraints.NotNull
import pl.szudor.repertoire.RepertoireDto
import java.time.LocalDateTime
import java.time.LocalTime

data class FilmDto (
    var id: Int?,
    @NotNull
    val playedAt: LocalTime?,
    @NotNull
    val roomNumber: Int?,

    val repertoire: RepertoireDto?,

    val createdAt: LocalDateTime?
)