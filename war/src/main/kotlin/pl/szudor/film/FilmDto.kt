package pl.szudor.film

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class FilmDto (
    var id: Int,
    val playedAt: LocalTime,
    val title: String,
    val pegi: Pegi,
    val duration: Int,
    val releaseDate: LocalDate,
    val originalLanguage: String,
    val createdAt: LocalDateTime
)