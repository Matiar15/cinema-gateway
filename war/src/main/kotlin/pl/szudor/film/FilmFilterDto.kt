package pl.szudor.film

import pl.szudor.utils.*

data class FilmFilterDto(
    @field:RangeTime
    val playedAt: RangeDto.LocalTime?,
    val title: String?,
    val pegi: Pegi?,
    @field:RangeInt
    val duration: RangeDto.Int?,
    @field:RangeDate
    val releaseDate: RangeDto.LocalDate?,
    val originalLanguage: String?,
    @field:RangeDateTime
    val createdAt: RangeDto.LocalDateTime?,
)