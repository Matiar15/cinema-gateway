package pl.szudor.film

import io.swagger.v3.oas.annotations.media.Schema

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