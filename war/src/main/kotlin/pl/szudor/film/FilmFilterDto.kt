package pl.szudor.film

import pl.szudor.utils.*

data class FilmFilterDto(
    @field:RangeTimeConstraint
    val playedAt: RangeDto.LocalTime?,
    val title: String?,
    val pegi: Pegi?,
    @field:RangeIntConstraint
    val duration: RangeDto.Int?,
    @field:RangeDateConstraint
    val releaseDate: RangeDto.LocalDate?,
    val originalLanguage: String?,
    @field:RangeDateTimeConstraint
    val createdAt: RangeDto.LocalDateTime?,
)