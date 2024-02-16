package pl.szudor.event

import pl.szudor.film.FilmFilterPayload
import pl.szudor.repertoire.RepertoireFilterPayload
import pl.szudor.utils.RangeDto
import pl.szudor.utils.RangeTime

data class EventFilterPayload(
    val repertoire: RepertoireFilterPayload?,
    val film: FilmFilterPayload?,
    @RangeTime
    val playedAt: RangeDto.LocalTime?
)
