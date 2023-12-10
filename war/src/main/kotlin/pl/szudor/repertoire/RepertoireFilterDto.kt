package pl.szudor.repertoire

import pl.szudor.utils.RangeDate
import pl.szudor.utils.RangeDto

data class RepertoireFilterDto(
    @RangeDate
    val playedAt: RangeDto.LocalDate?
)