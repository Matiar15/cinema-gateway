package pl.szudor.event

import pl.szudor.film.FilmFilterDto
import pl.szudor.repertoire.RepertoireFilterDto
import pl.szudor.utils.RangeDto
import pl.szudor.utils.RangeTime

data class EventFilterPayload(
    val repertoire: RepertoireFilterDto?,
    val film: FilmFilterDto?,
    @RangeTime
    val playedAt: RangeDto.LocalTime?
)
