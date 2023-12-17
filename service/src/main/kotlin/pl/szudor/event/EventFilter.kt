package pl.szudor.event

import com.google.common.collect.Range
import pl.szudor.film.FilmFilter
import pl.szudor.repertoire.RepertoireFilter
import java.time.LocalTime

data class EventFilter(
    val repertoire: RepertoireFilter?,
    val film: FilmFilter?,
    val playedAt: Range<LocalTime>?
)
