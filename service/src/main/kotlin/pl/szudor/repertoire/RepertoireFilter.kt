package pl.szudor.repertoire

import com.google.common.collect.Range
import java.time.LocalDate

data class RepertoireFilter(
    val playedAt: Range<LocalDate>?
)
