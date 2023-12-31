package pl.szudor.event

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalTime
import javax.validation.constraints.NotNull

data class EventPayload(
    @Schema(name = "Played at")
    @field:NotNull
    val playedAt: LocalTime?
)