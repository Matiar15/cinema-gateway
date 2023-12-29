package pl.szudor.repertoire

import pl.szudor.utils.PositiveDate
import java.time.LocalDate
import javax.validation.constraints.NotNull

data class RepertoirePayload(
    @field:NotNull
    @field:PositiveDate
    val playedAt: LocalDate?
)