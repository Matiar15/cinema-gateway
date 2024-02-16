package pl.szudor.film

import com.google.common.collect.Range
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class FilmFilter(
    val playedAt: Range<LocalTime>?,
    val title: String?,
    val pegi: Pegi?,
    val duration: Range<Int>?,
    val releaseDate: Range<LocalDate>?,
    val originalLanguage: String?,
    val createdAt: Range<LocalDateTime>?,
)
