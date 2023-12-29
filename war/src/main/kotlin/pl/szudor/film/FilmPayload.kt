package pl.szudor.film

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class FilmPayload(
    @field:Schema(description = "Title")
    @field:NotEmpty
    val title: String?,
    @field:Schema(description = "PEGI")
    @field:NotNull
    val pegi: Pegi?,
    @field:Schema(description = "Duration")
    @field:NotNull
    @field:Positive
    val duration: Int?,
    @field:Schema(description = "Release date")
    @field:NotNull
    val releaseDate: LocalDate?,
    @field:Schema(description = "Original language")
    @field:NotEmpty
    val originalLanguage: String?
)
