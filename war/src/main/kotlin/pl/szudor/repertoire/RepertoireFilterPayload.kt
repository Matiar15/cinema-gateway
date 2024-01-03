package pl.szudor.repertoire

import pl.szudor.utils.RangeDate
import pl.szudor.utils.RangeDto

data class RepertoireFilterPayload(
    @RangeDate
    val playedAt: RangeDto.LocalDate?
)